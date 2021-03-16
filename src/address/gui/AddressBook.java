package address.gui;

import address.data.Address;
import address.data.DatabaseManager;
import address.data.AddressEntry;
import address.data.Name;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.sql.*;


/**
 * provides the necessary functions and data to display a basic address book on screen
 * In addition to displaying the components it contains functions and data necessary to
 * store, update, add, delete, and modify the member of the address book in computer
 * memory and also in a database.
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 3/14/21
 * */
public class AddressBook extends JFrame implements ListSelectionListener {

    /**
     * contains all other panels necessary to implement the layout
     */
    private JPanel panel1;
    /**
     * allows for typing in of text data which represents a last name substring to be searched for
     */
    private JTextField findTF;
    /**
     *  a button which when clicked on triggers the ActionEvent contained in function {@link AddressBook#setListBEventHandlers()}
     */
    private JButton listB;
    /**
     * a button which when clicked triggers the ActionEvent contained in function {@link AddressBook#setListBEventHandlers()}
     */
    private JButton findB;
    /**
     * displays address entries to the screen
     */
    private JList<AddressEntry> addressEntryJList;
    /**
     * a text field which displays firstName of the currently selected address entry. Can also be used to enter in
     * string data to add or modify address entries.
     */
    private JTextField firstNameTF;
    /**
     * a text field which displays lastName of the currently selected address entry. Can also be used to enter in
     * string data to add or modify address entries.
     */
    private JTextField lastNameTF;
    /**
     * a text field which displays street of the currently selected address entry. Can also be used to enter in
     * string data to add or modify address entries.
     */
    private JTextField streetTF;
    /**
     * a text field which displays city of the currently selected address entry. Can also be used to enter in
     * string data to add or modify address entries.
     */
    private JTextField cityTF;
    /**
     * a text field which displays state of the currently selected address entry. Can also be used to enter in
     * string data to add or modify address entries.
     */
    private JTextField stateTF;
    /**
     * a text field which displays zip of the currently selected address entry. Can also be used to enter in
     * string data to add or modify address entries.
     */
    private JTextField zipTF;
    /**
     * a text field which displays email address of the currently selected address entry. Can also be used to enter in
     * string data to add or modify address entries.
     */
    private JTextField emailTF;
    /**
     * a text field which displays phone number of the currently selected address entry. Can also be used to enter in
     * string data to add or modify address entries.
     */
    private JTextField phoneTF;
    /**
     * a label which reads "Find entries by last name:" is denotes the purpose of {@link AddressBook#findTF}
     */
    private JLabel findL;
    /**
     * a label which reads "First Name:" it labels the data displayed in {@link AddressBook#firstNameTF}
     */
    private JLabel firstNameL;
    /**
     * a label which reads "Last Name:" it labels the data displayed in {@link AddressBook#lastNameTF}
     */
    private JLabel lastNameL;
    /**
     * a label which reads "Street:" it labels the data displayed in {@link AddressBook#streetTF}
     */
    private JLabel streetL;
    /**
     * a label which reads "City:" it labels the data displayed in {@link AddressBook#cityTF}
     */
    private JLabel cityL;
    /**
     * a label which reads "State:" it labels the data displayed in {@link AddressBook#stateTF}
     */
    private JLabel stateL;
    /**
     * a label which reads "Zip Code:" it labels the data displayed in {@link AddressBook#zipTF}
     */
    private JLabel zipL;
    /**
     * a label which reads "Email:" it labels the data displayed in {@link AddressBook#emailTF}
     */
    private JLabel emailL;
    /**
     * a label which reads "Phone:" it labels the data displayed in {@link AddressBook#phoneTF}
     */
    private JLabel phoneL;
    /**
     * a button which when pressed triggers event described in {@link AddressBook#setAddBEventHandlers()}
     */
    private JButton addB;
    /**
     * a button which when clicked on triggers event described in {@link AddressBook#setDeleteBEventHandlers()}
     */
    private JButton deleteB;
    /**
     * a button which when clicked on triggers event described in {@link AddressBook#setUpdateBEventHandlers()}
     * */
    private JButton updateB;
    /**
     * linked to {@link AddressBook#addressEntryJList} it contains the data that the JList displays
     */
    private ListModel listModel;
    /**
     * stores all of the entries so that they can be quickly retrieved from memory
     */
    private final TreeMap<String, TreeSet<AddressEntry>> addressEntryList = new TreeMap<>();
    /**
     * allows for adding, removing, updating, and retrieving address entries from database
     */
    private final DatabaseManager databaseManager = new DatabaseManager();

