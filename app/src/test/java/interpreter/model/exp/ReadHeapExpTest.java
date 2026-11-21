package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadHeapExpTest {
    private IDictionary<String, IValue> symTable;
    private IHeap heap;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
        heap = new MyHeap();
    }
    @Test
    void readsTheValueAtReferencedAddress() throws MyException{
        int a = heap.allocate(new IntValue(10));
        symTable.put("v", new RefValue(a,new IntType()));
        assertEquals(new IntValue(10),new ReadHeapExp(new VarExp("v")).eval(symTable,heap));
    }
    @Test
    void nestedReadFollowsTwoReferences() throws MyException {
        int a1 = heap.allocate(new IntValue(10));
        int a2 = heap.allocate(new RefValue(a1,new IntType()));
        symTable.put("v", new RefValue(a2,new RefType(new IntType())));
        IExp e = new ReadHeapExp(new ReadHeapExp(new VarExp("v")));
        assertEquals(new IntValue(10),e.eval(symTable,heap));
    }
    @Test
    void nonReferenceExpThrows() {
        IExp e = new ReadHeapExp(new ValueExp(new IntValue(1)));
        var ex = assertThrows(ExpressionException.class,()->e.eval(symTable,heap));
        assertEquals("rH: 1 is not a reference",ex.getMessage());
    }
    @Test
    void addressNotInHeapThrows() {
        symTable.put("v",new RefValue(1,new IntType()));
        var ex = assertThrows(ExpressionException.class,
                ()->new ReadHeapExp(new VarExp("v")).eval(symTable,heap));
        assertEquals("rH address: 1 is not in the heap",ex.getMessage());
    }
}