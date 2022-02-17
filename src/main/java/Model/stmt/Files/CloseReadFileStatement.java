package Model.stmt.Files;

import Exception.InvalidTypeException;
import Exception.UndefinedVariableException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.IExp;
import Model.stmt.IStmt;
import Model.types.IType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;

public class CloseReadFileStatement implements IStmt {
    private final IExp filePath;

    public CloseReadFileStatement(IExp filePath) {
        this.filePath = filePath;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IDict<String, IValue> symbolTable = crtState.getSymTable();
        IDict<String, BufferedReader> fileTable = crtState.getFileTable();
        IHeap<Integer, IValue> heap = crtState.getHeapTable();

        IValue filePathValue = this.filePath.eval(symbolTable, heap);

        String filePathString = ((StringValue) filePathValue).getValue();
        if (!fileTable.isDefined(filePathString)) {
            throw new UndefinedVariableException("CloseReadFileStatement: File path " + filePathString + " is not defined in the file table");
        }

        BufferedReader fileBuffer = fileTable.lookup(filePathString);
        fileBuffer.close();
        fileTable.remove(filePathString);

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("closeRead(" + this.filePath.toString() + ");\n");
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
