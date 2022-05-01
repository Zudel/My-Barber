package engineering.exception;

import java.io.Serial;

public class InsertNegativePriceException extends Exception{

    @Serial
    private static final long serialVersionUID = -1087345248886858448L;

    public InsertNegativePriceException(){
        super();
    }

    public InsertNegativePriceException(String message) {
        super(message);
    }

}
