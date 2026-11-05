package interpreter.model.value;

import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValueTest {
    @Test void intValueHasIntType() {
        assertEquals(new IntType(),new IntValue(2).getType());
    }
    @Test void boolValueHasBoolType() {
        assertEquals(new BoolType(), new BoolValue(true).getType());
    }
    @Test void stringValueHasStringType() {assertEquals(new StringType(), new StringValue("a").getType());}
    @Test void valuesWithSameContentAreEqual() {
        assertEquals(new IntValue(2),new IntValue(2));
        assertEquals(new BoolValue(true), new BoolValue(true));
    }
    @Test void valuesWithDifferentContentAreNotEqual() {
        assertNotEquals(new IntValue(2), new IntValue(3));
        assertNotEquals(new BoolValue(false), new BoolValue(true));
        assertNotEquals(new StringValue("a.in"),new StringValue("b.in"));
    }
    @Test void valuesPrintAsText() {
        assertEquals("2", new IntValue(2).toString());
        assertEquals("true", new BoolValue(true).toString());
    }
    @Test void equalStringValuesAreEqualAndHaveTheSameHashCode() {
        StringValue v1 = new StringValue("test.in");
        StringValue v2 = new StringValue("test.in");
        assertEquals(v1,v2);
        assertEquals(v1.hashCode(),v2.hashCode());
    }
    @Test void stringValuesPrintWithQuotes() {
        assertEquals("\"test.in\"",new StringValue("test.in").toString());
    }
}
