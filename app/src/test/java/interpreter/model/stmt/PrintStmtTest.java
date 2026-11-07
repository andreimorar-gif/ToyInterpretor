package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {
    private static PrgState freshState(IStmt stmt) {
        return PrgState.of(stmt);
    }

    private static void step(PrgState state) throws MyException {
        state.getExeStack().pop().execute(state);
    }
    @Test void appendTheValueToOut() throws MyException {
        PrgState state = freshState(new PrintStmt(new ValueExp(new IntValue(2))));
        step(state);
        assertEquals(1,state.getOut().size());
        assertEquals(new IntValue(2),state.getOut().get(0));
    }
    @Test void printingUndefinedVariableThrows() {
        PrgState state = freshState(new PrintStmt(new VarExp("v")));
        assertThrows(ExpressionException.class,()->step(state));
    }
}