package address.gui;

import address.data.AddressEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ListModelTest {

    private ListModel listModel;

    private final AddressEntry ae1 = new AddressEntry("John", "A", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 1);
    private final AddressEntry ae2 = new AddressEntry("John", "B", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 2);
    private final AddressEntry ae3 = new AddressEntry("John", "C", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 3);
    private final AddressEntry ae4 = new AddressEntry("John", "D", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 4);
    private final AddressEntry ae5 = new AddressEntry("John", "E", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 5);

    @BeforeEach
    public void initializeListModel() {
        this.listModel = new ListModel();
        listModel.add(ae1);
        listModel.add(ae2);
        listModel.add(ae3);
    }

    @Test
    void testGetSize() {
        assertEquals(3, listModel.getSize());
    }

    @Test
    void testGetElementAt() {
        assertNull(listModel.getElementAt(-1));
        assertNull(listModel.getElementAt(listModel.getSize()));
        assertEquals(ae1, listModel.getElementAt(0));
        assertEquals(ae3, listModel.getElementAt(listModel.getSize() - 1));
    }

    @Test
    void testGetIndexOf() {
        assertEquals(-1, listModel.getIndexOf(ae4));
        assertEquals(0, listModel.getIndexOf(ae1));
        assertEquals(2, listModel.getIndexOf(ae3));
    }

    @Test
    void testAdd() {
        listModel.add(ae4);
        assertEquals(4, listModel.getSize());
        assertEquals(3, listModel.getIndexOf(ae4));
    }

    @Test
    void testSetElementAt() {
        listModel.setElementAt(ae4, listModel.getSize() - 1);
        assertEquals(ae4.getName().getLastName(), listModel.getElementAt(listModel.getSize() - 1).getName().getLastName());
    }

    @Test
    void testClear() {
        listModel.clear();
        assertEquals(0, listModel.getSize());
    }

    @Test
    void testContains() {
        assertTrue(listModel.contains(ae1));
        assertFalse(listModel.contains(ae4));
    }

    @Test
    void testFirstElement() {
        assertEquals(ae1, listModel.firstElement());
        listModel.clear();
        assertNull(listModel.firstElement());
    }

    @Test
    void testLastElement() {
        assertEquals(ae3, listModel.lastElement());
        listModel.clear();
        assertNull(listModel.lastElement());
    }

    @Test
    void testRemoveElement() {
        listModel.removeElement(ae1);
        assertEquals(2, listModel.getSize());
        assertFalse(listModel.contains(ae1));
    }

}