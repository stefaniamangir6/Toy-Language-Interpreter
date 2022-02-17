package Model.value;

import Model.types.IType;
import Model.types.IntType;

public class IntValue implements IValue{
    private final int value;

    public IntValue(){
        this.value = 0;
    }

    public IntValue(int val){
        this.value = val;
    }

    @Override
    public boolean equals(Object o){
        return (o instanceof IntValue && ((IntValue)o).getValue() == this.value);
    }

    public String toString(){
        return Integer.toString(this.value);
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }
}
