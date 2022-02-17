package Model.value;

import Model.types.BoolType;
import Model.types.IType;

public class BoolValue implements IValue{
    private final boolean value;

    public BoolValue(){
        this.value = false;
    }

    public BoolValue(boolean val){
        this.value = val;
    }

    @Override
    public boolean equals(Object o){
        return (o instanceof BoolValue && ((BoolValue)o).getValue() == this.value);
    }

    public String toString(){
        return this.value ? "true" : "false";
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    public boolean getValue(){
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(this.value);
    }
}
