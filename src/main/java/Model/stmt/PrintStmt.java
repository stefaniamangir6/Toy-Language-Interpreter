package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.exp.IExp;
import Model.types.IType;

public class PrintStmt implements IStmt{
    IExp toPrintExpr;

    public PrintStmt(IExp toPrintExpr) {
        this.toPrintExpr = toPrintExpr;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception{
        crtState.getOutput().add(this.toPrintExpr.eval(crtState.getSymTable(), crtState.getHeapTable()));
        return null;
    }

    public String toString() {
        String representation = "";
        representation += ("print(" + this.toPrintExpr.toString() + ");\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        this.toPrintExpr.typeCheck(initialTypeEnvironment); // throws an exception if the expression's type is invalid
        return initialTypeEnvironment; // the type environment is not modified
    }
}
