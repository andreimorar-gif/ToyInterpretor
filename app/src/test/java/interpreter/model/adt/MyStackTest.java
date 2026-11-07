package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    private IStack<Integer> stack;
    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }
    @Test void newStackIsEmpty() {
        assertTrue(stack.isEmpty());
    }
    @Test void stackNotEmptyAfterPush() {
        stack.push(67);
        assertFalse(stack.isEmpty());
    }
    @Test void popReturnsElemsInLifoOrder() throws ADTException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3,stack.pop());
        assertEquals(2,stack.pop());
        assertEquals(1,stack.pop());
        assertTrue(stack.isEmpty());
    }
    @Test void popOnEmptyStackThrows() {
        var ex = assertThrows(ADTException.class, () -> stack.pop());
        assertEquals("pop from an empty stack",ex.getMessage());
    }
    @Test void toListHasTopOfStackFirst() throws ADTException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(List.of(3,2,1), stack.toList());
    }
}