    /**
     *Constructs a new address book. This includes
     * setting the title of the main application window to "Address Book",
     * setting the content pane to the main panel, panel 1,
     * setting the default close operation to "EXIT_ON_CLOSE",
     * packing the window,
     * initializing listModel,
     * linking addressEntryJList with listModel,
     * setting the event handlers {@link AddressBook#setEventHandlers()},
     * setting the text fields immutable {@link AddressBook#setTextFieldsImmutable()},
     * initializing the address book {@link AddressBook#initAddressBook()}, and
     * setting the window to be visible.
     */
    public AddressBook()  {
        setTitle("Address Book");
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        listModel = new ListModel();
        addressEntryJList.setModel(listModel);
        setEventHandlers();
        setTextFieldsImmutable();
        initAddressBook();
        setVisible(true);
    }

    /**
     * Obtains ArrayList of AddressEntry from {@link DatabaseManager#getAllEntries()}.
     * Iterates through this array and adds the entries to {@link AddressBook#listModel} with
     * {@link ListModel#add(AddressEntry)}. Also adds entries to {@link AddressBook#addressEntryList}
     * with {@link AddressBook#add(AddressEntry)}.
     * Finally tells {@link AddressBook#addressEntryJList} to select the first of these entries.
     */
    public void initAddressBook() {
        ArrayList<AddressEntry> entries = databaseManager.getAllEntries();
        for(AddressEntry entry : entries) {
            this.add(entry);
            listModel.add(new AddressEntry(entry));
        }
        addressEntryJList.setSelectedIndex(0);
    }

    /**
     * Clears selection from {@link AddressBook#addressEntryJList} with a call to {@link JList#clearSelection()}.
     * Clears selection from {@link AddressBook#listModel} with a call to {@link ListModel#clear()}.
     * Clears all text from text fields with a call to {@link AddressBook#clearEntryTF()}. Sets {@link AddressBook#listModel}
     * to a new {@link ListModel}. Iterates through all of the {@link AddressEntry} contained in
     * {@link AddressBook#addressEntryList} and adds them
     * to the list model. Finally tells {@link AddressBook#addressEntryJList} to select the first of these entries with
     * {@link JList#setSelectedIndex(int)}
     * @param addressEntryList is a TreeMap which contains a Set of AddressEntry objects.
     */
    public void refreshList(TreeMap<String, TreeSet<AddressEntry>> addressEntryList) {
        addressEntryJList.clearSelection();
        listModel.clear();
        clearEntryTF();
        listModel = new ListModel();
        addressEntryJList.setModel(listModel);
        for(Map.Entry<String, TreeSet<AddressEntry>> entry : addressEntryList.entrySet()) {
            for(AddressEntry item : entry.getValue()) {
                if(item != null) {
                    listModel.add(new AddressEntry(item));
                }
            }
        }
        addressEntryJList.setSelectedIndex(0);
    }

    /**
     * Sets the text fields {@link AddressBook#firstNameTF}, {@link AddressBook#lastNameTF}, {@link AddressBook#cityTF},
     * {@link AddressBook#stateTF}, {@link AddressBook#streetTF}, {@link AddressBook#zipTF},
     * {@link AddressBook#phoneTF}, {@link AddressBook#emailTF} to the empty string("").
     */
    public void clearEntryTF() {
        firstNameTF.setText("");
        lastNameTF.setText("");
        cityTF.setText("");
        stateTF.setText("");
        streetTF.setText("");
        zipTF.setText("");
        phoneTF.setText("");
        emailTF.setText("");
    }

    /**
     * Adds an instance of {@link AddressEntry} to {@link AddressBook#addressEntryList}. If the {@link address.data.Name#getLastName()}
     * which is contained in {@link AddressEntry#getName()} has never before been used as a key in the addressEntryList,
     * then a new {@link TreeSet<AddressEntry>} is added to addressEntryList which contains as a key the last name of AddressEntry. Otherwise
     * the AddressEntry is added to that TreeSet which already contains address entries that have that last name.
     * @param entry is an {@link AddressEntry} object to be added to {@link AddressBook#addressEntryList}
     */
    private void add(AddressEntry entry) {
        addressEntryList.computeIfAbsent(entry.getName().getLastName(), k -> new TreeSet<>()).add(entry);
    }

