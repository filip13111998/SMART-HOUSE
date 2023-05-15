package sbnz.ftn.uns.ac.rs.ADMIN.exception;

public class JWTBlackListException extends RuntimeException{
    public JWTBlackListException() {
        super();
    }
    public JWTBlackListException(String message, Throwable cause) {
        super(message, cause);
    }
    public JWTBlackListException(String message) {
        super(message);
    }
    public JWTBlackListException(Throwable cause) {
        super(cause);
    }
}
