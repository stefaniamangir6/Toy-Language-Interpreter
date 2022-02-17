package Exception;

public class InvalidOperandException extends Exception{
    public InvalidOperandException() {
        super("Invalid operand");
    }

    public InvalidOperandException(String message) {
        super(message);
    }
}

