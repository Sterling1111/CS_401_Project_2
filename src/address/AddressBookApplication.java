package address;

import address.gui.AddressBook;
import java.awt.EventQueue;

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
