package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {
    @TempDir
    Path dir; // a new empty folder for every test
    private PrgState state;
    private ValueExp file; // the expression "<dir>/numbers.in"
    private StringValue fileName;

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n");
        fileName = new StringValue(f.toString());
        file = new ValueExp(fileName);
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @AfterEach
    void closeOpenFile() throws IOException {
        for(BufferedReader reader : state.getFileTable().toMap().values())
            reader.close();
    }

    @Test
    void openingTheSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,() -> new OpenRFileStmt(file).execute(state));
    }

    @Test
    void openAddsFileToFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().isDefined(fileName));
    }

    @Test
    void openingANonStringThrows() {
        var stmt = new OpenRFileStmt(new ValueExp(new IntValue(4)));
        assertThrows(StatementException.class,() -> stmt.execute(state));
    }

    @Test
    void openingAMissingFileThrows() {
        var missing = new ValueExp(new StringValue(dir.resolve("missing.in").toString()));
        assertThrows(StatementException.class,() -> new OpenRFileStmt(missing).execute(state));
    }

    @Test
    void readGivesTheNextNumberThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void readIntoUndeclaredVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,() -> new ReadFileStmt(file, "y").execute(state));
    }

    @Test
    void readFromNotOpenedFileThrows() throws MyException {
        assertThrows(StatementException.class,() -> new ReadFileStmt(file, "x").execute(state));
    }

    @Test
    void readNonIntegerLineThrows() throws Exception {
        Path f = dir.resolve("bad.in");
        Files.writeString(f,"abc");
        var bad = new ValueExp(new StringValue(f.toString()));
        new OpenRFileStmt(bad).execute(state);
        assertThrows(StatementException.class,()->new ReadFileStmt(bad,"x").execute(state));
    }

    @Test
    void readIntoNonIntVariableThrows() throws MyException {
        new VarDeclStmt("z", new BoolType()).execute(state);
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,()->new ReadFileStmt(file,"z").execute(state));
    }

}