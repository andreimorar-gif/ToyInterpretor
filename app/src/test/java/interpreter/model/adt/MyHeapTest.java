package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {
    private IHeap heap;
    @BeforeEach
    void setUp()
    {
        heap = new MyHeap();
    }
    @Test
    void allocateReturnsConsecutiveAddresses()
    {
        assertEquals(1,heap.allocate(new IntValue(10)));
        assertEquals(2,heap.allocate(new IntValue(20)));
        assertEquals(3,heap.allocate(new IntValue(30)));
    }
    @Test
    void getReturnsStoredValue()
    {
        int a = heap.allocate(new IntValue(10));
        assertEquals(new IntValue(10),heap.get(a));
    }
    @Test
    void updateChangesTheStoredValue()
    {
        int a = heap.allocate(new IntValue(10));
        heap.update(a,new IntValue(20));
        assertEquals(new IntValue(20),heap.get(a));
    }
    @Test
    void getOfMissingAddressThrows() {
        assertThrows(ADTException.class,()->heap.get(1));
    }
    @Test
    void updateOfMissingAddressThrows() {
        assertThrows(ADTException.class,()->heap.update(1,new IntValue(10)));
    }
}