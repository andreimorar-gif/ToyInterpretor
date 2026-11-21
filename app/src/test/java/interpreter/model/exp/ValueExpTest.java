package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpTest {
    @Test void evaluateToItsValue() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IHeap heap = new MyHeap();
        assertEquals(new IntValue(2), new ValueExp(new IntValue(2)).eval(symTable,heap));
    }
}