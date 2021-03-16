package address;

import address.gui.AddressBook;
import java.awt.EventQueue;

/**
 * Runs the application
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 3/14/21
 * */
public class AddressBookApplication {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    AddressBook ab = new AddressBook();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }
}
