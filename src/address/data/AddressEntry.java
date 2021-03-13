package address.data;

public class AddressEntry implements Comparable<AddressEntry>{

    private Name name;
    private Address address;
    private String email;
    private String phone;
    private Integer ID;

    public AddressEntry(Name name, Address address, String email, String phone, Integer ID) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.ID = ID;
    }

    public AddressEntry(String firstName, String lastName, String street, String city,
                        String state, Integer zip, String email, String phone, Integer ID) {
        name = new Name(firstName, lastName);
        address = new Address(street, city, state, zip);
        this.email = email;
        this.phone = phone;
        this.ID = ID;
    }

    public AddressEntry(AddressEntry ae) {
        this(ae.getName(), ae.getAddress(), ae.getEmail(), ae.getPhone(), ae.getID());
    }

    public Name getName() { return name; }

    public Address getAddress() { return address; }

    public String getEmail() { return email; }

    public String getPhone() { return phone; }

    public Integer getID() { return ID; }

    public void setName(Name name) { this.name = name; }

    public void setName(String firstName, String lastName) {
        name.setFirstName(firstName);
        name.setLastName(lastName);
    }

    public void setAddress(Address address) { this.address = address; }

    public void setAddress(String street, String city, String state, Integer zip) {
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
    }

    public void setEmail(String email) { this.email = email; }

    public void setPhone(String phone) { this.phone = phone; }

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
