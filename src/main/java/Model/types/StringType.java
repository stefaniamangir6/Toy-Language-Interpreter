package Model.types;

import Model.value.IValue;
import Model.value.StringValue;

public class StringType implements IType{
    @Override
    public IValue defaultValue() {
        return new StringValue();
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof StringType);
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString(){
        return "string";
    }
}
