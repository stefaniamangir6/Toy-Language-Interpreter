package Model.value;

import Model.types.IType;
import Model.types.StringType;

import java.util.Objects;

public class StringValue implements IValue{
    private final String value;

    public StringValue(){
        this.value = "";
    }

    public StringValue(String val){
        this.value = val;
    }

    @Override
    public boolean equals(Object o){
        return (o instanceof StringValue && Objects.equals(((StringValue) o).getValue(), this.value));
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    public String toString() {
        String representation = "";
        representation += this.value;
        return representation;
    }
}
