package Model.value;

import Model.types.IType;
import Model.types.RefType;

public class RefValue implements IValue{
    private final int heapAddress;
    private final IType referencedType;

    public RefValue(int heapAddress, IType referencedType) {
        this.heapAddress = heapAddress;
        this.referencedType = referencedType;
    }

    public RefValue(IType referencedType) {
        this.heapAddress = 0;
        this.referencedType = referencedType;
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof RefValue && ((RefValue)another).getHeapAddress() == this.heapAddress);
    }

    public int getHeapAddress() {
        return this.heapAddress;
    }

    public IType getReferencedType() {
        return this.referencedType;
    }

    @Override
    public IType getType() {
        return new RefType(this.referencedType);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(this.referencedType);
    }

    public String toString() {
        String representation = "";
        representation += ("(" + Integer.toHexString(this.heapAddress) + ", " + this.referencedType.toString() + ")");
        return representation;
    }
}