    /**
     * Returns a set of all those address entries where some prefix of the last names of all the entries matches the parameter passed
     * @param startOf_lastName is a String which specifies the prefix of the last name of all entries to be returned in the Set.
     * @return a {@link TreeSet<AddressEntry>} where every {@link AddressEntry} in the set have last names with prefixes that exactly
     * math the parameter passed.
     */
    private TreeSet<AddressEntry> getPrefixSet(String startOf_lastName) {
        SortedMap<String, TreeSet<AddressEntry>> tempMap;
        TreeSet<AddressEntry> tempSet = new TreeSet<>();
        tempMap = addressEntryList.subMap(startOf_lastName, startOf_lastName + Character.MAX_VALUE);

        for(Map.Entry<String, TreeSet<AddressEntry>> entry : tempMap.entrySet()) {
            tempSet.addAll(entry.getValue());
        }
        return tempSet;
    }

    /**
     * Populates text fields with specified entry details
     * @param ae is the {@link AddressEntry} who's fields will be displayed in the text fields
     */
    private void populateTextFields(AddressEntry ae) {
        firstNameTF.setText(ae.getName().getFirstName());
        lastNameTF.setText(ae.getName().getLastName());
        cityTF.setText(ae.getAddress().getCity());
        stateTF.setText(ae.getAddress().getState());
        streetTF.setText(ae.getAddress().getStreet());
        zipTF.setText(String.valueOf(ae.getAddress().getZip()));
        phoneTF.setText(ae.getPhone());
        emailTF.setText(ae.getEmail());
    }

    /**
     * Established the behavior for {@link AddressBook#addressEntryJList} in the event that a new selection is made in the list.
     * The behavior is that whichever element in the JList is currently selected will have all of its fields except for ID displayed
     * to the JTextFields. This method is the implementation of of method in interface ListSelectionListener which {@link AddressBook} implements.
     * @param e an event that characterizes a change in a selection
     */
    public void valueChanged(ListSelectionEvent e) {
        setTextFieldsImmutable();
        AddressEntry ae = listModel.getElementAt(addressEntryJList.getSelectedIndex());
        if(ae != null) {
            populateTextFields(ae);
        }
    }

    /**
     * Determines whether the TextFields {@link AddressBook#firstNameTF}, {@link AddressBook#lastNameTF}, {@link AddressBook#cityTF},
     * {@link AddressBook#stateTF}, {@link AddressBook#streetTF}, {@link AddressBook#zipTF},
     * {@link AddressBook#phoneTF}, and {@link AddressBook#emailTF} can be edited by the user or not.
     * @param mutability A boolean value which if true allows the JTextFields to be edited and if false does not allow them to be edited.
     */
    private void setTextFieldsMutability(boolean mutability) {
        firstNameTF.setEditable(mutability);
        lastNameTF.setEditable(mutability);
        cityTF.setEditable(mutability);
        stateTF.setEditable(mutability);
        streetTF.setEditable(mutability);
        zipTF.setEditable(mutability);
        phoneTF.setEditable(mutability);
        emailTF.setEditable(mutability);
    }

    /**
     * Calls method {@link AddressBook#setTextFieldsMutability(boolean)} with the value false to prevent the
     * JTextFields from being changed by the user.
     */
    public void setTextFieldsImmutable() {
       setTextFieldsMutability(false);
    }

    /**
     * Calls method {@link AddressBook#setTextFieldsMutability(boolean)} with the value true to allow the
     * JTextFields to be changed by the user.
     */
    public void setTextFieldsMutable() {
        setTextFieldsMutability(true);
    }

