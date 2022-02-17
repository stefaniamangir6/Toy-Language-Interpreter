package Model.exp;

import Exception.InvalidOperandException;
import Exception.InvalidOperatorException;
import Exception.InvalidTypeException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class LogicExpr implements IExp{
    IExp firstExp;
    IExp secondExp;
    String operator;

    public LogicExpr(IExp firstExp, IExp secondExp, String operator) {
        this.firstExp = firstExp;
        this.secondExp = secondExp;
        this.operator = operator;
    }

    @Override
    public IValue eval(IDict<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws Exception {
        IValue firstVal, secondVal;

        firstVal = this.firstExp.eval(symbolTable, heap);
        if(firstVal.getType().equals(new BoolType()))
        {
            secondVal = this.secondExp.eval(symbolTable, heap);
            if(secondVal.getType().equals(new BoolType())) {
                boolean firstBoolean = ((BoolValue) firstVal).getValue();
                boolean secondBoolean = ((BoolValue) secondVal).getValue();
                if (this.operator.equals("&&")) {
                    return new BoolValue(firstBoolean && secondBoolean);
                }
                if (this.operator.equals("||")) {
                    return new BoolValue(firstBoolean || secondBoolean);
                }
                else
                    throw new InvalidOperatorException("Invalid operator");
            }else
                throw new InvalidOperandException("Second operand is not a boolean");
        }else
            throw new InvalidOperandException("First operand is not an boolean");

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
        IType firstType, secondType, boolType;
        firstType = this.firstExp.typeCheck(typeEnvironment);
        secondType = this.secondExp.typeCheck(typeEnvironment);
        boolType = new BoolType();

        if (!firstType.equals(boolType)) {
            throw new InvalidTypeException("First expression is not a boolean");
        }
        if (!secondType.equals(boolType)) {
            throw new InvalidTypeException("Second expression is not a boolean");
        }

        return boolType;
    }
}
