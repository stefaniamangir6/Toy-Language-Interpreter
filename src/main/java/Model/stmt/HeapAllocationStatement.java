package Model.stmt;

import Exception.InvalidTypeException;
import Exception.UndefinedVariableException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.IExp;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapAllocationStatement implements IStmt{
    private final String variableName;
    private final IExp expression;

    public HeapAllocationStatement(String variableName, IExp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IDict<String, IValue> symbolTable = crtState.getSymTable();
        IHeap<Integer, IValue> heap = crtState.getHeapTable();

        if (!symbolTable.isDefined(this.variableName)) {
            throw new UndefinedVariableException("HeapAllocationStatement: " + this.variableName + " is not defined in the symbol table");
        }

        IValue variableValue = symbolTable.lookup(this.variableName);
        IValue expressionValue = this.expression.eval(symbolTable, heap);
        IType referencedType = ((RefValue)variableValue).getReferencedType();

        int newPositionInHeap = heap.add(expressionValue);
        symbolTable.update(this.variableName, new RefValue(newPositionInHeap, referencedType));

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("new(" + this.variableName + ", " + this.expression.toString() + ");\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        IType expressionReferenceType = new RefType(this.expression.typeCheck(initialTypeEnvironment));

        if (!initialTypeEnvironment.isDefined(this.variableName)) {
            throw new UndefinedVariableException("HeapAllocationStatement: " + this.variableName + " is not defined in the typeEnvironment");
        }
        if (!initialTypeEnvironment.lookup(this.variableName).equals(expressionReferenceType)) {
            throw new InvalidTypeException("HeapAllocationStatement: Expression cannot be evaluated to a " + expressionReferenceType.toString());
        }
        return initialTypeEnvironment;
    }
}
