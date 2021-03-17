package address.gui;

import address.data.AddressEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests the {@link address.gui.ListModel} class
 * @author Sterling Jeppson
 * @author Arian Aryubi
 * @author Lissette Sotto
 * @author Karthikeyan Vijayaraj
 * @since 3/14/21
 * */
class ListModelTest {

    /**
     * instance of {@link ListModel} to test.
     */
    private ListModel listModel;

    /**
     * instance of {@link AddressEntry} to test.
     */
    private final AddressEntry ae1 = new AddressEntry("John", "A", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 1);
    /**
     * instance of {@link AddressEntry} to test.
     */
    private final AddressEntry ae2 = new AddressEntry("John", "B", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 2);
    /**
     * instance of {@link AddressEntry} to test.
     */
    private final AddressEntry ae3 = new AddressEntry("John", "C", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 3);
    /**
     * instance of {@link AddressEntry} to test.
     */
    private final AddressEntry ae4 = new AddressEntry("John", "D", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 4);
    /**
     * instance of {@link AddressEntry} to test.
     */
    private final AddressEntry ae5 = new AddressEntry("John", "E", "Arroyo", "Dublin", "NY", 81777, "boring@gmail.com", "925-123-7924", 5);

    /**
     * adds some address entires to be tested.
     */
    @BeforeEach
    public void initializeListModel() {
        this.listModel = new ListModel();
        listModel.add(ae1);
        listModel.add(ae2);
        listModel.add(ae3);
    }

    /**
     * tests whether the size method works
     */
    @Test
    void testGetSize() {
        assertEquals(3, listModel.getSize());
    }

    /**
     * tests whether the getElementAt method works
     */
    @Test
    void testGetElementAt() {
        assertNull(listModel.getElementAt(-1));
        assertNull(listModel.getElementAt(listModel.getSize()));
        assertEquals(ae1, listModel.getElementAt(0));
        assertEquals(ae3, listModel.getElementAt(listModel.getSize() - 1));
    }

    /**
     * tests whether the getIndexOf method works
     */
    @Test
    void testGetIndexOf() {
        assertEquals(-1, listModel.getIndexOf(ae4));
        assertEquals(0, listModel.getIndexOf(ae1));
        assertEquals(2, listModel.getIndexOf(ae3));
    }

    /**
     * tests whether the add method works.
     */
    @Test
    void testAdd() {
        listModel.add(ae4);
        assertEquals(4, listModel.getSize());
        assertEquals(3, listModel.getIndexOf(ae4));
    }

    /**
     * tests whether the setElementAt method works
     */
    @Test
    void testSetElementAt() {
        listModel.setElementAt(ae4, listModel.getSize() - 1);
        assertEquals(ae4.getName().getLastName(), listModel.getElementAt(listModel.getSize() - 1).getName().getLastName());
    }

    /**
     * tests whether the testClear method works.
     */
    @Test
    void testClear() {
        listModel.clear();
        assertEquals(0, listModel.getSize());
    }

    /**
     * tests whether the contains method works.
     */
    @Test
    void testContains() {
        assertTrue(listModel.contains(ae1));
        assertFalse(listModel.contains(ae4));
    }

    /**
     * tests whether the firstElement method works.
     */
    @Test
    void testFirstElement() {
        assertEquals(ae1, listModel.firstElement());
        listModel.clear();
        assertNull(listModel.firstElement());
    }

    /**
     * tests whether the lastElement method works
     */
    @Test
    void testLastElement() {
        assertEquals(ae3, listModel.lastElement());
        listModel.clear();
        assertNull(listModel.lastElement());
    }

    /**
     * tests whether the removeElement works.
     */
    @Test
    void testRemoveElement() {
        listModel.removeElement(ae1);
        assertEquals(2, listModel.getSize());
        assertFalse(listModel.contains(ae1));
    }

}