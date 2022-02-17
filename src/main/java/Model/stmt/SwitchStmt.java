package Model.stmt;


import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.IExp;
import Model.types.IType;
import Model.value.IValue;

import java.util.ArrayList;
import Exception.InvalidTypeException;

public class SwitchStmt implements IStmt {
    private final IExp switchExpr;
    private final ArrayList<IExp> caseExpList;
    private final ArrayList<IStmt> caseStmtList;

    public SwitchStmt(IExp switchExpr, ArrayList<IExp> caseExpList, ArrayList<IStmt> caseStmtList) {
        this.switchExpr = switchExpr;
        this.caseExpList = caseExpList;
        this.caseStmtList = caseStmtList;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        if (this.caseExpList.size() + 1 != this.caseStmtList.size()) {
            throw new Exception("SwitchStatement: number of case expressions does not match the number of case statements");
        }

        IDict<String, IValue> symbolTable = crtState.getSymTable();
        IHeap<Integer, IValue> heap = crtState.getHeapTable();
        IStack<IStmt> stack = crtState.getExecutionStack();

        int pos;
       IValue switchExprValue = this.switchExpr.eval(symbolTable, heap);
        for (pos = 0; pos < this.caseExpList.size(); pos++) {
            if (this.caseExpList.get(pos).eval(symbolTable, heap).equals(switchExprValue)) {
                stack.push(this.caseStmtList.get(pos));
                return null;
            }
        }

        // the 'default' case
        stack.push(this.caseStmtList.get(pos));

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("switch(" + this.switchExpr.toString() + ")\n");

        int pos;
        for (pos = 0; pos < this.caseExpList.size(); pos++) {
            representation += "case (" + this.caseExpList.get(pos).toString() + ") \n{\n" + this.caseStmtList.get(pos) + "}\n";
        }
        representation += "default\n{\n" + this.caseStmtList.get(pos) + "}\n";

        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(
            IDict<String, IType> initialTypeEnvironment) throws Exception {
        IType switchExprType = this.switchExpr.typeCheck(initialTypeEnvironment);

        for (IExp caseExpression : this.caseExpList) {
            if (!caseExpression.typeCheck(initialTypeEnvironment).equals(switchExprType)) {
                throw new InvalidTypeException("SwitchStatement: expression does not match the type of the switch expression");
            }
        }
        for (IStmt caseStatement : this.caseStmtList) {
            caseStatement.getTypeEnvironment(initialTypeEnvironment.clone());
        }

        return initialTypeEnvironment;
    }
}
