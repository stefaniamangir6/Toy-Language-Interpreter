package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.IExp;
import Model.exp.ValExpr;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;
import Exception.InvalidTypeException;

public class SleepStmt implements IStmt {
    private final IExp sleepTimeExpression;

    public SleepStmt(IExp sleepTimeExpression) {
        this.sleepTimeExpression = sleepTimeExpression;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IDict<String, IValue> symbolTable = crtState.getSymTable();
       IHeap<Integer, IValue> heap = crtState.getHeapTable();
        IStack<IStmt> stack = crtState.getExecutionStack();

        int sleepTimeAsInteger = ((IntValue)this.sleepTimeExpression.eval(symbolTable, heap)).getValue();
        if (sleepTimeAsInteger > 0) {
            stack.push(new SleepStmt(new ValExpr(new IntValue(sleepTimeAsInteger - 1))));
        }

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("sleep(" + this.sleepTimeExpression.toString() + ");\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(
            IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (!this.sleepTimeExpression.typeCheck(initialTypeEnvironment).equals(new IntType())) {
            throw new InvalidTypeException("sleep time should be an integer");
        }
        return initialTypeEnvironment;
    }
}
