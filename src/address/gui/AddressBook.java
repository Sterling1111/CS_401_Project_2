package address.gui;

import address.DatabaseManager;
import address.data.AddressEntry;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.sql.*;

public class AddressBook extends JFrame implements ListSelectionListener {

    private JPanel panel1;
    private JTextField findTF;
    private JButton listB;
    private JButton findB;
    private JList<AddressEntry> addressEntryJList;
    private JTextField firstNameTF;
    private JTextField lastNameTF;
    private JTextField streetTF;
    private JTextField cityTF;
    private JTextField stateTF;
    private JTextField zipTF;
    private JTextField emailTF;
    private JTextField phoneTF;
    private JLabel findL;
    private JLabel firstNameL;
    private JLabel lastNameL;
    private JLabel streetL;
    private JLabel cityL;
    private JLabel stateL;
    private JLabel zipL;
    private JLabel emailL;
    private JLabel phoneL;
    private JButton addB;
    private JButton deleteB;
    private JButton updateB;
    private ListModel listModel;

    private final TreeMap<String, TreeSet<AddressEntry>> addressEntryList = new TreeMap<>();
    private final DatabaseManager databaseManager = new DatabaseManager();

    public AddressBook() throws SQLException {
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

    public void initAddressBook() {
        ArrayList<AddressEntry> entries = databaseManager.getAllEntries();
        for(AddressEntry entry : entries) {
            this.add(entry);
            listModel.add(new AddressEntry(entry));
        }
        addressEntryJList.setSelectedIndex(0);
    }

    public void refreshList(TreeMap<String, TreeSet<AddressEntry>> addressEntryList) {
        addressEntryJList.clearSelection();
        listModel.clear();
        clearEntryTF();
        listModel = new ListModel();
        addressEntryJList.setModel(listModel);
        for(Map.Entry<String, TreeSet<AddressEntry>> entry : addressEntryList.entrySet()) {
            for(AddressEntry item : entry.getValue()) {
                if(item != null) {
                    //listModel.add(new AddressEntry(item.getName(), item.getAddress(), item.getEmail(), item.getPhone(), item.getID()));
                    listModel.add(new AddressEntry(item));
                }
            }
        }
        addressEntryJList.setSelectedIndex(0);
    }

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

    private void add(AddressEntry entry) {
        addressEntryList.computeIfAbsent(entry.getName().getLastName(), k -> new TreeSet<>()).add(entry);
    }

    private TreeSet<AddressEntry> getPrefixSet(String startOf_lastName) {
        SortedMap<String, TreeSet<AddressEntry>> tempMap;
        TreeSet<AddressEntry> tempSet = new TreeSet<>();
        tempMap = addressEntryList.subMap(startOf_lastName, startOf_lastName + Character.MAX_VALUE);

        for(Map.Entry<String, TreeSet<AddressEntry>> entry : tempMap.entrySet()) {
            tempSet.addAll(entry.getValue());
        }
        return tempSet;
    }

    public void valueChanged(ListSelectionEvent e) {
        setTextFieldsImmutable();
        AddressEntry ae = listModel.getElementAt(addressEntryJList.getSelectedIndex());
        if(ae != null) {
            firstNameTF.setText(ae.getName().getFirstName());
            lastNameTF.setText(ae.getName().getLastName());
            cityTF.setText(ae.getAddress().getCity());
            stateTF.setText(ae.getAddress().getState());
            streetTF.setText(ae.getAddress().getStreet());
            zipTF.setText(String.valueOf(ae.getAddress().getZip()));
            phoneTF.setText(ae.getPhone());
            emailTF.setText(ae.getEmail());
        }
    }

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

    public void setTextFieldsImmutable() {
       setTextFieldsMutability(false);
    }

    public void setTextFieldsMutable() {
        setTextFieldsMutability(true);
    }

    private Boolean checkForBlankTF() {
        if (firstNameTF.getText().isBlank()) {
            return false;
        } else if (lastNameTF.getText().isBlank()) {
            return false;
        } else if (streetTF.getText().isBlank()) {
            return false;
        } else if (cityTF.getText().isBlank()) {
            return false;
        } else if (stateTF.getText().isBlank()) {
            return false;
        } else if (zipTF.getText().isBlank()) {
            return false;
        } else if (emailTF.getText().isBlank()) {
            return false;
        } else return !phoneTF.getText().isBlank();
    }

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
        //databaseManager.deleteAddressEntry(entry);
    }

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

