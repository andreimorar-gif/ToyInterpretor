package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {
    private static PrgState freshState(IStmt stmt) {
        return PrgState.of(stmt);
    }

    private static void step(PrgState state) throws MyException {
        state.getExeStack().pop().execute(state);
    }
    @Test void changeNothing() throws MyException {
        PrgState state = freshState(new NopStmt());
        step(state);
        assertTrue(state.getExeStack().isEmpty());
        assertFalse(state.getSymTable().isDefined("v"));
        assertEquals(0,state.getOut().size());
    }
}