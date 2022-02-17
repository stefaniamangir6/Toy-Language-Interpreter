package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.IExp;
import Model.exp.RelationalExpr;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Exception.InvalidTypeException;
import Exception.AlreadyDefinedVariable;

public class ForStmt implements IStmt {
    private final String variableName;
    private final IStmt variableDeclarationStatement;
    private final IExp initialExpression; // for(int v = initialExpression...)
    private final IExp conditionalExpression; // relational expression
    private final IStmt finalStatement; // increment statement
    private final IStmt bodyStatement; //  inside the curly brackets

    public ForStmt(String variableName,
                   IExp initialExpression,
                   IExp conditionalExpression,
                   IStmt finalStatement,
                   IStmt bodyStatement) {
        this.variableName = variableName;
        this.variableDeclarationStatement = new VarDeclStmt(this.variableName, new IntType());
        // create it here because we also need it in the type check
        this.initialExpression = initialExpression;
        this.conditionalExpression = conditionalExpression;
        this.finalStatement = finalStatement;
        this.bodyStatement = bodyStatement;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        // rel expr because it checks if its operands are integers
        if (!(this.conditionalExpression instanceof RelationalExpr)) {
            throw new InvalidTypeException("Conditional expression is not a RelationalExpression");
        }
        if (!(this.finalStatement instanceof IncrementStmt)) {
            throw new InvalidTypeException("FinalStatement is not an IncrementStatement");
        }

        IStack<IStmt> stack = crtState.getExecutionStack();

        stack.push(new WhileStmt(
                this.conditionalExpression,
                new CompStmt(this.bodyStatement, this.finalStatement)
        ));
        // our for is basically a while statement which receives as params the cond expression
        // and the body statement together with the final stmt which is the increment one
        stack.push(new AssignStmt(this.variableName, this.initialExpression));
        // here we assign the initial value: ex (int i = 1)

        return this.variableDeclarationStatement.execute(crtState);
    }

    public String toString() {
        String representation = "";

        // remove the "\n" and the ";" at the end of each statement
        String initialStatementString = this.variableDeclarationStatement.toString();
        initialStatementString = initialStatementString.substring(0, initialStatementString.length() - 2);
        String finalStatementString = this.finalStatement.toString();
        finalStatementString = finalStatementString.substring(0, finalStatementString.length() - 2);

        representation += ("for (" + initialStatementString + " = " + this.initialExpression.toString() + "; " + this.conditionalExpression.toString() + "; " + finalStatementString + ") {\n\t");
        representation += (this.bodyStatement.toString() + "}\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(
            IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (initialTypeEnvironment.isDefined(this.variableName)) {
            throw new AlreadyDefinedVariable("ForStatement: variable " + this.variableName + " is already defined in the type environment");
        }
        initialTypeEnvironment = this.variableDeclarationStatement.getTypeEnvironment(initialTypeEnvironment);

        if (!this.initialExpression.typeCheck(initialTypeEnvironment).equals(new IntType())) {
            throw new InvalidTypeException("ForStatement: Initial assignment expression is not integer");
        }
        if (!this.conditionalExpression.typeCheck(initialTypeEnvironment).equals(new BoolType())) {
            throw new InvalidTypeException("ForStatement: The conditional expression is not boolean");
        }
        this.finalStatement.getTypeEnvironment(initialTypeEnvironment);
        this.bodyStatement.getTypeEnvironment(initialTypeEnvironment.clone());

        return initialTypeEnvironment;
    }
}
