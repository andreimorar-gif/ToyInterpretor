package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {
    private static PrgState freshState(IStmt stmt) {
        return PrgState.of(stmt);
    }

    private static void step(PrgState state) throws MyException {
        state.getExeStack().pop().execute(state);
    }
    private static final IStmt THEN = new VarDeclStmt("t",new IntType());
    private static final IStmt ELSE = new VarDeclStmt("e",new IntType());

    @Test void trueConditionPushesThenBranch() throws MyException {
        PrgState state = freshState(new IfStmt(new ValueExp(new BoolValue(true)),THEN,ELSE));
        step(state);
        assertEquals(THEN,state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }
    @Test void falseConditionPushesElseBranch() throws MyException {
        PrgState state = freshState(new IfStmt(new ValueExp(new BoolValue(false)),THEN,ELSE));
        step(state);
        assertEquals(ELSE,state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }
    @Test void nonBooleanConditionThrows() {
        PrgState state = freshState(new IfStmt(new ValueExp(new IntValue(1)),THEN,ELSE));
        var ex = assertThrows(StatementException.class,()->step(state));
        assertEquals("condition is not boolean",ex.getMessage());
    }
}