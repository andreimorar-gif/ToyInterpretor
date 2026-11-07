package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {
    private static PrgState freshState(IStmt stmt) {
        return PrgState.of(stmt);
    }

    private static void step(PrgState state) throws MyException {
        state.getExeStack().pop().execute(state);
    }
    @Test void intDeclarationStartsAtZero() throws MyException {
        PrgState state = freshState(new VarDeclStmt("v",new IntType()));
        step(state);
        assertEquals(new IntValue(0),state.getSymTable().lookup("v"));
    }
    @Test void boolDeclarationStartsAtFalse() throws MyException {
        PrgState state = freshState(new VarDeclStmt("v",new BoolType()));
        step(state);
        assertEquals(new BoolValue(false),state.getSymTable().lookup("v"));
    }
    @Test void stringDeclarationStartsAtFalse() throws MyException {
        PrgState state = freshState(new VarDeclStmt("v",new StringType()));
        step(state);
        assertEquals(new StringValue(""),state.getSymTable().lookup("v"));
    }
    @Test void alreadyDeclaredVariablesThrow() {
        PrgState state = freshState(new VarDeclStmt("v",new IntType()));
        state.getSymTable().put("v",new IntValue(0));
        var ex = assertThrows(StatementException.class,()->step(state));
        assertEquals("variable v is already declared",ex.getMessage());
    }
}