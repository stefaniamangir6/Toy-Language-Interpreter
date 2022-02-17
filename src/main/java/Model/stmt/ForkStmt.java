package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyDict;
import Model.adt.MyStack;
import Model.types.IType;
import Model.value.IValue;

public class ForkStmt implements IStmt{
    private final IStmt threadStmts;

    public ForkStmt(IStmt threadStmts) {
        this.threadStmts = threadStmts;
    }

    @Override
    public PrgState execute(PrgState parentThread) throws Exception {
        if (this.threadStmts == null) {
            return null;
        }

        IStack<IStmt> stack = new MyStack<IStmt>();
        IDict<String, IValue> symbolTable = new MyDict<String,IValue>();
        symbolTable = parentThread.getSymTable().clone();

        return new PrgState(stack, symbolTable,
                parentThread.getOutput(),
                parentThread.getFileTable(),
                parentThread.getHeapTable(),
                this.threadStmts);
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("fork\n(\n" + this.threadStmts.toString() + ")\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        this.threadStmts.getTypeEnvironment(initialTypeEnvironment.clone());
        return initialTypeEnvironment;
    }
}
