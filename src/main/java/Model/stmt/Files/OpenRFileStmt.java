package Model.stmt.Files;

import Exception.AlreadyDefinedVariable;
import Exception.InvalidOperandException;
import Exception.InvalidTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.IExp;
import Model.stmt.IStmt;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt {
    private final IExp filePath;
    //String filename;
    //String varFileId;


    public OpenRFileStmt(IExp filePath) {
        this.filePath = filePath;

    }

    @Override
    public PrgState execute(PrgState state) throws IOException, FileNotFoundException, Exception {

        IDict<String, IValue> symbolTable = state.getSymTable();
        IDict<String, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, IValue> heap = state.getHeapTable();

        IValue filePathValue = this.filePath.eval(symbolTable, heap); // get the value of the exp

        if (filePathValue.getType().equals(new StringType())) {
            String filePathString = filePathValue.toString();
            if (fileTable.isDefined(filePathString)) {
                throw new AlreadyDefinedVariable("Variable " + filePath + " is already defined in the fileTable");
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(filePathString));
                fileTable.add(filePathString, reader);
            }

        } else
            throw new InvalidOperandException("The file is not a string");
        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("openRead(" + this.filePath.toString() + ");\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (!this.filePath.typeCheck(initialTypeEnvironment).equals(new StringType())) {
            throw new InvalidTypeException("file path should be a stringValue");
        }
        return initialTypeEnvironment;
    }

}