package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeapStmtTest {
    private PrgState state;

    @BeforeEach
    public void setup() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v",new RefType(new IntType())).execute(state); //Ref int v
    }
    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }
    @Test
    void newOnUndeclaredVariableThrows() {
        assertThrows(StatementException.class,
                ()-> new NewStmt("x", new ValueExp(new IntValue(20))).execute(state));
    }
    @Test
    void newOnANonReferenceThrows() {
        new VarDeclStmt("x",new IntType()).execute(state);
        assertThrows(StatementException.class,
                ()-> new NewStmt("x", new ValueExp(new IntValue(20))).execute(state));
    }
    @Test
    void newWithValueOfWrongTypeThrows() {
        var exp = new ValueExp(new BoolValue(true));
        assertThrows(StatementException.class,
                ()->new NewStmt("v", exp).execute(state));
    }

}
