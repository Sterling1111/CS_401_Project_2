package address.data;

/**
 * Stores the data necessary to represent an address in memory
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 3/14/21
 * */
public class Address {

    /**
     * Stores the data necessary to represent a street in memory
     */
    private String street;

    /**
     * Stores the data necessary to represent a city in memory
     */
    private String city;

    /**
     * Stores the data necessary to represent a state in memory
     */
    private String state;

    /**
     * Stores the data necessary to represent an ID in memory
     */
    private Integer zip;

    /**
     * Constructs a new Address according to the parameters passed
     * @param street a String whose address will be set to this.street
     * @param city a String whose address will be set to this.city
     * @param state a String whose address will be set to this.state
     * @param zip an Integer whose address will be set to this.street
     */
    public Address(String street, String city, String state, Integer zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    /**
     * returns that address of this.street
     * @return this.street
     */
    public String getStreet() { return street; }

    /**
     * returns the address of this.city
     * @return this.city
     */
    public String getCity() {
        return city;
    }

    /**
     * returns the address of this.state
     * @return this.state
     */
    public String getState() {
        return state;
    }

    /**
     * returns the address of this.zip
     * @return this.zip
     */
    public Integer getZip() {
        return zip;
    }

    /**
     * sets this.street to the address of the String object passed
     * @param street is an instance of String class
     */
    public void setStreet(String street) { this.street = street; }

    /**
     * sets this.city to the address of the String object passed
     * @param city is an instance of String class
     */
    public void setCity(String city) { this.city = city; }

    /**
     * sets this.state to the address of the String object passed
     * @param state is an instance of String class
     */
    public void setState(String state) { this.state = state; }

    /**
     * sets this.zip to the address of the Integer object passed
     * @param zip is an Integer object
     */
    public void setZip(Integer zip) { this.zip = zip; }

    @Override
    public String toString() {
        return street + "\n" + city + ", " + state + " " + zip;
    }

}
