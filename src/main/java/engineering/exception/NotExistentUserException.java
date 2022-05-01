package engineering.exception;

public class NotExistentUserException extends Exception {

    public NotExistentUserException() {
        super() ;
    }

    public NotExistentUserException(String errorMessage) {
        super(errorMessage) ;
    }
}
