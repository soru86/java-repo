package com.dvtsoftware.airline.booking;

import java.sql.SQLException;

import org.h2.tools.Server;

import com.dvtsoftware.airline.booking.handler.AirlineHandler;
import com.dvtsoftware.airline.booking.handler.BookingsHandler;
import com.dvtsoftware.airline.booking.handler.FlightHandler;
import com.dvtsoftware.airline.booking.handler.PassengerHandler;
import com.dvtsoftware.airline.booking.service.DatabaseAppService;
import com.dvtsoftware.airline.booking.service.DatabaseInitService;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
	private DatabaseInitService databaseInitService;
	private DatabaseAppService databaseAppService;
	private AirlineHandler airlineHandler;
	private FlightHandler flightHandler;
	private PassengerHandler passengerHandler;
	private BookingsHandler bookingsHandler;
	private Server webServer;

	@Override
	public void start(Promise<Void> startPromise) {
		try {
			createH2Console();
			initializeH2Database(startPromise);
		} catch (SQLException ex) {
			System.out.println("Error while setting up H2 DB..." + ex);
		}
	}

	private void createH2Console() throws SQLException {
		webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
	}

	private void initializeH2Database(Promise<Void> startPromise) {
		databaseInitService = new DatabaseInitService(vertx);
		
		// Initialize services after Vert.x is available
		databaseAppService = new DatabaseAppService(vertx);
		airlineHandler = new AirlineHandler(databaseAppService);
		flightHandler = new FlightHandler(databaseAppService);
		passengerHandler = new PassengerHandler(databaseAppService);
		bookingsHandler = new BookingsHandler(databaseAppService);

		// Initialize DB before starting HTTP server
		databaseInitService.initializeDatabase().onComplete(dbInit -> {
			if (dbInit.failed()) {
				startPromise.fail(dbInit.cause());
				return;
			}

			Router router = registerRouters();
			createHttpServer(startPromise, router);
		});
	}

	private Router registerRouters() {
		Router router = Router.router(vertx);
		// Airlines
		router.post("/airlines").handler(airlineHandler::addAirline);
		router.get("/airlines").handler(airlineHandler::listAirlines);

		// Flights
		router.post("/flights").handler(flightHandler::addFlight);
		router.get("/flights/:id").handler(flightHandler::getFlightById);
		router.get("/flights/search").handler(flightHandler::searchFlights);

		// Passengers
		router.post("/passengers").handler(passengerHandler::addPassenger);

		// Bookings
		router.post("/bookings").handler(bookingsHandler::createBooking);
		router.get("/bookings/:id").handler(bookingsHandler::getBookingById);
		router.delete("/bookings/:id").handler(bookingsHandler::cancelBooking);
		router.get("/passengers/:id/bookings").handler(bookingsHandler::listPassengerBookings);
		return router;
	}

	private void createHttpServer(Promise<Void> startPromise, Router router) {
		vertx.createHttpServer()
				.requestHandler(router)
				.listen(8080)
				.onSuccess(server -> {
					System.out.println("HTTP server started on port 8080");
					startPromise.complete();
				})
				.onFailure(startPromise::fail);
	}

	@Override
	public void stop() throws Exception {
		if (webServer != null) {
			webServer.stop();
		}
	}

	public static void main(String[] args) {
		io.vertx.core.Vertx vertx = io.vertx.core.Vertx.vertx();
		vertx.deployVerticle(new MainVerticle());
	}
}