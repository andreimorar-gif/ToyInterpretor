package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<>();

    @Override public void add(T v) {
        list.add(v);
    }
    @Override public T get(int i) throws ADTException{
        if(i<0 || i>=list.size())
            throw new ADTException("index "+i+" is out of bounds");
        return list.get(i);
    }
    @Override public int size(){
        return list.size();
    }
    @Override public List<T> toList() {
        return List.copyOf(list);
    }
    @Override public String toString(){
        return list.toString();
    }
}
