package address.data;

import address.data.Address;
import address.data.AddressEntry;
import address.data.Name;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides capability to load, add, update, and delete entries from a database.
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 3/14/21
 * */
public class DatabaseManager {
    /**
     * Stores the userName which is required to establish a connection to the server.
     */
    private String userName = "";
    /**
     * Stores the password which is required to establish a connection to the server.
     */
    private String password = "";
    /**
     * Holds the address of Various connection sessions as needed.
     */
    private Connection conn = null;
    /**
     * Holds the address of various statements needed to query the database.
     */
    private Statement stmt = null;
    /**
     * Holds the address of various prepared statements needed to query the database.
     */
    private PreparedStatement pstmt = null;
    /**
     * Holds the address of various result sets from various database queries.
     */
    private ResultSet rset = null;

    /**
     * Constructs a database manager object and loads the oracle Driver class
     * It also reads sets {@link DatabaseManager#userName} and {@link DatabaseManager#password}
     * with a call to {@link DatabaseManager#getCredentials()}
     */
    public DatabaseManager() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            getCredentials();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Loads two strings from file "credentials.txt" which is located in Project folder.
     * The two items read from the file will be strings and they will initialize data-member
     * {@link DatabaseManager#userName} and {@link DatabaseManager#password}.
     */
    public void getCredentials() {
        try {
            File file = new File("credentials.txt");
            Scanner inputFile = new Scanner(file);
            userName = inputFile.nextLine();
            password = inputFile.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establishes a connection to the database and retrieves all of the data fields. These data
     * fields will be used to create a series of {@link AddressEntry} instances. These will be loaded into
     * an ArrayList of AddressEntry. Then the ArrayList will be returned.
     * @return {@link ArrayList<AddressEntry>} which represent all of the data that is in the database.
     */
    public ArrayList<AddressEntry> getAllEntries() {
        try {
            ArrayList<AddressEntry> entries = new ArrayList<>();

            conn = DriverManager.getConnection("jdbc:oracle:thin:" + userName + "/" + password + "@adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu");
            stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT * FROM ADDRESSENTRYTABLE");
            while(rset.next()) {
                int ID = rset.getInt("ID");
                String firstName = rset.getString("FIRSTNAME");
                String lastName = rset.getString("LASTNAME");
                String street = rset.getString("STREET");
                String city = rset.getString("CITY");
                String state = rset.getString("STATE");
                int zip = rset.getInt("ZIP");
                String email = rset.getString("EMAIL");
                String phone = rset.getString("PHONE");
                Name name = new Name(firstName, lastName);
                Address address = new Address(street, city, state, zip);
                AddressEntry entry = new AddressEntry(name, address, email, phone, ID);
                entries.add(entry);
            }
            rset.close();
            stmt.close();
            conn.close();
            return entries;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Establishes a connection to the database and adds the data which is contained in the parameter to the database.
     * When the entry is added the database provides an auto-generated ID and returns this ID. Then method {@link AddressEntry#setID(Integer)}
     * with the ID that was returned from the database. This is done because now we will be able to find the ID in the database from the ID field
     * in the AddressEntry and we also do not have to worry about primary key clashes from adding our own ID's.
     * @param entry an AddressEntry where the fields are to be added to the database.
     */
    public void addAddressEntry(AddressEntry entry) {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:" + userName + "/" + password + "@adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu");
            pstmt = conn.prepareStatement(
                    "INSERT INTO ADDRESSENTRYTABLE " +
                            "(FIRSTNAME, LASTNAME, STREET, CITY, STATE, ZIP, EMAIL, PHONE) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new String[]{"ID"});
            pstmt.setString(1, entry.getName().getFirstName());
            pstmt.setString(2, entry.getName().getLastName());
            pstmt.setString(3, entry.getAddress().getStreet());
            pstmt.setString(4, entry.getAddress().getCity());
            pstmt.setString(5, entry.getAddress().getState());
            pstmt.setInt(6, entry.getAddress().getZip());
            pstmt.setString(7, entry.getEmail());
            pstmt.setString(8, entry.getPhone());

            int affectedRows = pstmt.executeUpdate();

            if(affectedRows == 0)
                throw new SQLException("Creating new AddressEntry failed. No rows inserted");

            rset = pstmt.getGeneratedKeys();

            if(rset.next()) {
                int id = rset.getInt(1);
                System.out.println(id);
                entry.setID(id);
            }
            else {
                System.out.println("Sorry no can do");
            }
            rset.close();
            pstmt.close();
            conn.close();

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes the address entry provided from the database.
     * @param entry the entry to be deleted from the database.
     */
    public void deleteAddressEntry(AddressEntry entry) {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:" + userName + "/" + password + "@adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu");
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM ADDRESSENTRYTABLE WHERE ID = " + entry.getID());
            stmt.close();
            conn.close();

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the entry in the database to which has the same ID as the entry provided in the parameter to contain
     * all the same data fields as in the parameter.
     * @param entry an AddressEntry that contains an identifying ID and fields that we wish to change the fields in the database where the ID matches to match.
     */
    public void updateAddressEntry(AddressEntry entry) {
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:" + userName + "/" + password + "@adcsdb01.csueastbay.edu:1521/mcspdb.ad.csueastbay.edu");
            pstmt = conn.prepareStatement("UPDATE ADDRESSENTRYTABLE " +
                    "SET FIRSTNAME = ?, LASTNAME = ?, STREET = ?, CITY = ?, STATE = ?, ZIP = ?, EMAIL = ?, PHONE = ? " +
                    "WHERE ID = ?");
            pstmt.setString(1, entry.getName().getFirstName());
            pstmt.setString(2, entry.getName().getLastName());
            pstmt.setString(3, entry.getAddress().getStreet());
            pstmt.setString(4, entry.getAddress().getCity());
            pstmt.setString(5, entry.getAddress().getState());
            pstmt.setInt(6, entry.getAddress().getZip());
            pstmt.setString(7, entry.getEmail());
            pstmt.setString(8, entry.getPhone());
            pstmt.setInt(9, entry.getID());

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
