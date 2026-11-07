package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<Integer> list;
    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }
    @Test void newListHasSizeZero() {
        assertEquals(0,list.size());
    }
    @Test void addIncreasesSize() {
        list.add(10);
        assertEquals(1,list.size());
    }
    @Test void getReturnsElemsInCorrectOrder() throws ADTException {
        list.add(10);
        list.add(11);
        assertEquals(10,list.get(0));
        assertEquals(11,list.get(1));
    }
    @Test void getNegativeIndexThrows() {
        assertThrows(ADTException.class, ()->list.get(-1));
    }
    @Test void getPastEndIndexThrows() {
        list.add(10);
        assertThrows(ADTException.class, ()->list.get(1));
    }
    @Test void toListKeepsInsertionOrder() throws ADTException {
        list.add(10);
        list.add(11);
        assertEquals(List.of(10,11),list.toList());
    }

}