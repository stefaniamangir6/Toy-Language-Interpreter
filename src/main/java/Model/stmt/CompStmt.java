package Model.stmt;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public class CompStmt implements IStmt{
    IStmt firstStmt;
    IStmt secondStmt;

    public CompStmt(IStmt firstStmt, IStmt secondStmt) {
        this.firstStmt = firstStmt;
        this.secondStmt = secondStmt;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        crtState.getExecutionStack().push(this.secondStmt);
        crtState.getExecutionStack().push(this.firstStmt);
        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += (this.firstStmt.toString() + this.secondStmt.toString());
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        return this.secondStmt.getTypeEnvironment(this.firstStmt.getTypeEnvironment(initialTypeEnvironment));
    }
}
