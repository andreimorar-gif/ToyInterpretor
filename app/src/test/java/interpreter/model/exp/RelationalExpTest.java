package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private IDictionary<String, IValue> symTable;
    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }
    public IValue eval(RelOp op, int a, int b) throws MyException {
        return new RelationalExp(op,num(a),num(b)).eval(symTable);
    }
    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }
    @Test
    void lessThan() throws MyException {
        assertEquals(new BoolValue(true),eval(RelOp.LT,1,2));
        assertEquals(new BoolValue(false),eval(RelOp.LT,2,1));
    }
    @Test
    void lessOrEqual() throws MyException {
        assertEquals(new BoolValue(true),eval(RelOp.LE,1,2));
        assertEquals(new BoolValue(true),eval(RelOp.LE,2,2));
        assertEquals(new BoolValue(false),eval(RelOp.LE,2,1));
    }
    @Test
    void equal() throws MyException {
        assertEquals(new BoolValue(true),eval(RelOp.EQ,2,2));
        assertEquals(new BoolValue(false),eval(RelOp.EQ,1,2));
    }
    @Test
    void notEqual() throws MyException {
        assertEquals(new BoolValue(true),eval(RelOp.NE,1,2));
        assertEquals(new BoolValue(false),eval(RelOp.NE,2,2));
    }
    @Test
    void greaterThan() throws MyException {
        assertEquals(new BoolValue(true),eval(RelOp.GT,2,1));
        assertEquals(new BoolValue(false),eval(RelOp.GT,1,2));
    }
    @Test
    void greaterOrEqual() throws MyException {
        assertEquals(new BoolValue(true),eval(RelOp.GE,2,1));
        assertEquals(new BoolValue(true),eval(RelOp.GE,2,2));
        assertEquals(new BoolValue(false),eval(RelOp.GE,1,2));
    }
    @Test
    void nonIntegerFirstOperandThrows() {
        IExp e = new RelationalExp(RelOp.LE,new ValueExp(new BoolValue(true)),num(1));
        var ex = assertThrows(ExpressionException.class, ()->e.eval(symTable));
        assertEquals("first operand is not an integer",ex.getMessage());
    }
    @Test
    void nonIntegerSecondOperandThrows() {
        IExp e = new RelationalExp(RelOp.LE,num(1),new ValueExp(new BoolValue(true)));
        var ex = assertThrows(ExpressionException.class, ()->e.eval(symTable));
        assertEquals("second operand is not an integer",ex.getMessage());
    }
    @Test
    void useVariableValues() throws MyException {
        symTable.put("a",new IntValue(1));
        symTable.put("b",new IntValue(2));
        IExp e = new RelationalExp(RelOp.LT,new VarExp("a"),new VarExp("b"));
    }
}