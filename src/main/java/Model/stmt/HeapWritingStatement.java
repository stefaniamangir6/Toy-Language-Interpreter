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

public class HeapWritingStatement implements IStmt{
    private final String variableName;
    private final IExp expression;

    public HeapWritingStatement(String variableName, IExp expression) {
        this.variableName = variableName; //heap address
        this.expression = expression; // new value that is going to be stored in the heap
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IDict<String, IValue> symbolTable = crtState.getSymTable();
        IHeap<Integer, IValue> heap = crtState.getHeapTable();
        if (!symbolTable.isDefined(this.variableName)) {

            throw new UndefinedVariableException("HeapWritingStatement: " + this.variableName + " is not defined in the symbol table");
        }

        IValue variableValue = symbolTable.lookup(this.variableName);
        int positionInHeap = ((RefValue)variableValue).getHeapAddress();
        if (!heap.isDefined(positionInHeap)) {
            throw new UndefinedVariableException("HeapWritingStatement: Undefined variable at address " + Integer.toHexString(positionInHeap));
        }

        IValue expressionValue = this.expression.eval(symbolTable, heap);
        heap.update(positionInHeap, expressionValue);

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("wH(" + this.variableName + ") = " + this.expression.toString() + ";\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        IType expressionReferenceType = new RefType(this.expression.typeCheck(initialTypeEnvironment));

        if (!initialTypeEnvironment.isDefined(this.variableName)) {
            throw new UndefinedVariableException(this.variableName + " is not defined in the typeEnvironment");
        }
        if (!initialTypeEnvironment.lookup(this.variableName).equals(expressionReferenceType)) {
            throw new InvalidTypeException("Expression cannot be evaluated to a " + expressionReferenceType.toString());
        }
        return initialTypeEnvironment;
    }

}
