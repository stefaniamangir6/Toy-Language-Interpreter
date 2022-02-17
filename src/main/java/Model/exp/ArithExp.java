package Model.exp;

import Exception.DivisionByZeroException;
import Exception.InvalidOperandException;
import Exception.InvalidOperatorException;
import Exception.InvalidTypeException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ArithExp implements IExp{
    IExp firstExp;
    IExp secondExp;
    String operator;

    public ArithExp(IExp firstExp, IExp secondExp, String operator) {
        this.firstExp = firstExp;
        this.secondExp = secondExp;
        this.operator = operator;
    }

    @Override
    public IValue eval(IDict<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws Exception {
        IValue firstVal, secondVal;

        firstVal = this.firstExp.eval(symbolTable, heap);
        if(firstVal.getType().equals(new IntType()))
        {
            secondVal = this.secondExp.eval(symbolTable, heap);
            if(secondVal.getType().equals(new IntType())) {
                int firstInt = ((IntValue) firstVal).getValue();
                int secondInt = ((IntValue) secondVal).getValue();
                if (this.operator.equals("+")) {
                    return new IntValue(firstInt + secondInt);
                }
                if (this.operator.equals("-")) {
                    return new IntValue(firstInt - secondInt);
                }
                if (this.operator.equals("*")) {
                    return new IntValue(firstInt * secondInt);
                }
                if (this.operator.equals("/")) {
                    if (secondInt == 0) {
                        throw new DivisionByZeroException("Division by zero");
                    }
                    return new IntValue(firstInt / secondInt);
                }
                else
                    throw new InvalidOperatorException("Invalid operator");
            }else
                throw new InvalidOperandException("Second operand is not an integer");
        }else
            throw new InvalidOperandException("First operand is not an integer");

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
            throw new InvalidTypeException("First expression is not an integer");
        }
        if (!secondType.equals(intType)) {
            throw new InvalidTypeException("Second expression is not an integer");
        }

        return intType;
    }

}
