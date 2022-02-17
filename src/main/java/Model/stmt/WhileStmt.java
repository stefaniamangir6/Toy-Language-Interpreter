package Model.stmt;

import Exception.InvalidTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.IExp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class WhileStmt implements IStmt{
    private final IExp condExpression;
    private final IStmt statement;
    private final boolean expectedLogicalValue; // for conditions like 'while (x == false)'

    public WhileStmt(IExp condExpression, IStmt statement) {
        this.condExpression = condExpression;
        this.statement = statement;
        this.expectedLogicalValue = true;
    }

    public WhileStmt(IExp conditionalExpression, IStmt statement, boolean expectedLogicalValue) {
        this.condExpression = conditionalExpression;
        this.statement = statement;
        this.expectedLogicalValue = expectedLogicalValue;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IStack<IStmt> stack = crtState.getExecutionStack();
        IDict<String, IValue> symbolTable = crtState.getSymTable();
        IHeap<Integer, IValue> heap = crtState.getHeapTable();

        IValue conditionalExpressionValue = this.condExpression.eval(symbolTable, heap);
        if (((BoolValue)conditionalExpressionValue).getValue() == expectedLogicalValue) {
            stack.push(this);
            return this.statement.execute(crtState);
        }

        return null; // if the condition is not met
    }

    public String toString() {
        String representation = "";
        String negationSymbolString = "";
        if (!this.expectedLogicalValue) {
            negationSymbolString += "! ";
        }
        representation += ("while (" + negationSymbolString + this.condExpression.toString() + ") {\n\t");
        representation += (this.statement.toString() + "}\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (!this.condExpression.typeCheck(initialTypeEnvironment).equals(new BoolType())) {
            throw new InvalidTypeException("WhileStatement: Conditional expression is not boolean");
        }
        this.statement.getTypeEnvironment(initialTypeEnvironment.clone());
        return initialTypeEnvironment;
    }
}
