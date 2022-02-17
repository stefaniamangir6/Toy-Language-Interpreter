package Model.stmt;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    // execution method for a statement
    String toString();
    IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception;
}
