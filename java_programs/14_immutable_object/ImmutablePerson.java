import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Create Immutable Object in Java
 * 
 * This program demonstrates how to create an immutable class in Java.
 * An immutable object is an object whose state cannot be changed after creation.
 * 
 * Rules for creating immutable class:
 * 1. Make the class final
 * 2. Make all fields final and private
 * 3. Don't provide setter methods
 * 4. Initialize all fields via constructor
 * 5. Perform deep copy for mutable objects
 * 6. Return defensive copies of mutable objects
 */
public final class ImmutablePerson {
    
    // All fields are final and private
    private final String name;
    private final int age;
    private final Date birthDate;
    private final List<String> hobbies;
    private final Address address;
    
    /**
     * Constructor - performs deep copy for mutable objects
     */
    public ImmutablePerson(String name, int age, Date birthDate, List<String> hobbies, Address address) {
        this.name = name;
        this.age = age;
        
        // Deep copy for Date (mutable object)
        if (birthDate != null) {
            this.birthDate = new Date(birthDate.getTime());
        } else {
            this.birthDate = null;
        }
        
        // Deep copy for List (mutable object)
        if (hobbies != null) {
            this.hobbies = Collections.unmodifiableList(new ArrayList<>(hobbies));
        } else {
            this.hobbies = Collections.emptyList();
        }
        
        // Deep copy for Address (mutable object)
        if (address != null) {
            this.address = new Address(address.getStreet(), address.getCity(), address.getZipCode());
        } else {
            this.address = null;
        }
    }
    
    // Only getter methods, no setters
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    /**
     * Return defensive copy of Date
     */
    public Date getBirthDate() {
        if (birthDate == null) {
            return null;
        }
        return new Date(birthDate.getTime());
    }
    
    /**
     * Return unmodifiable list (defensive copy)
     */
    public List<String> getHobbies() {
        return Collections.unmodifiableList(new ArrayList<>(hobbies));
    }
    
    /**
     * Return defensive copy of Address
     */
    public Address getAddress() {
        if (address == null) {
            return null;
        }
        return new Address(address.getStreet(), address.getCity(), address.getZipCode());
    }
    
    @Override
    public String toString() {
        return "ImmutablePerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthDate=" + birthDate +
                ", hobbies=" + hobbies +
                ", address=" + address +
                '}';
    }
    
    /**
     * Mutable Address class (used as example of mutable field)
     */
    public static class Address {
        private String street;
        private String city;
        private String zipCode;
        
        public Address(String street, String city, String zipCode) {
            this.street = street;
            this.city = city;
            this.zipCode = zipCode;
        }
        
        public String getStreet() {
            return street;
        }
        
        public void setStreet(String street) {
            this.street = street;
        }
        
        public String getCity() {
            return city;
        }
        
        public void setCity(String city) {
            this.city = city;
        }
        
        public String getZipCode() {
            return zipCode;
        }
        
        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
        
        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", city='" + city + '\'' +
                    ", zipCode='" + zipCode + '\'' +
                    '}';
        }
    }
}


