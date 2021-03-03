package address.data;

public class Address {

    private String street;
    private String city;
    private String state;
    private Integer zip;

    public Address(String street, String city, String state, Integer zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getStreet() { return street; }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setStreet(String street) { this.street = street; }

    public void setCity(String city) { this.city = city; }

    public void setState(String state) { this.state = state; }

    public void setZip(Integer zip) { this.zip = zip; }

    @Override
    public String toString() {
        return street + "\n" + city + ", " + state + " " + zip;
    }

}
