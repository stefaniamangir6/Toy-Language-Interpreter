package Model.types;

import Model.value.IValue;

public interface IType {
    public IValue defaultValue();
    IType deepCopy();
}
