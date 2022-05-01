package second_view.barber;

import engineering.other_classes.Weekdays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import second_view.general.ScreenChanger;

import java.time.DateTimeException;
import java.time.LocalTime;

import static engineering.other_classes.NumericVerify.isNumeric;

public class BarberAddNewSaloonController {

    @FXML private TextField addSaloonCommandLine;
    @FXML private TextField addSaloonNameTextField;
    @FXML private TextField addSaloonAddressTextField;
    @FXML private TextField addSaloonCityTextField;
    @FXML private TextField addSaloonPhoneTextField;
    @FXML private TextField addSaloonSlotTimeTextField;
    @FXML private TextField addSaloonSeatsNumberTimeTextField;
    @FXML private TextField addSaloonOpenMorningTimeTextField;
    @FXML private TextField addSaloonCloseMorningTimeTextField;
    @FXML private TextField addSaloonOpenAfternoonTimeTextField;
    @FXML private TextField addSaloonCloseAfternoonTimeTextField;
    @FXML private TextField addSaloonClosedDayTextField;
    @FXML private Label errorAddSaloonLabel;

    private static final String SET_NAME_NEW_SALOON_COMMAND = "set name";
    private static final String SET_ADDRESS_NEW_SALOON_COMMAND = "set address";
    private static final String SET_CITY_NEW_SALOON_COMMAND = "set city";
    private static final String SET_PHONE_NEW_SALOON_COMMAND = "set phone number";
    private static final String SET_SLOT_TIME_NEW_SALOON_COMMAND = "set slot time";
    private static final String SET_SEATS_NUMBER_NEW_SALOON_COMMAND = "set seats number";
    private static final String SET_OPEN_MORNING_TIME_COMMAND = "set open morning time";
    private static final String SET_CLOSE_MORNING_TIME_COMMAND = "set close morning time";
    private static final String SET_OPEN_AFTERNOON_TIME_COMMAND = "set open afternoon time";
    private static final String SET_CLOSE_AFTERNOON_TIME_COMMAND = "set close afternoon time";
    private static final String SET_CLOSE_DAY_COMMAND = "set closed day";

    @FXML
    public void onCommand(ActionEvent event) {

        String addSaloonCommandLineLocal = addSaloonCommandLine.getText();
        Weekdays closedDay;
        addSaloonCommandLine.setText("");
        addSaloonCommandLine.setStyle(null);
        errorAddSaloonLabel.setText("");
        String newField;

        if(addSaloonCommandLineLocal.compareTo("back") == 0) {
            ScreenChanger.getInstance().onBack(event);
            return ;
        }
        else if(addSaloonCommandLineLocal.startsWith(SET_NAME_NEW_SALOON_COMMAND)) {
            newField = addSaloonCommandLineLocal.replace(SET_NAME_NEW_SALOON_COMMAND + " ", "");
            addSaloonNameTextField.setText(newField);
            return ;
        }
        else if(addSaloonCommandLineLocal.startsWith(SET_ADDRESS_NEW_SALOON_COMMAND) ) {
            newField = addSaloonCommandLineLocal.replace(SET_ADDRESS_NEW_SALOON_COMMAND + " ", "");
            addSaloonAddressTextField.setText(newField);
            return;
        }else if(addSaloonCommandLineLocal.startsWith(SET_CITY_NEW_SALOON_COMMAND)) {
            newField = addSaloonCommandLineLocal.replace(SET_CITY_NEW_SALOON_COMMAND + " ", "");
            addSaloonCityTextField.setText(newField);
            return;
        }
        else if(addSaloonCommandLineLocal.startsWith(SET_PHONE_NEW_SALOON_COMMAND)) {
            newField = addSaloonCommandLineLocal.replace(SET_PHONE_NEW_SALOON_COMMAND + " ", "");
            addSaloonPhoneTextField.setText(newField);
            return ;
        }
        else if(addSaloonCommandLineLocal.startsWith(SET_SLOT_TIME_NEW_SALOON_COMMAND)) {
            newField = addSaloonCommandLineLocal.replace(SET_PHONE_NEW_SALOON_COMMAND + " ", "");
            addSaloonSlotTimeTextField.setText(newField);
            return;
        }
        else if(addSaloonCommandLineLocal.startsWith(SET_SEATS_NUMBER_NEW_SALOON_COMMAND)) {
            newField = addSaloonCommandLineLocal.replace(SET_SEATS_NUMBER_NEW_SALOON_COMMAND + " ", "");
            if(isNumeric(newField)) {
                addSaloonSeatsNumberTimeTextField.setText(newField);
                return;
            }
        }
        else if((addSaloonCommandLineLocal.startsWith(SET_OPEN_MORNING_TIME_COMMAND) || addSaloonCommandLineLocal.startsWith(SET_CLOSE_MORNING_TIME_COMMAND)) && setMorningTime(addSaloonCommandLineLocal)){
            return;
        }
        else if((addSaloonCommandLineLocal.startsWith(SET_OPEN_AFTERNOON_TIME_COMMAND) || addSaloonCommandLineLocal.startsWith(SET_CLOSE_AFTERNOON_TIME_COMMAND)) && setAfternoonTime(addSaloonCommandLineLocal)) {
            return ;
        }
        else if(addSaloonCommandLineLocal.startsWith(SET_CLOSE_DAY_COMMAND)) {
            newField = addSaloonCommandLineLocal.replace(SET_CLOSE_DAY_COMMAND + " ", "");
            switch (newField) {
                case "Monday" -> {
                    closedDay = Weekdays.MONDAY;
                    addSaloonClosedDayTextField.setText(newField);
                    return;
                }
                case "Tuesday" -> {
                    closedDay = Weekdays.TUESDAY;
                    addSaloonClosedDayTextField.setText(newField);
                    return;
                }
                case "Wednesday" -> {
                    closedDay = Weekdays.WEDNESDAY;
                    addSaloonClosedDayTextField.setText(newField);
                    return;
                }
                case "Thursday" -> {
                    closedDay = Weekdays.THURSDAY;
                    addSaloonClosedDayTextField.setText(newField);
                    return;
                }
                case "Friday" -> {
                    closedDay = Weekdays.FRIDAY;
                    addSaloonClosedDayTextField.setText(newField);
                    return;
                }
                case "Saturday" -> {
                    closedDay = Weekdays.SATURDAY;
                    addSaloonClosedDayTextField.setText(newField);
                    return;
                }
            }

        }

        addSaloonCommandLine.setStyle("-fx-border-color: red");

    }

