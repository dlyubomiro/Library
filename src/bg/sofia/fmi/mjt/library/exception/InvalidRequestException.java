package bg.sofia.fmi.mjt.library.exception;

public class InvalidRequestException extends Exception {
    public InvalidRequestException(String s) {
        super(s);
    }

    public InvalidRequestException(String s, Throwable t) {
        super(s, t);
    }
}
