package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String,Integer> dict;
    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }
    @Test void keyNotDefinedBeforePut() {
        assertFalse(dict.isDefined("w"));
    }
    @Test void keyDefinedAfterPut() {
        dict.put("w",1);
        assertTrue(dict.isDefined("w"));
    }
    @Test void lookupReturnsValueAfterPut() throws ADTException {
        dict.put("w",1);
        assertEquals(1,dict.lookup("w"));
    }
    @Test void updateChangesValue() throws ADTException {
        dict.put("w",1);
        dict.update("w",4);
        assertEquals(4,dict.lookup("w"));
    }
    @Test void lookupOfMissingKeyThrows() {
        assertThrows(ADTException.class, ()->dict.lookup("w"));
    }
    @Test void updateOfMissingKeyThrows() {
        assertThrows(ADTException.class, ()->dict.update("w",2));
        assertFalse(dict.isDefined("w"));
    }
    @Test void removeDeletesKey() throws ADTException {
        dict.put("w",1);
        dict.remove("w");
        assertFalse(dict.isDefined("w"));
    }
    @Test void removeOfMissingKeyThrows() {
        assertThrows(ADTException.class, ()->dict.remove("w"));
    }
    @Test void toMapContainsAllEntries() throws ADTException {
        dict.put("w",1);
        dict.put("x",2);
        assertEquals(Map.of("w",1,"x",2),dict.toMap());
    }
    @Test void toMapIsReadOnly() {
        dict.put("w",1);
        assertThrows(UnsupportedOperationException.class, ()->dict.toMap().put("x",2));
    }
    @Test void toMapIsACopy() {
        dict.put("w",1);
        Map<String,Integer> copy = dict.toMap();
        dict.put("x",2);
        assertEquals(1,copy.size());
    }
}