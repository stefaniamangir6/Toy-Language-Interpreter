package Exception;

public class UndefinedVariableException extends Exception{
    public UndefinedVariableException() {
        super("Undefined variable");
    }

    public UndefinedVariableException(String message) {
        super(message);
    }
}
