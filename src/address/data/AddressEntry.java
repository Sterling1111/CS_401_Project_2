package address.data;

/**
 * Stores the data necessary to represent an address entry in memory
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 3/14/21
 * */
public class AddressEntry implements Comparable<AddressEntry>{

    /**
     * Stores the data necessary to represent a name in memory
     * @see Name
     */
    private Name name;
    /**
     * Stores the data necessary to represent an address in memory
     * @see Address
     */
    private Address address;
    /**
     * Stores the data necessary to represent an email address in memory
     */
    private String email;
    /**
     * Stores the data necessary to represent a phone number in memory
     */
    private String phone;
    /**
     * Stores the data necessary to represent an integer ID in memory
     */
    private Integer ID;

    /**
     * Constructs a new address entry according to the parameters passed
     * @param name an instance of Name object whose address will be set to this.name
     * @param address an instance of address object whose address will be set to this.address
     * @param email a String whose address will be set to this.email
     * @param phone a String whose address will be set to this.phone
     * @param ID an Integer whose address will be set to this.ID
     */
    public AddressEntry(Name name, Address address, String email, String phone, Integer ID) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.ID = ID;
    }

    /**
     * Constructs a new name, address, and address entry according to the parameters passed
     * @param firstName a String whose address will be passed to Name constructor to construct this.name
     * @param lastName a String whose address will be passed to Name constructor to construct this.name
     * @param street a String whose address will be passed to Address constructor to construct this.address
     * @param city a String whose address will be passed to Address constructor to construct this.address
     * @param state a String whose address will be passed to Address constructor to construct this.address
     * @param zip an Integer whose address will be passed to Address constructor to construct this.address
     * @param email a String whose address will be set to this.email
     * @param phone a String whose address will be set to this.phone
     * @param ID an Integer whose address will be set to this.ID
     */
    public AddressEntry(String firstName, String lastName, String street, String city,
                        String state, Integer zip, String email, String phone, Integer ID) {
        name = new Name(firstName, lastName);
        address = new Address(street, city, state, zip);
        this.email = email;
        this.phone = phone;
        this.ID = ID;
    }

    /**
     * Constructs a new address entry according the fields in parameters passed
     * @param ae an instance of AddressEntry whose fields will be used to call @see #AddressEntry(Name, Address, String, String, ID)
     */
    public AddressEntry(AddressEntry ae) {
        this(ae.getName(), ae.getAddress(), ae.getEmail(), ae.getPhone(), ae.getID());
    }

    /**
     * returns the address of this.name
     * @return this.name
     */
    public Name getName() { return name; }

    /**
     * returns the address of this.address
     * @return this.address
     */
    public Address getAddress() { return address; }

    /**
     * returns the address of this.email
     * @return this.email
     */
    public String getEmail() { return email; }

    /**
     * returns the address of this.phone
     * @return this.phone
     */
    public String getPhone() { return phone; }

    /**
     * returns the address of this.ID
     * @return ID
     */
    public Integer getID() { return ID; }

    /**
     * sets this.name to the address of the Name object passed
     * @param name is an instance of Name class
     */
    public void setName(Name name) { this.name = name; }

    /**
     * sets this.firstName and this.lastName to the respective address of the Strings passed
     * @param firstName a String whose address be set to this.firstName
     * @param lastName a String whose address will be set to this.lastName
     */
    public void setName(String firstName, String lastName) {
        name.setFirstName(firstName);
        name.setLastName(lastName);
    }

    /**
     * sets this.address to the address of the Address object passed
     * @param address is an instance of Address class
     */
    public void setAddress(Address address) { this.address = address; }

    /**
     * sets the fields of this.address to the addresses of the strings passed
     * @param street a String whose address will be set to this.street
     * @param city a String whose address will be set to this.city
     * @param state a String whose address will be set to this.state
     * @param zip an Integer whose address will be set to this.ID
     */
    public void setAddress(String street, String city, String state, Integer zip) {
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
    }

    /**
     * sets this.email to the address of the String passed
     * @param email is an instance of String class
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * sets this.phone to the address of the String passed
     * @param phone is an instance of String class
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * sets this.ID to the address of the Integer passed
     * @param ID is an instance of Integer class
     */
    public void setID(Integer ID) { this.ID = ID; }

    //prints last name before first name
    @Override
    public String toString() {
        return name.getLastName() + ", " + name.getFirstName();
    }

    @Override
    public int compareTo(AddressEntry other) {
        if(this.name.getLastName().compareTo(other.name.getLastName()) != 0)
            return this.name.getLastName().compareTo(other.name.getLastName());
        else if(this.name.getFirstName().compareTo(other.name.getFirstName()) == 0 &&
                this.address.getCity().compareTo(other.address.getCity()) == 0 &&
                this.phone.compareTo(other.phone) == 0 &&
                this.address.getState().compareTo(other.address.getState()) == 0 &&
                this.address.getStreet().compareTo(other.address.getStreet()) == 0 &&
                this.email.compareTo(other.email) == 0 &&
                this.address.getZip().compareTo(other.address.getZip()) == 0) {
            return 0;
        }
        else
            return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof AddressEntry))
            return false;
        return this.compareTo((AddressEntry)obj) == 0;
    }
}
