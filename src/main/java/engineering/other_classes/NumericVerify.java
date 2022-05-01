package engineering.other_classes;

public class NumericVerify {

    private NumericVerify(){}

    public static boolean isNumeric(String insertString) {
        try{
            Double.parseDouble(insertString);
            return true;
        }catch (NumberFormatException e){
            return false;
        }

    }

}
