package Model;

import Exception.EmptyADTException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IList;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Model.value.IValue;

import java.io.BufferedReader;

public class PrgState {

    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IList<IValue> out;
    IStmt originalProgram; //optional field
    IDict<String, BufferedReader> fileTable;
    IHeap<Integer, IValue> heapTable;
    private static int threadCount = 1;
    private final int threadID;

    public PrgState(IStack<IStmt> stack, IDict<String, IValue> symTable,  IList<IValue> out, IDict<String, BufferedReader> fileTable, IHeap<Integer, IValue> heapTable, IStmt prg){
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        exeStack.push(prg);
        this.threadID = PrgState.manageThreadID();
    }

    public static synchronized int manageThreadID() {
        int newThreadID = PrgState.threadCount;
        PrgState.threadCount += 1;
        return newThreadID;
    }

    public int getThreadID() {
        return this.threadID;
    }

    public int getThreadCount() {
        return PrgState.threadCount;
    }


    public IList<IValue> getOutput() {
        return this.out;
    }

    public IStack<IStmt> getExecutionStack() {
        return this.exeStack;
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IDict<String, BufferedReader> getFileTable() {return this.fileTable;}

    public IHeap<Integer, IValue> getHeapTable() {return this.heapTable;}

    public boolean isCompleted() {
        return (this.exeStack.size() == 0);
    }

    public PrgState oneStep() throws Exception{
        if (this.exeStack.size() == 0) {
            throw new EmptyADTException("PrgState stack is empty");
        }
        IStmt crtStmt = this.exeStack.pop();
        return crtStmt.execute(this);

    }

    public String toString(){
        String representation = "";

        representation += "\n======== ThreadID: " + Integer.toString(this.threadID) + " ========\n";
        representation += "ExecutionStack:\n";
        representation += this.exeStack.toString();
        representation += "\nSymbolTable:\n";
        representation += this.symTable.toString();
        representation += "\nOutputTable:\n";
        representation += this.out.toString();
        representation += "\nFileTable:\n";
        representation += this.fileTable.toString1();
        representation += "\nHeapTable:\n";
        representation += this.heapTable.toString();

        return representation;
    }

}