    /**
     * Returns a Boolean depending on if any text fields are left blank
     * @return true if any field is blank and false if all fields are filled out
     */
    private Boolean checkForBlankTF() {
        if (firstNameTF.getText().isBlank()) {
            return true;
        } else if (lastNameTF.getText().isBlank()) {
            return true;
        } else if (streetTF.getText().isBlank()) {
            return true;
        } else if (cityTF.getText().isBlank()) {
            return true;
        } else if (stateTF.getText().isBlank()) {
            return true;
        } else if (zipTF.getText().isBlank()) {
            return true;
        } else if (emailTF.getText().isBlank()) {
            return true;
        } else if(phoneTF.getText().isBlank()) {
            return true;
        } else return false;
    }
    /**
     * Pulls the data from {@link AddressBook#firstNameTF}, {@link AddressBook#lastNameTF}, {@link AddressBook#cityTF},
     * {@link AddressBook#stateTF}, {@link AddressBook#streetTF}, {@link AddressBook#zipTF},
     * {@link AddressBook#phoneTF}, and {@link AddressBook#emailTF}, and creates a new {@link AddressEntry} out of them
     * with a call to {@link AddressEntry#AddressEntry(Name, Address, String, String, Integer)}
     * @return the {@link AddressEntry} that was just created.
     */
    public AddressEntry constructEntryFromFields() {
        return new AddressEntry(
                new address.data.Name(
                        firstNameTF.getText(),
                        lastNameTF.getText()
                ),
                new address.data.Address(
                        streetTF.getText(),
                        cityTF.getText(),
                        stateTF.getText(),
                        Integer.parseInt(zipTF.getText())
                ),
                emailTF.getText(),
                phoneTF.getText(),
                1);
    }

    /**
     * Returns the {@link AddressEntry} which is found at the index specified by the parameter.
     * @param index An index of {@link AddressBook#addressEntryList}
     * @return the {@link AddressEntry} which is found at the index specified by the parameter. If the index
     * specified is greater than the number of AddressEntry objects - 1 then null is returned.
     */
    public AddressEntry getElementAt(int index) {
        for (Map.Entry<String, TreeSet<AddressEntry>> entry : addressEntryList.entrySet()) {
            index -= entry.getValue().size();
            if(index < 0) {
                index = entry.getValue().size() - Math.abs(index);
                for(AddressEntry elem : entry.getValue()) {
                    if(index == 0)
                        return elem;
                    index--;
                }
            }
        }
        return null;
    }

    /**
     * Removed the {@link AddressEntry} object from {@link AddressBook#addressEntryList}. If this is the last
     * AddressEntry to be found with where the last name is the same as the last name of the parameter passed in,
     * then the {@link TreeSet} which contained the AddressEntry is also removed. This method should only be called
     * by {@link AddressBook#setElementAt(AddressEntry, int)}. This is because the method is meant to be used for remapping
     * addressEntryList in the case that the lastName of an AddressEntry is modified. If this occurs then because the lastName
     * is the key for the map, the AddressEntry would be lost. However, since we know that the addressEntry will be immediately
     * re-inserted into the map it doesn't make sense to have the database do all this extra work to remove it only to add it back in.
     * Therefore if the function was used to permanently remove an AddressEntry from the addressEntryList, there would be an
     * inconsistency between the data in the database and the data in the addressEntryList.
     * @param entry an entry whose fields(with the exception of ID) all must match the entry to be removed.
     */
    public void removeElement(AddressEntry entry) {
        String lastName = entry.getName().getLastName();
        if(addressEntryList.computeIfPresent(lastName, (k, v) -> {
            v.remove(entry);
            if(v.isEmpty())
                return null;
            else return v;
        }) != null) {
            return;
        }
        addressEntryList.remove(lastName);
    }

    /**
     * Sets the fields of the AddressEntry in addressEntryList at the specified index to match the fields of entry
     * @param entry the entry that will determine the fields at the AddressEntry at the specified index.
     * @param index the index of addressEntry at which the AddressEntry is to be modified.
     */
    public void setElementAt(AddressEntry entry, int index) {
        AddressEntry ae = getElementAt(index);
        if(entry.getName().getLastName().compareTo(ae.getName().getLastName()) != 0) {
            removeElement(ae);
            ae.setName(entry.getName());
            ae.setAddress(entry.getAddress());
            ae.setEmail(entry.getEmail());
            ae.setPhone(entry.getPhone());
            add(ae);
        }
        else {
            ae.setName(entry.getName());
            ae.setAddress(entry.getAddress());
            ae.setEmail(entry.getEmail());
            ae.setPhone(entry.getPhone());
        }
        databaseManager.updateAddressEntry(ae);
    }

