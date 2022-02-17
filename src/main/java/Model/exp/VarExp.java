package Model.exp;

import Exception.UndefinedVariableException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class VarExp implements IExp{
    String variableName;

    public VarExp(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public IValue eval(IDict<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws Exception {
        if (!symbolTable.isDefined(this.variableName)) {
            throw new UndefinedVariableException("Variable " + this.variableName + " is not defined");
        }
        return symbolTable.lookup(this.variableName);
    }

    @Override
    public String toString() {
        String representation = "";
        representation += (this.variableName);
        return representation;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws Exception {
        if (!typeEnvironment.isDefined(this.variableName)) {
            throw new UndefinedVariableException("VariableExpression: variable " + this.variableName + " is undefined in the type environment");
        }
        return typeEnvironment.lookup(this.variableName);
    }


}
