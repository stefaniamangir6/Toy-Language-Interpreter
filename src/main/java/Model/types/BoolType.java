package Model.types;

import Model.value.BoolValue;
import Model.value.IValue;

public class BoolType implements IType{
    @Override
    public IValue defaultValue() {
        return new BoolValue();
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof BoolType);
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString(){
        return "bool";
    }
}