    /**
     * returns the index of {@link AddressBook#addressEntryList} where the AddressEntry found matches the parameter passed
     * @param ae an AddressEntry which is to be found in the addressEntryList
     * @return the index in addressEntryList where the addressEntry is found.
     */
    public int getIndexOf(AddressEntry ae) {
        int index = 0;
        for (Map.Entry<String, TreeSet<AddressEntry>> entry : addressEntryList.entrySet()) {
            if(entry.getValue().contains(ae)) {
                for(AddressEntry elem : entry.getValue()) {
                    if(elem.equals(ae))
                        return index;
                    else
                        index++;
                }
            }
            index += entry.getValue().size();
        }
        return -1;
    }

    /**
     * Sets the event handlers for the following components,
     * {@link AddressBook#addressEntryJList}, {@link AddressBook#deleteB}, {@link AddressBook#findB},
     * {@link AddressBook#findTF}, {@link AddressBook#listB}, {@link AddressBook#updateB}, {@link AddressBook#addB}.
     */
    public void setEventHandlers() {
        setAddressEntryJListEventHandlers();
        setDeleteBEventHandlers();
        setFindBEventHandlers();
        setFindTFEventHandlers();
        setListBEventHandlers();
        setUpdateBEventHandlers();
        setAddBEventHandlers();
    }

    /**
     * Since {@link AddressBook} implements ListSelectionListener and has a definition
     * for {@link AddressBook#valueChanged(ListSelectionEvent)}, we can pass this as an argument
     * to the addListSelectionListener method of {@link AddressBook#addressEntryJList}. When an event
     * is triggered for the JList, the method {@link AddressBook#valueChanged(ListSelectionEvent)} will be called.
     */
    public void setAddressEntryJListEventHandlers() {
        addressEntryJList.addListSelectionListener(this);
    }

    /**
     * Resets UI and allows user to interact with buttons
     */
    private void viewMode() {
        addB.setText("Add");
        updateB.setText("Edit");
        deleteB.setText("Delete");
        listB.setEnabled(true);
        findB.setEnabled(true);
        addB.setEnabled(true);
        updateB.setEnabled(true);
        addressEntryJList.setEnabled(true);
    }

    /**
     * registers and defines an ActionListener for {@link AddressBook#deleteB}. When an event is triggered
     * for this button the textFields are made immutable with a call to {@link AddressBook#setTextFieldsImmutable()}.
     * Then the textFields are cleared with a call to {@link AddressBook#clearEntryTF()}. Next the {@link AddressEntry}
     * which is currently selected on {@link AddressBook#addressEntryJList} is obtained. If the AddressEntry is null then
     * nothing is selected and so nothing can be deleted so we return. If there is an AddressEntry selected then it is
     * removed from {@link AddressBook#listModel}, removed from {@link AddressBook#addressEntryList}, and removed from
     * database through {@link AddressBook#databaseManager}. If the selected AddressEntry prior to deletion was the last
     * element in the JList then the selected index will decrement by 1. Else it will remain the same.
     */
    public void setDeleteBEventHandlers() {
        deleteB.addActionListener(e -> {
            if (deleteB.getText().equals("Cancel")) {
                if (!addressEntryJList.isSelectionEmpty()) {
                    AddressEntry ae = listModel.getElementAt(addressEntryJList.getSelectedIndex());
                    populateTextFields(ae);
                } else {
                    clearEntryTF();
                }
                setTextFieldsImmutable();
                viewMode();
                return;
            }
            setTextFieldsImmutable();
            clearEntryTF();
            AddressEntry ae = addressEntryJList.getSelectedValue();
            if(ae == null)
                return;
            int index = addressEntryJList.getSelectedIndex();
            boolean wasLastElement = false;
            if(listModel.lastElement().compareTo(ae) == 0) {
                wasLastElement = true;
            }
            String lastName = ae.getName().getLastName();
            if(addressEntryList.computeIfPresent(lastName, (k, v) -> {
                v.remove(ae);
                if(v.isEmpty())
                    return null;
                else return v;
            }) == null) {
                addressEntryList.remove(lastName);
            }
            addressEntryJList.clearSelection();
            if(listModel.removeElement(ae)) {
                clearEntryTF();
                if(wasLastElement)
                    addressEntryJList.setSelectedIndex(index - 1);
                else
                    addressEntryJList.setSelectedIndex(index);
            }
            databaseManager.deleteAddressEntry(ae);
        });
    }

