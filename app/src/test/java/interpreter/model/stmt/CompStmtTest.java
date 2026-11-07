package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompStmtTest {
    private static PrgState freshState(IStmt stmt) {
        return PrgState.of(stmt);
    }

    private static void step(PrgState state) throws MyException {
        state.getExeStack().pop().execute(state);
    }

    @Test void pushesFirstOnTopOfSecond() throws MyException {
        IStmt first = new VarDeclStmt("a",new IntType());
        IStmt second = new VarDeclStmt("b", new IntType());
        PrgState state = freshState(new CompStmt(first,second));
        step(state);
        assertEquals(first,state.getExeStack().pop());
        assertEquals(second,state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

}