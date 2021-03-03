package address.gui;

import address.data.AddressEntry;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class AddressBook extends JFrame implements ListSelectionListener{

    private JPanel panel1;
    private JTextField findTF;
    private JButton deleteB;
    private JButton addB;
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
    private JLabel firstNameL;
    private JLabel lastNameL;
    private JLabel streetL;
    private JLabel cityL;
    private JLabel stateL;
    private JLabel zipL;
    private JLabel emailL;
    private JTextField phoneTF;
    private JLabel phoneL;
    private JLabel findL;
    private ListModel listModel;

    private final TreeMap<String, TreeSet<AddressEntry>> addressEntryList = new TreeMap<>();

    public AddressBook() {
        setTitle("Address Book");
        setContentPane(panel1);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        listModel = new ListModel();
        addressEntryJList.setModel(listModel);
        initAddressBook();
        setEventHandlers();
    }

    public void initAddressBook() {
        AddressEntry entry1 = new AddressEntry("Sterling", "Jeppson",
                "2759 Vine Dr.","Livermore",
                "CA", 94550, "sterlingijeppson@gmail.com", "925-289-6963", 1);
        AddressEntry entry2 = new AddressEntry("D.S", "Malik",
                "2759 Vine Dr.","Livermore",
                "CA", 94550, "sterlingijeppson@gmail.com","925-289-6963", 2);
        add(entry1);
        add(entry2);
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
                    listModel.add(item);
                }
            }
        }
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

    public void add(AddressEntry entry) {
        addressEntryList.computeIfAbsent(entry.getName().getLastName(), k -> new TreeSet<>()).add(entry);
        listModel.add(entry);
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
        int index = addressEntryJList.getSelectedIndex();
        System.out.println(index);
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

    void setEventHandlers() {
        addB.addActionListener(e -> {
            AddressEntry ae = constructEntryFromFields();
            add(ae);
        });

        findB.addActionListener(e -> {
            TreeSet<AddressEntry> prefix_Set = getPrefixSet(findTF.getText());
            TreeMap<String, TreeSet<AddressEntry>> addressEntryList = new TreeMap<>();
            addressEntryList.put(findTF.getText(), prefix_Set);
            refreshList(addressEntryList);
        });

        listB.addActionListener(e -> refreshList(this.addressEntryList));

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

        deleteB.addActionListener(e -> {
            AddressEntry ae = constructEntryFromFields();
            addressEntryList.computeIfPresent(lastNameTF.getText(), (k, v) -> {v.remove(ae); return v;});
            addressEntryJList.clearSelection();
            if(listModel.removeElement(ae)) {
                clearEntryTF();
            }
        });

        addressEntryJList.addListSelectionListener(this::valueChanged);
    }
}
