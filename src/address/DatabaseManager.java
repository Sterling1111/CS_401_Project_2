package address;

import address.data.Address;
import address.data.AddressEntry;
import address.data.Name;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseManager {
    private String userName = "";
    private String password = "";
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rset = null;

    public DatabaseManager() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            getCredentials();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

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
