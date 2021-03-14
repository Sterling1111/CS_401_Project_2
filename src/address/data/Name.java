package address.data;

/**
 * Stores the data necessary to represent a name in memory
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 3/14/21
 * */
public class Name {

    /**
     * stores the data necessary to represent a first name in memory
     */
    private String firstName;
    /**
     * stores the data necessary to represent a last name in memory
     */
    private String lastName;

    /**
     * constructs a new name according to the parameters passed
     * @param firstName a String whose address will be set to this.firstName
     * @param lastName a String whose address will be set to this.lastName
     */
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * returns the address of this.firstName
     * @return this.firstName
     */
    public String getFirstName() { return firstName; }

    /**
     * returns the address of this.lastName
     * @return this.lastName
     */
    public String getLastName() { return lastName; }

    /**
     * sets this.firstName to the address of the String passed
     * @param firstName is an instance of String class
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * sets this.lastName to the address of the string passed
     * @param lastName is an instance of String class
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
