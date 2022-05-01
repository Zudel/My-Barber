package engineering.exception;

import java.io.Serial;

public class DuplicatedServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = -1087345248663858448L;

    public DuplicatedServiceException() {

        super();

    }

    public DuplicatedServiceException(String message) {

        super(message);

    }

}
