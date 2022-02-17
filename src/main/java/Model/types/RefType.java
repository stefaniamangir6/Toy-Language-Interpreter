package Model.types;

import Model.value.IValue;
import Model.value.RefValue;

public class RefType implements IType{
    private final IType innerType;

    public RefType(IType  innerType) {
        this.innerType = innerType;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefType) {
            return this.innerType.equals(((RefType)another).getInnerType());
        }
        return false;
    }

    @Override
    public IValue defaultValue() {
        // when the heap address is not provided, the ReferenceValue is initialised with the default one (0)
        return new RefValue(this.innerType);
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.innerType);
    }

    public IType  getInnerType() {
        return this.innerType;
    }

    public String toString() {
        String representation = "";
        representation += ("Ref(" + this.innerType.toString() + ")");
        return representation;
    }
}
