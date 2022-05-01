package engineering.exception;

import java.io.Serial;

public class InvalidInsertProductException extends Exception{

    @Serial
    private static final long serialVersionUID = -1087345299963421448L;

    public InvalidInsertProductException() {

        super();

    }

    public InvalidInsertProductException(String message) {

        super(message);

    }

}