    private boolean setAfternoonTime(String commandLine) {

        String[] afternoonTimes;
        LocalTime afternoonLocalTime;

        if(commandLine.startsWith(SET_OPEN_AFTERNOON_TIME_COMMAND)) {
            afternoonTimes = commandLine.replace(SET_OPEN_AFTERNOON_TIME_COMMAND + " ", "").split(":");
            if(isNumeric(afternoonTimes[0]) && isNumeric(afternoonTimes[1]) && isLocalTime(afternoonTimes) != null) {
                afternoonLocalTime = isLocalTime(afternoonTimes);
                addSaloonOpenAfternoonTimeTextField.setText(String.valueOf(afternoonLocalTime));
                return true;
            }
        }
        else if(commandLine.startsWith(SET_CLOSE_AFTERNOON_TIME_COMMAND)) {
            afternoonTimes = commandLine.replace(SET_CLOSE_AFTERNOON_TIME_COMMAND + " ", "").split(":");
            if(isNumeric(afternoonTimes[0]) && isNumeric(afternoonTimes[1]) && isLocalTime(afternoonTimes) != null) {
                afternoonLocalTime = isLocalTime(afternoonTimes);
                addSaloonCloseAfternoonTimeTextField.setText(String.valueOf(afternoonLocalTime));
                return true;
            }
        }

        return false;

    }

    private boolean setMorningTime(String commandLine) {

        String[] morningTimes;
        LocalTime morningLocalTime;

        if(commandLine.startsWith(SET_OPEN_MORNING_TIME_COMMAND)) {
            morningTimes = commandLine.replace(SET_OPEN_MORNING_TIME_COMMAND + " ", "").split(":");
            if(isNumeric(morningTimes[0]) && isNumeric(morningTimes[1]) && isLocalTime(morningTimes) != null) {
                morningLocalTime = isLocalTime(morningTimes);
                addSaloonOpenMorningTimeTextField.setText(String.valueOf(morningLocalTime));
                return true;
            }
        }
        else if(commandLine.startsWith(SET_CLOSE_MORNING_TIME_COMMAND)) {
            morningTimes = commandLine.replace(SET_OPEN_AFTERNOON_TIME_COMMAND + " ", "").split(":");
            if(isNumeric(morningTimes[0]) && isNumeric(morningTimes[1]) && isLocalTime(morningTimes) != null) {
                morningLocalTime = isLocalTime(morningTimes);
                addSaloonCloseMorningTimeTextField.setText(String.valueOf(morningLocalTime));
                return true;
            }
        }

        return false;

    }

    private LocalTime isLocalTime(String[] times) {

        try {
            return LocalTime.of(Integer.parseInt(times[0]), Integer.parseInt(times[1]));

        } catch (DateTimeException e) {

            return null;

        }

    }
}