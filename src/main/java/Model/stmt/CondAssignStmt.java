package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.IExp;
import Model.types.IType;

public class CondAssignStmt implements IStmt {
    private final String varName;
    private final IExp conditionalExpr;
    private final IExp trueBranchExpr;
    private final IExp falseBranchExpr;

    public CondAssignStmt(String varName, IExp conditionalExpr, IExp trueBranchExpr, IExp falseBranchExpr) {
        this.varName = varName;
        this.conditionalExpr = conditionalExpr;
        this.trueBranchExpr = trueBranchExpr;
        this.falseBranchExpr = falseBranchExpr;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IStack<IStmt> stack = crtState.getExecutionStack();
        stack.push(new IfStmt(this.conditionalExpr,
                new AssignStmt(this.varName, this.trueBranchExpr),
                new AssignStmt(this.varName, this.falseBranchExpr)));
        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += (this.varName + " = " + this.conditionalExpr.toString() + " ? " + this.trueBranchExpr.toString() + " : " + this.falseBranchExpr.toString());
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(
            IDict<String, IType> initialTypeEnvironment) throws Exception {
        return initialTypeEnvironment;
    }
}