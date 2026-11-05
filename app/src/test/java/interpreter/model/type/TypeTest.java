package interpreter.model.type;

import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TypeTest {
    @Test void intDefaultValueIsZero() {
        assertEquals(new IntValue(0),new IntType().defaultValue());
    }
    @Test void boolDefaultValueIsFalse() {
        assertEquals(new BoolValue(false),new BoolType().defaultValue());
    }
    @Test void stringDefaultValueIsEmpty() {
        assertEquals(new StringValue(""),new StringType().defaultValue());
    }
    @Test void sameTypesAreEqual() {
        assertEquals(new IntType(),new IntType());
        assertEquals(new BoolType(), new BoolType());
        assertEquals(new StringType(), new StringType());
    }
    @Test void differentTypesAreNotEqual() {
        assertNotEquals(new IntType(), new BoolType());
        assertNotEquals(new BoolType(), new StringType());
        assertNotEquals(new StringType(), new IntType());
    }
    @Test void typesAsText() {
        assertEquals("int",new IntType().toString());
        assertEquals("bool",new BoolType().toString());
        assertEquals("string",new StringType().toString());
    }
}
