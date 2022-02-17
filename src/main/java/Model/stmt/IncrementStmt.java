package Model.stmt;

import Exception.InvalidOperatorException;
import Exception.InvalidTypeException;
import Exception.UndefinedVariableException;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.Objects;

public class IncrementStmt implements IStmt{
    private final String variableName;
    private final String operator;
    private final int incrementValue;

    public IncrementStmt(String variableName, String operator) {
        this.variableName = variableName;
        this.operator = operator;
        this.incrementValue = 1;
    }

    public IncrementStmt(String variableName, String operator, int incrementValue) {
        this.variableName = variableName;
        this.operator = operator;
        this.incrementValue = incrementValue;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IDict<String, IValue> symbolTable = crtState.getSymTable();
        if (!symbolTable.isDefined(this.variableName)) {
            throw new UndefinedVariableException("IncrementStatement: variable " + this.variableName + " is undefined in the symbolTable");
        }

        int previousValueAsInteger = ((IntValue)symbolTable.lookup(this.variableName)).getValue();
        if (Objects.equals(this.operator, "+")) {
            symbolTable.update(this.variableName, new IntValue(previousValueAsInteger + this.incrementValue));
        }
        else if(Objects.equals(this.operator, "-")) {
            symbolTable.update(this.variableName, new IntValue(previousValueAsInteger - this.incrementValue));
        }
        else {
            throw new InvalidOperatorException("IncrementStatement: operator should be either + or -, not " + this.operator);
        }

        return null;
    }

    public String toString() {
        String representation = "";
        representation += this.variableName;

        if (this.incrementValue == 1) {
            representation += (this.operator + this.operator + ";\n"); // beware that wrong operators will still be displayed here!
        }
        else {
            representation += (this.operator + "= " + Integer.toString(this.incrementValue) + ";\n");
        }

        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (!initialTypeEnvironment.isDefined(this.variableName)) {
            throw new UndefinedVariableException("IncrementStatement: variable " + this.variableName + " is undefined in the typeEnvironment");
        }
        if (!initialTypeEnvironment.lookup(this.variableName).equals(new IntType())) {
            throw new InvalidTypeException("IncrementStatement: variable " + this.variableName + " should be an integer");
        }
        return initialTypeEnvironment;
    }
}
