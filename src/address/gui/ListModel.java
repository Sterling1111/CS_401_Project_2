package address.gui;

import address.data.AddressEntry;

import javax.swing.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.lang.Math;

public class ListModel extends AbstractListModel<AddressEntry> {

    private final TreeMap<String, TreeSet<AddressEntry>> addressEntryList;

    public ListModel() {
        addressEntryList = new TreeMap<>();
    }

    public int getSize() {
        return addressEntryList.values().stream().mapToInt(TreeSet::size).sum();
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

    public void add(AddressEntry entry) {
        if(addressEntryList.computeIfAbsent(entry.getName().getLastName(), k -> new TreeSet<>()).add(entry))
            fireContentsChanged(this, 0, getSize());
    }

    public void setElementAt(AddressEntry entry, int index) {
        AddressEntry ae = getElementAt(index);
        if(entry.getName().getLastName().compareTo(ae.getName().getLastName()) != 0) {
            System.out.println("They are not equal");
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

    public void addAll(AddressEntry[] elements) {
        boolean addedAtLeastOne = false;
        for(AddressEntry entry : elements) {
            addedAtLeastOne = (addedAtLeastOne ||
            addressEntryList.computeIfAbsent(entry.getName().getLastName(), k -> new TreeSet<>()).add(entry));
        }
        if(addedAtLeastOne)
            fireContentsChanged(this, 0, getSize());
    }

    public void clear() {
        addressEntryList.clear();
    }

    public boolean contains(AddressEntry entry) {
        if(addressEntryList.containsKey(entry.getName().getLastName())) {
            TreeSet<AddressEntry> tempSet = addressEntryList.get(entry.getName().getLastName());
            return tempSet.contains(entry);
        }
        return false;
    }

    public AddressEntry firstElement() {
        if(addressEntryList.size() != 0)
            return addressEntryList.firstEntry().getValue().first();
        return null;
    }

    public AddressEntry lastElement() {
        if(addressEntryList.size() != 0)
            return addressEntryList.lastEntry().getValue().last();
        return null;
    }

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
        System.out.println("The set is empty so lets remove it");
        addressEntryList.remove(lastName);
        fireContentsChanged(this, 0, getSize());
        return true;
    }
}
