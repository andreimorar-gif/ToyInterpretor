package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
    private static PrgState freshState(IStmt stmt) {
        return PrgState.of(stmt);
    }

    private static void step(PrgState state) throws MyException {
        state.getExeStack().pop().execute(state);
    }
    @Test void assignmentUpdatesTheVariable() throws MyException {
        PrgState state = freshState(new AssignStmt("v",new ValueExp(new IntValue(1))));
        state.getSymTable().put("v",new IntValue(2));
        step(state);
        assertEquals(new IntValue(1),state.getSymTable().lookup("v"));
    }
    @Test void assignmentToUndeclaredVariableThrows() {
        PrgState state = freshState(new AssignStmt("v",new ValueExp(new IntValue(1))));
        var ex = assertThrows(StatementException.class,()->step(state));
        assertEquals("variable v was not declared",ex.getMessage());
    }
    @Test void wrongTypesTrows() {
            PrgState state = freshState(new AssignStmt("v",new ValueExp(new BoolValue(true))));
            state.getSymTable().put("v",new IntValue(1));
            assertThrows(StatementException.class,()->step(state));
    }

}