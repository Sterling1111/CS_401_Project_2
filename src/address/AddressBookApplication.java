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
    /**
     * the main function of the program
     * @param args command line arguments. They will not be used.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            /**
             * The starting point of the application
             */
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
