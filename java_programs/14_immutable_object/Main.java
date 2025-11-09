import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Main class to demonstrate Immutable Object
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Create Immutable Object in Java ===");
        
        // Create mutable objects
        Date birthDate = new Date();
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        hobbies.add("Coding");
        hobbies.add("Swimming");
        
        ImmutablePerson.Address address = new ImmutablePerson.Address(
            "123 Main St", "New York", "10001"
        );
        
        // Create immutable person
        ImmutablePerson person = new ImmutablePerson(
            "John Doe", 30, birthDate, hobbies, address
        );
        
        System.out.println("\nOriginal Immutable Person:");
        System.out.println(person);
        
        // Try to modify the original mutable objects
        System.out.println("\n=== Attempting to modify original objects ===");
        
        // Modify date
        birthDate.setTime(System.currentTimeMillis() + 1000000);
        System.out.println("Modified original date: " + birthDate);
        System.out.println("Person's date (should be unchanged): " + person.getBirthDate());
        
        // Modify hobbies list
        hobbies.add("Gaming");
        System.out.println("\nModified original hobbies: " + hobbies);
        System.out.println("Person's hobbies (should be unchanged): " + person.getHobbies());
        
        // Try to modify returned hobbies
        List<String> returnedHobbies = person.getHobbies();
        try {
            returnedHobbies.add("New Hobby"); // This should fail
        } catch (UnsupportedOperationException e) {
            System.out.println("\nCannot modify returned hobbies list (UnmodifiableList): " + e.getMessage());
        }
        
        // Modify address
        address.setCity("Los Angeles");
        System.out.println("\nModified original address: " + address);
        System.out.println("Person's address (should be unchanged): " + person.getAddress());
        
        // Try to modify returned address
        ImmutablePerson.Address returnedAddress = person.getAddress();
        returnedAddress.setCity("Chicago");
        System.out.println("\nModified returned address: " + returnedAddress);
        System.out.println("Person's address (should still be unchanged): " + person.getAddress());
        
        System.out.println("\n=== Final state of Immutable Person ===");
        System.out.println(person);
        
        System.out.println("\n✓ Immutable object successfully created - all modification attempts failed!");
    }
}


