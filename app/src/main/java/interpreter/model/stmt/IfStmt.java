package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt { //S5
    @Override public PrgState execute(PrgState state) throws MyException {
        if(!(cond.eval(state.getSymTable(),state.getHeap()) instanceof BoolValue(boolean c)))
            throw new StatementException("condition is not boolean");
        if(c)
            state.getExeStack().push(thenS);
        else
            state.getExeStack().push(elseS);
        return state;
    }
    @Override public String toString() { return "if "+cond+" then "+thenS+" else "+elseS; }
}
