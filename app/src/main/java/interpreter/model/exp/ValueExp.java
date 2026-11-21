package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;

public record ValueExp(IValue val) implements IExp {
    @Override public IValue eval(IDictionary<String,IValue> symTable, IHeap heap) {
        return val;
    }
    @Override public String toString() {
        return val.toString();
    }
}
