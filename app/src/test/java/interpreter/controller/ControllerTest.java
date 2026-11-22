package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @TempDir
    Path dir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep(); // display off
        return state;
    }
    @Test void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test void example2PrintsEighteen() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test void example3PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX3);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test void oneStepOnEmptyStackThrows() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        Controller controller = new Controller(new Repository(state,dir.resolve("log.txt").toString()), false);
        controller.oneStep(state);
        var ex = assertThrows(StatementException.class, () -> controller.oneStep(state));
        assertEquals("program state stack is empty", ex.getMessage());
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
                c = log.indexOf("Out:"), d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }

    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(50), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    void fileExampleWithAnEmptyFilePrintsZeroTwice() throws Exception {
        Path in = dir.resolve("empty.in");
        Files.writeString(in,"");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(0), s.getOut().get(0));
        assertEquals(new IntValue(0), s.getOut().get(1));
    }

    @Test
    void example5PrintsFive() throws MyException {
        PrgState state = run(Examples.EX5);
        assertEquals(1,state.getOut().size());
        assertEquals(new IntValue(5), state.getOut().get(0));
    }

    @Test
    void example6() throws MyException {
        PrgState state = run(Examples.EX6);
        assertEquals(Map.of(1,new IntValue(20),
                2,new RefValue(1,new IntType())),
                state.getHeap().getContent());
        assertEquals(Map.of("v",new RefValue(1,new IntType()),
                "a",new RefValue(2,new RefType(new IntType()))),
                state.getSymTable().toMap());
        assertEquals(List.of(new RefValue(1,new IntType()),
                new RefValue(2,new RefType(new IntType()))),
                state.getOut().toList());
    }

    @Test
    void example7() throws MyException {
        PrgState state = run(Examples.EX7);
        assertEquals(Map.of(1,new IntValue(20),
                        2,new RefValue(1,new IntType())),
                state.getHeap().getContent());
        assertEquals(Map.of("v",new RefValue(1,new IntType()),
                        "a",new RefValue(2,new RefType(new IntType()))),
                state.getSymTable().toMap());
        assertEquals(List.of(new IntValue(20),new IntValue(25)),state.getOut().toList());
    }

    @Test
    void example8() throws MyException {
        PrgState state = run(Examples.EX8);
        assertEquals(Map.of(1,new IntValue(30)),state.getHeap().getContent());
        assertEquals(Map.of("v",new RefValue(1,new IntType())),state.getSymTable().toMap());
        assertEquals(List.of(new IntValue(20),new IntValue(35)),state.getOut().toList());
    }

    @Test
    void example9PrintsTwentyWithSafeCollector()  throws MyException {
        PrgState state = run(Examples.EX9);
        assertEquals(List.of(new IntValue(20)),state.getOut().toList());
    }

    @Test
    void example10() throws MyException {
        PrgState state = run(Examples.EX10);
        assertEquals(List.of(new IntValue(4),new IntValue(3),new IntValue(2),new IntValue(1),new IntValue(0)),
                state.getOut().toList());
    }
}