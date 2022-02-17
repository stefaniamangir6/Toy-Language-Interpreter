package Model.stmt.Files;

import Exception.InvalidTypeException;
import Exception.UndefinedVariableException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.IExp;
import Model.stmt.IStmt;
import Model.types.IType;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.IntValue;

import java.io.BufferedReader;

public class ReadFileStmt implements IStmt {
    private final IExp filePath;
    private final String varName;

    public ReadFileStmt(IExp filePath, String varName) {
        this.filePath = filePath;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IDict<String, IValue> symbolTable = state.getSymTable();
        IDict<String, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, IValue> heap = state.getHeapTable();

        if (!symbolTable.isDefined(this.varName)) {
            throw new UndefinedVariableException("ReadFileStatement: " + this.varName + " is not defined in the symbolTable");
        }

        IValue filePathValue = this.filePath.eval(symbolTable, heap); // get the value of the exp
        String filePathString = filePathValue.toString();
        if (!fileTable.isDefined(filePathString)) {
            throw new UndefinedVariableException("ReadFileStatement: File path " + filePathString + " is not defined in the file table");
        }

        BufferedReader fileBuffer = fileTable.lookup(filePathString);
        String currentLine = fileBuffer.readLine();
        if (currentLine == null) {
            symbolTable.update(this.varName, new IntValue()); // default value for int = 0
        }
        else {
            symbolTable.update(this.varName, new IntValue(Integer.parseInt(currentLine)));
        }

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("readFile(" + this.filePath.toString() + ");\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (!initialTypeEnvironment.isDefined(this.varName)) {
            throw new UndefinedVariableException("ReadFileStatement: " + this.varName + " is not defined in the typeEnvironment");
        }
        if (!initialTypeEnvironment.lookup(this.varName).equals(new IntType())) {
            throw new InvalidTypeException("ReadFileStatement: " + this.varName + " is not an integer");
        }
        if (!this.filePath.typeCheck(initialTypeEnvironment).equals(new StringType())) {
            throw new InvalidTypeException("ReadFileStatement: file path should be a stringValue");
        }
        return initialTypeEnvironment;
    }

}
