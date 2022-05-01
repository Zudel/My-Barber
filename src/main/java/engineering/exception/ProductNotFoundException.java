package engineering.exception;

import java.io.Serial;

public class ProductNotFoundException extends Exception{

    @Serial
    private static final long serialVersionUID = -1087345249983858448L;


    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}
