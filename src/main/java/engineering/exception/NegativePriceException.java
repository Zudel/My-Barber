package engineering.exception;

public class NegativePriceException extends Exception {
    public NegativePriceException() {
        super() ;
    }

    public NegativePriceException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