    public void setEventHandlers() {
        setAddressEntryJListEventHandlers();
        setDeleteBEventHandlers();
        setFindBEventHandlers();
        setFindTFEventHandlers();
        setListBEventHandlers();
        setUpdateBEventHandlers();
        setAddBEventHandlers();
    }

    public void setAddressEntryJListEventHandlers() {
        addressEntryJList.addListSelectionListener(this);
    }

    public void setDeleteBEventHandlers() {
        deleteB.addActionListener(e -> {
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

    public void setListBEventHandlers() {
        listB.addActionListener(e -> {
            setTextFieldsImmutable();
            refreshList(this.addressEntryList);
        });
    }

    public void setFindBEventHandlers() {
        findB.addActionListener(e -> {
            TreeSet<AddressEntry> prefix_Set = getPrefixSet(findTF.getText());
            TreeMap<String, TreeSet<AddressEntry>> addressEntryList = new TreeMap<>();
            addressEntryList.put(findTF.getText(), prefix_Set);
            refreshList(addressEntryList);
        });
    }

    public void setUpdateBEventHandlers() {
        updateB.addActionListener(e -> {
            if(!addressEntryJList.isSelectionEmpty() && firstNameTF.isEditable()) {
                if (!checkForBlankTF()) {
                    AddressEntry ae = listModel.getElementAt(addressEntryJList.getSelectedIndex());
                    firstNameTF.setText(ae.getName().getFirstName());
                    lastNameTF.setText(ae.getName().getLastName());
                    cityTF.setText(ae.getAddress().getCity());
                    stateTF.setText(ae.getAddress().getState());
                    streetTF.setText(ae.getAddress().getStreet());
                    zipTF.setText(String.valueOf(ae.getAddress().getZip()));
                    phoneTF.setText(ae.getPhone());
                    emailTF.setText(ae.getEmail());
                    return;
                }
                int index = addressEntryJList.getSelectedIndex();
                AddressEntry currentlySelectedEntry = addressEntryJList.getSelectedValue();
                AddressEntry entry = constructEntryFromFields();
                setElementAt(entry, getIndexOf(currentlySelectedEntry));
                listModel.setElementAt(entry, index);
                addressEntryJList.setSelectedIndex(listModel.getIndexOf(entry));
                setTextFieldsImmutable();
                updateB.setText("Edit");
                listB.setEnabled(true);
                findB.setEnabled(true);
                deleteB.setEnabled(true);
                updateB.setEnabled(true);
            }
            else if(!addressEntryJList.isSelectionEmpty()) {
                setTextFieldsMutable();
                updateB.setText("Done");
                listB.setEnabled(false);
                findB.setEnabled(false);
                deleteB.setEnabled(false);
                updateB.setEnabled(false);
            }
            else {
                setTextFieldsImmutable();
            }
        });
    }

    public void setAddBEventHandlers() {
        addB.addActionListener(e -> {
            if(firstNameTF.isEditable()) {
                if (!checkForBlankTF()) {
                    JOptionPane.showMessageDialog(panel1, "Incomplete Entry!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                AddressEntry ae = constructEntryFromFields();
                databaseManager.addAddressEntry(ae);
                System.out.println(ae.getID());
                add(ae);
                listModel.add(new AddressEntry(ae.getName(), ae.getAddress(), ae.getEmail(), ae.getPhone(), ae.getID()));
                setTextFieldsImmutable();
                addB.setText("Add");
                listB.setEnabled(true);
                findB.setEnabled(true);
                deleteB.setEnabled(true);
                updateB.setEnabled(true);
                addressEntryJList.setSelectedIndex(listModel.getIndexOf(ae));
            }
            else {
                clearEntryTF();
                addressEntryJList.clearSelection();
                setTextFieldsMutable();
                addB.setText("Done");
                listB.setEnabled(false);
                findB.setEnabled(false);
                deleteB.setEnabled(false);
                updateB.setEnabled(false);
                firstNameTF.requestFocus();
            }
        });
    }
}


