package Model.stmt;

import Exception.InvalidOperandException;
import Exception.InvalidTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.IExp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class IfStmt implements IStmt {
    IExp condExpr;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(IExp e, IStmt t, IStmt el) {
        condExpr = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IValue condExprValue = this.condExpr.eval(crtState.getSymTable(), crtState.getHeapTable());
        if (condExprValue.getType().equals(new BoolType())) {
            if (((BoolValue) condExprValue).getValue() == true) {
                crtState.getExecutionStack().push(thenS);
            } else
                crtState.getExecutionStack().push(elseS);
        } else
            throw new InvalidOperandException("the conditional expression is not a boolean");
        return null;
    }

    @Override
    public String toString() {
        return "if(" + condExpr.toString() + ")\nthen(" + thenS.toString() + ")\n else(" + elseS.toString() + ")";
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (!this.condExpr.typeCheck(initialTypeEnvironment).equals(new BoolType())) {
            throw new InvalidTypeException("IfStatement: Conditional expression is not boolean");
        }
        this.thenS.getTypeEnvironment(initialTypeEnvironment.clone());
        this.elseS.getTypeEnvironment(initialTypeEnvironment.clone());
        return initialTypeEnvironment;
    }
}
