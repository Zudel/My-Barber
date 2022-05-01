package engineering.exception;

import java.io.Serial;

public class DuplicatedProductException extends Exception{

    @Serial
    private static final long serialVersionUID = -1087345248663421448L;

    public DuplicatedProductException() {

        super();

    }

    public DuplicatedProductException(String message) {

        super(message);

    }

}