    /**
     * sets EventHandlers for {@link AddressBook#findTF}
     */
    void setFindTFEventHandlers() {
        findTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(addressEntryJList.getSelectedIndex() == -1) {
                    clearEntryTF();
                    setTextFieldsImmutable();
                }
            }
            public void focusLost(FocusEvent e) {}
        });

        findTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyChar() == KeyEvent.VK_ENTER) {
                    TreeSet<AddressEntry> prefix_Set = getPrefixSet(findTF.getText());
                    TreeMap<String, TreeSet<AddressEntry>> addressEntryList = new TreeMap<>();
                    addressEntryList.put(findTF.getText(), prefix_Set);
                    refreshList(addressEntryList);
                }
            }
        });
    }

    /**
     * sets EventHandlers for {@link AddressBook#findTF}.
     */
    public void setListBEventHandlers() {
        listB.addActionListener(e -> {
            setTextFieldsImmutable();
            refreshList(this.addressEntryList);
        });
    }

    /**
     * sets EventHandlers for {@link AddressBook#findB}.
     */
    public void setFindBEventHandlers() {
        findB.addActionListener(e -> {
            TreeSet<AddressEntry> prefix_Set = getPrefixSet(findTF.getText());
            TreeMap<String, TreeSet<AddressEntry>> addressEntryList = new TreeMap<>();
            addressEntryList.put(findTF.getText(), prefix_Set);
            refreshList(addressEntryList);
        });
    }

    /**
     * sets EventHandlers for {@link AddressBook#updateB}.
     */
    public void setUpdateBEventHandlers() {
        updateB.addActionListener(e -> {
            if(!addressEntryJList.isSelectionEmpty() && firstNameTF.isEditable()) {
                //resets text fields to saved entry if any fields are left blank when done editing
                if (checkForBlankTF()) {
                    AddressEntry ae = listModel.getElementAt(addressEntryJList.getSelectedIndex());
                    populateTextFields(ae);
                    return;
                }
                int index = addressEntryJList.getSelectedIndex();
                AddressEntry currentlySelectedEntry = addressEntryJList.getSelectedValue();
                AddressEntry entry = constructEntryFromFields();
                setElementAt(entry, getIndexOf(currentlySelectedEntry));
                listModel.setElementAt(entry, index);
                addressEntryJList.setSelectedIndex(listModel.getIndexOf(entry));
                setTextFieldsImmutable();
                viewMode();
            }
            else if(!addressEntryJList.isSelectionEmpty()) {
                setTextFieldsMutable();
                updateB.setText("Done");
                deleteB.setText("Cancel");
                listB.setEnabled(false);
                findB.setEnabled(false);
                addB.setEnabled(false);
                addressEntryJList.setEnabled(false);
            }
            else {
                setTextFieldsImmutable();
            }
        });
    }

    /**
     * sets EventHandlers for {@link AddressBook#addB}
     */
    public void setAddBEventHandlers() {
        addB.addActionListener(e -> {
            if(firstNameTF.isEditable()) {
                if (checkForBlankTF()) {
                    JOptionPane.showMessageDialog(panel1, "Incomplete Entry!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                AddressEntry ae = constructEntryFromFields();
                databaseManager.addAddressEntry(ae);
                System.out.println(ae.getID());
                add(ae);
                listModel.add(new AddressEntry(ae.getName(), ae.getAddress(), ae.getEmail(), ae.getPhone(), ae.getID()));
                setTextFieldsImmutable();
                viewMode();
                addressEntryJList.setSelectedIndex(listModel.getIndexOf(ae));
            }
            else {
                clearEntryTF();
                addressEntryJList.clearSelection();
                setTextFieldsMutable();
                addB.setText("Done");
                deleteB.setText("Cancel");
                listB.setEnabled(false);
                findB.setEnabled(false);
                updateB.setEnabled(false);
                addressEntryJList.setEnabled(false);
                firstNameTF.requestFocus();
            }
        });
    }
}


