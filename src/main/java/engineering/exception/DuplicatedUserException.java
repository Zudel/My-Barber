package engineering.exception;

public class DuplicatedUserException extends Exception {
    public DuplicatedUserException(String message){
        super(message);
    }
    public DuplicatedUserException(){
        super();
    }

}
