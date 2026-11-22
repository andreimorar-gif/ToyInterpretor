package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;

public record WhileStmt(IExp cond, IStmt body) implements IStmt { // S13
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!(cond.eval(state.getSymTable(), state.getHeap()) instanceof BoolValue(boolean c)))
            throw new StatementException("while condition: "+cond+" is not boolean");
        if(c)
        {
            state.getExeStack().push(this);
            state.getExeStack().push(body);
        }
        return state;
    }
    @Override public String toString() {
        return "(while (" + cond + ") " + body + ")";
    }
}
