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

public class WaitStmt implements IStmt {
    private final IExp waitTimeExpression;

    public WaitStmt(IExp waitTimeExpression) {
        this.waitTimeExpression = waitTimeExpression;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IDict<String, IValue> symbolTable = crtState.getSymTable();
        IHeap<Integer, IValue> heap = crtState.getHeapTable();
        IStack<IStmt> stack = crtState.getExecutionStack();

        int waitTimeAsInteger = ((IntValue)this.waitTimeExpression.eval(symbolTable, heap)).getValue();
        if (waitTimeAsInteger > 0) {
            crtState.getOutput().add(new IntValue(waitTimeAsInteger));
            stack.push(new WaitStmt(new ValExpr(new IntValue(waitTimeAsInteger - 1))));
        }

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("wait(" + this.waitTimeExpression.toString() + ");\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(
            IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (!this.waitTimeExpression.typeCheck(initialTypeEnvironment).equals(new IntType())) {
            throw new InvalidTypeException("WaitStatement: wait time should be an integer");
        }
        return initialTypeEnvironment;
    }
}
