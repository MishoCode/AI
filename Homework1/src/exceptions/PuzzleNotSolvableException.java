package exceptions;

public class PuzzleNotSolvableException extends Exception {
    public PuzzleNotSolvableException(String message) {
        super(message);
    }
}
