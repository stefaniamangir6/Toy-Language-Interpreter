package Model.stmt;

import Exception.AlreadyDefinedVariable;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.*;

public class VarDeclStmt implements IStmt {
    String varName;
    IType varType;

    public VarDeclStmt(String varName, IType varType) {
        this.varName = varName;
        this.varType = varType;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {

        if (crtState.getSymTable().isDefined(this.varName)) {
            throw new AlreadyDefinedVariable("Variable " + this.varName + " is already defined in the symbolTable");
        }

        if (this.varType instanceof IntType) { // variable is of type int
            crtState.getSymTable().add(this.varName, this.varType.defaultValue());
        }
        else if(this.varType instanceof BoolType) { // variable is of type bool
            crtState.getSymTable().add(this.varName, this.varType.defaultValue());
        }
        else if(this.varType instanceof StringType) {
            crtState.getSymTable().add(this.varName, this.varType.defaultValue());
        }
        else if (this.varType instanceof RefType) {
            crtState.getSymTable().add(this.varName, this.varType.defaultValue());
        }
        return null;
    }

    public String toString() {
        String representation = "";
        representation += (this.varType.toString() + " " + this.varName + ";\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (initialTypeEnvironment.isDefined(this.varName)) {
            throw new AlreadyDefinedVariable("VariableDeclarationStatement: Variable " + this.varName + " is already defined in the typeEnvironment");
        }
        initialTypeEnvironment.add(this.varName, this.varType);
        return initialTypeEnvironment; // this is the only statement that changes the type environment
    }
}
