package engineering.exception;

public class DuplicatedServiceSelectedException extends Exception {

    public DuplicatedServiceSelectedException(){
        super();
    }
    public DuplicatedServiceSelectedException(String message){
        super(message);
    }
}
