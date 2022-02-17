package Model.stmt;

import Exception.InvalidTypeException;
import Exception.NotAMatchException;
import Exception.UndefinedVariableException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.IExp;
import Model.types.IType;
import Model.value.IValue;

public class AssignStmt implements IStmt{

    String varName; // left operator
    IExp expression; // right operator

    public AssignStmt(String varName, IExp expr){
        this.varName = varName;
        this.expression = expr;

    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        if (crtState.getSymTable().isDefined(this.varName)) {
            IValue val = this.expression.eval(crtState.getSymTable(), crtState.getHeapTable());
            IType typeId = (crtState.getSymTable().lookup(this.varName)).getType();
            if(val.getType().equals(typeId))
                crtState.getSymTable().update(this.varName, val);
            else
                throw new NotAMatchException("Declared type of variable and the type of the assigned expression do not match");
        }
        else
            throw new UndefinedVariableException("Variable " + this.varName + " is not defined in the symbolTable");

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += (this.varName + " = " + this.expression.toString() + ";\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (!initialTypeEnvironment.isDefined(this.varName)) {
            throw new UndefinedVariableException("AssignmentStatement: Variable " + this.varName + " is not defined in the typeEnvironment");
        }
        if (!initialTypeEnvironment.lookup(this.varName).equals(this.expression.typeCheck(initialTypeEnvironment))) {
            throw new InvalidTypeException("AssignmentStatement: type of " + this.varName + " doesn't match the expression's type");
        }
        return initialTypeEnvironment; // the type environment remains unchanged
    }

}
