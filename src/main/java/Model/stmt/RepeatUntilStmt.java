package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.IExp;
import Model.types.BoolType;
import Model.types.IType;
import Exception.InvalidTypeException;

public class RepeatUntilStmt implements IStmt {
    private final IStmt stmt;
    private final IExp conditionalExpr;

    public RepeatUntilStmt(IStmt stmt, IExp conditionalExpr) {
        this.stmt = stmt;
        this.conditionalExpr = conditionalExpr;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IStack<IStmt> stack = crtState.getExecutionStack();
        stack.push(new WhileStmt(this.conditionalExpr, this.stmt, false));
        // the while condition needs to be false (<=> not true)
        stack.push(this.stmt); // the first step is always executed regardless of the condition
        return null;
    }

    public String toString() {
        String representation = "";
        representation += ("repeat {\n" + this.stmt.toString() + "} ");
        representation += (" until (" + this.conditionalExpr.toString() + ");\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(
            IDict<String, IType> initialTypeEnvironment) throws Exception {
        // verifies the conditional expression to be bool
        if (!this.conditionalExpr.typeCheck(initialTypeEnvironment).equals(new BoolType())) {
            throw new InvalidTypeException("Conditional expression is not boolean");
        }
        this.stmt.getTypeEnvironment(initialTypeEnvironment.clone());
        // type checks stmt 1
        return initialTypeEnvironment;
    }
}
