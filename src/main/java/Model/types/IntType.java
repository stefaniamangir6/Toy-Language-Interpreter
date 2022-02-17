package Model.types;

import Model.value.IValue;
import Model.value.IntValue;

public class IntType implements IType{
    @Override
    public IValue defaultValue() {
        return new IntValue();
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof IntType);
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString(){
        return "int";
    }
}
