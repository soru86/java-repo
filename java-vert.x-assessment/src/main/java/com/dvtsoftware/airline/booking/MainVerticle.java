package com.dvtsoftware.airline.booking;

import com.dvtsoftware.airline.booking.handler.AirlineHandler;
import com.dvtsoftware.airline.booking.handler.BookingsHandler;
import com.dvtsoftware.airline.booking.handler.FlightHandler;
import com.dvtsoftware.airline.booking.handler.PassengerHandler;
import com.dvtsoftware.airline.booking.service.DatabaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Main Verticle for the Airline Booking System.
 * Sets up HTTP server, routing, and database initialization.
 */
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        // Configure Jackson to support LocalDateTime
        ObjectMapper mapper = DatabindCodec.mapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // Load configuration
        JsonObject configObj = config();
        if (configObj == null || configObj.isEmpty()) {
            configObj = new JsonObject();
        }
        final JsonObject config = configObj;
        
        JsonObject dbConfig = config.getJsonObject("database");
        if (dbConfig == null) {
            dbConfig = new JsonObject()
                    .put("url", "jdbc:h2:mem:airline_booking;DB_CLOSE_DELAY=-1;MODE=MySQL")
                    .put("driver_class", "org.h2.Driver")
                    .put("user", "sa")
                    .put("password", "")
                    .put("max_pool_size", 10);
        }

        // Create JDBC client
        JDBCClient jdbcClient = JDBCClient.createShared(vertx, dbConfig);

        // Create database service
        final DatabaseService databaseService = new DatabaseService(jdbcClient);

        // Initialize database
        databaseService.initializeDatabase()
                .compose(v -> {
                    // Create handlers
                    final AirlineHandler airlineHandler = new AirlineHandler(databaseService);
                    final FlightHandler flightHandler = new FlightHandler(databaseService);
                    final PassengerHandler passengerHandler = new PassengerHandler(databaseService);
                    final BookingsHandler bookingsHandler = new BookingsHandler(databaseService);

                    // Create router
                    Router router = Router.router(vertx);

                    // Body handler for parsing JSON
                    router.route().handler(BodyHandler.create());

                    // Health check endpoint
                    router.get("/health").handler(ctx -> {
                        ctx.response()
                                .putHeader("Content-Type", "application/json")
                                .end(new JsonObject().put("status", "UP").encodePrettily());
                    });

                    // Airlines endpoints
                    router.post("/airlines").handler(airlineHandler::createAirline);
                    router.get("/airlines").handler(airlineHandler::getAllAirlines);

                    // Flights endpoints - register specific routes before parameterized ones
                    router.post("/flights").handler(flightHandler::createFlight);
                    router.get("/flights/search").handler(flightHandler::searchFlights);
                    router.get("/flights/:id").handler(flightHandler::getFlightById);

                    // Passengers endpoints
                    router.post("/passengers").handler(passengerHandler::createPassenger);

                    // Bookings endpoints
                    router.post("/bookings").handler(bookingsHandler::createBooking);
                    router.get("/bookings/:id").handler(bookingsHandler::getBookingById);
                    router.delete("/bookings/:id").handler(bookingsHandler::cancelBooking);
                    router.get("/passengers/:id/bookings").handler(bookingsHandler::getBookingsByPassengerId);

                    // Error handler
                    router.errorHandler(500, ctx -> {
                        JsonObject error = new JsonObject()
                                .put("error", "Internal server error")
                                .put("statusCode", 500);
                        ctx.response()
                                .setStatusCode(500)
                                .putHeader("Content-Type", "application/json")
                                .end(error.encodePrettily());
                    });

                    // Start HTTP server
                    final JsonObject serverConfig = config.getJsonObject("server", new JsonObject());
                    final int port = serverConfig.getInteger("port", 8080);
                    final String host = serverConfig.getString("host", "0.0.0.0");

                    return vertx.createHttpServer()
                            .requestHandler(router)
                            .listen(port, host);
                })
                .onSuccess(server -> {
                    System.out.println("HTTP server started on port " + server.actualPort());
                    startPromise.complete();
                })
                .onFailure(error -> {
                    System.err.println("Failed to start server: " + error.getMessage());
                    error.printStackTrace();
                    startPromise.fail(error);
                });
    }

    public static void main(String[] args) {
        io.vertx.core.Vertx vertx = io.vertx.core.Vertx.vertx();
        
        // Load configuration from application.json
        io.vertx.config.ConfigStoreOptions fileStore = new io.vertx.config.ConfigStoreOptions()
                .setType("file")
                .setConfig(new JsonObject().put("path", "src/main/resources/application.json"));

        io.vertx.config.ConfigRetrieverOptions options = new io.vertx.config.ConfigRetrieverOptions()
                .addStore(fileStore);

        io.vertx.config.ConfigRetriever retriever = io.vertx.config.ConfigRetriever.create(vertx, options);
        
        retriever.getConfig()
                .onSuccess(config -> {
                    vertx.deployVerticle(new MainVerticle(), new io.vertx.core.DeploymentOptions().setConfig(config))
                            .onSuccess(id -> System.out.println("Deployed verticle: " + id))
                            .onFailure(error -> {
                                System.err.println("Failed to deploy verticle: " + error.getMessage());
                                error.printStackTrace();
                                System.exit(1);
                            });
                })
                .onFailure(error -> {
                    System.err.println("Failed to load configuration: " + error.getMessage());
                    error.printStackTrace();
                    // Deploy with default config
                    vertx.deployVerticle(new MainVerticle())
                            .onSuccess(id -> System.out.println("Deployed verticle with default config: " + id))
                            .onFailure(err -> {
                                System.err.println("Failed to deploy verticle: " + err.getMessage());
                                err.printStackTrace();
                                System.exit(1);
                            });
                });
    }
}

