package address.gui;

import address.data.AddressEntry;

import javax.swing.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.lang.Math;

/**
 * Provides an AbstractListModel implementation which will automatically store AddressEntry object
 * in sorted order by last name.
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 3/14/21
 * */
public class ListModel extends AbstractListModel<AddressEntry> {

    /**
     * a data structure to contain the AddressEntry objects.
     */
    private final TreeMap<String, TreeSet<AddressEntry>> addressEntryList;

    /**
     * Constructs a new list model and initializes the data member {@link ListModel#addressEntryList}
     */
    public ListModel() {
        addressEntryList = new TreeMap<>();
    }

    /**
     * returns the total number of AddressEntry objects stored in the list.
     * @return the total number of AddressEntry objects stored in the list.
     */
    public int getSize() {
        return addressEntryList.values().stream().mapToInt(TreeSet::size).sum();
    }

    /**
     * returns the AddressEntry found at the index provided.
     * @param index the index of the {@link ListModel#addressEntryList} where the AddressEntry found is to be returned.
     * @return the AddressEntry at the specified index or null if the index is out of range.
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
     * returns the index at which the AddressEntry specified by the parameter is found.
     * @param ae the AddressEntry which we are searching for
     * @return the index at which the AddressEntry specified by the parameter is found or -1 if no such
     * AddressEntry is found.
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
     * Adds the AddressEntry specified in the parameter to the list.
     * @param entry the AddressEntry instance to be added
     */
    public void add(AddressEntry entry) {
        if(addressEntryList.computeIfAbsent(entry.getName().getLastName(), k -> new TreeSet<>()).add(entry));
            fireContentsChanged(this, 0, getSize());
    }

    /**
     * Updates the element found at the index specified to match the AddressEntry instance passed.
     * @param entry The AddressEntry which the AddressEntry at index will be set to.
     * @param index The index at which we wish to update the AddressEntry.
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
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Removes all elements from {@link ListModel#addressEntryList} with a call to
     * {@link TreeMap#clear()}
     */
    public void clear() {
        addressEntryList.clear();
    }

    /**
     * Determines if the AddressEntry passed is contained within {@link ListModel#addressEntryList}
     * @param entry the AddressEntry to be found.
     * @return true if the entry is found and false if the entry is not found.
     */
    public boolean contains(AddressEntry entry) {
        if(addressEntryList.containsKey(entry.getName().getLastName())) {
            TreeSet<AddressEntry> tempSet = addressEntryList.get(entry.getName().getLastName());
            return tempSet.contains(entry);
        }
        return false;
    }

    /**
     * returns the first AddressEntry in the list.
     * @return the first AddressEntry or null if the list is empty.
     */
    public AddressEntry firstElement() {
        if(addressEntryList.size() != 0)
            return addressEntryList.firstEntry().getValue().first();
        return null;
    }

    /**
     * returns the last AddressEntry in the list.
     * @return the last AddressEntry or null if the list is empty.
     */
    public AddressEntry lastElement() {
        if(addressEntryList.size() != 0)
            return addressEntryList.lastEntry().getValue().last();
        return null;
    }

    /**
     * returns the passed AddressEntry object if it can be found.
     * @param entry the AddressEntry to be removed.
     * @return true if entry is found and false if it is not ain't that something.
     */
    public boolean removeElement(AddressEntry entry) {
        String lastName = entry.getName().getLastName();
        if(addressEntryList.computeIfPresent(lastName, (k, v) -> {
            v.remove(entry);
            if(v.isEmpty())
                return null;
            else return v;
        }) != null) {
            fireContentsChanged(this, 0, getSize());
            return true;
        }
        addressEntryList.remove(lastName);
        fireContentsChanged(this, 0, getSize());
        return true;
    }
}
