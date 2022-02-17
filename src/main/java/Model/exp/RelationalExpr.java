package Model.exp;

import Exception.InvalidOperatorException;
import Exception.InvalidTypeException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

public class RelationalExpr implements IExp{
    private final IExp firstExp;
    private final IExp secondExp;
    private final String operator;

    public RelationalExpr(IExp firstExp, IExp secondExp, String operator) {
        this.firstExp = firstExp;
        this.secondExp = secondExp;
        this.operator = operator;
    }

    @Override
    public IValue eval(IDict<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws Exception {
        IValue firstVal, secondVal;

        firstVal = this.firstExp.eval(symbolTable, heap);
        secondVal = this.secondExp.eval(symbolTable, heap);
        int firstInt = ((IntValue)firstVal).getValue();
        int secondInt = ((IntValue)secondVal).getValue();

        if (this.operator.equals("<")) {
            return new BoolValue(firstInt < secondInt);
        }
        if (this.operator.equals("<=")) {
            return new BoolValue(firstInt <= secondInt);
        }
        if (this.operator.equals(">")) {
            return new BoolValue(firstInt > secondInt);
        }
        if (this.operator.equals(">=")) {
            return new BoolValue(firstInt >= secondInt);
        }
        if (this.operator.equals("==")) {
            return new BoolValue(firstInt == secondInt);
        }
        if (this.operator.equals("!=")) {
            return new BoolValue(firstInt != secondInt);
        }
        else {
            throw new InvalidOperatorException("RelationalExpression: Invalid operator");
        }

    }

    @Override
    public String toString() {
        String representation = "";
        representation += (this.firstExp.toString());
        representation += (" " + this.operator + " ");
        representation += (this.secondExp.toString());
        return representation;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws Exception {
        IType firstType, secondType, intType;
        firstType = this.firstExp.typeCheck(typeEnvironment);
        secondType = this.secondExp.typeCheck(typeEnvironment);
        intType = new IntType();

        if (!firstType.equals(intType)) {
            throw new InvalidTypeException("RelationalExpression: First expression is not an integer");
        }
        if (!secondType.equals(intType)) {
            throw new InvalidTypeException("RelationalExpression: Second expression is not an integer");
        }

        return new BoolType();
    }

}
