package engineering.bean;

import engineering.other_classes.Weekdays;
import java.sql.Time;
import java.time.LocalTime;

public class SaloonBean {

    private String name;
    private String address;
    private String phone;
    private String city;
    private LocalTime slotTime;
    private Weekdays[] closedDayOfWeekInfo;
    private  int seatNumber;
    private Time openingMorningTimeInfo;
    private Time closeMorningTimeInfo;
    private Time openingAfternoonTimeInfo;
    private Time closeAfternoonTimeInfo;
    private Integer numberOfMorningSlotsInfo;
    private Integer numberOfAfternoonSlotsInfo;


    public SaloonBean(){

    } //CONSTRUCTOR-NO ARGS MUST BE EMPTY!

    public SaloonBean(boolean type, String string){

        if(type) //if type is true, then the string inserted is "by name"
            this.name = string;
        else    // by city
            this.city = string;

    }

    public SaloonBean(String name, String telephone, String address, String city){

        this.name = name;
        this.phone = telephone;
        this.address = address;
        this.city = city;
        this.extractClosedDayOfWeekInfo();

    }

    public SaloonBean(String saloonName) {

        this.name = saloonName;

    }

    public SaloonBean(String name,String address,String city, String phone, int seatNumber, int slotTimeMinutes){

        this.address = address;
        this.name = name;
        this.phone = phone;
        this.seatNumber = seatNumber;
        this.city = city;
        this.slotTime = LocalTime.of(0,slotTimeMinutes);
        this.extractClosedDayOfWeekInfo();

    }

    private void extractClosedDayOfWeekInfo(){

        this.closedDayOfWeekInfo = new Weekdays[2];

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public String getPhone() {

        return phone;

    }

    public void setPhone(String phone) {

        this.phone = phone;

    }

    public String getAddress() {

        return address;

    }

    public void setAddress(String address) {

        this.address = address;

    }

    public int getSeatNumber() {

        return seatNumber;

    }

    public void setSeatNumber(int seatNumber) {

        this.seatNumber = seatNumber;

    }

    public LocalTime getSlotTime() {
        return slotTime;

    }

    public void setSlotTime(LocalTime slotTime) {

        this.slotTime = slotTime;

    }

    public String getCity() {

        return city;

    }

    public void setCity(String city) {

        this.city = city;

    }

    public void setNumberOfMorningSlotsInfo(Integer morningSlots) {

        this.numberOfMorningSlotsInfo = morningSlots;

    }

    public Integer getNumberOfMorningSlotsInfo() {

        return this.numberOfMorningSlotsInfo;

    }

    public void setNumberOfAfternoonSlotsInfo(Integer afternoonSlots) {

        this.numberOfAfternoonSlotsInfo = afternoonSlots;

    }

    public Integer getNumberOfAfternoonSlotsInfo() {

        return this.numberOfAfternoonSlotsInfo;

    }

    public Time getOpeningMorningTimeInfo() {

        return openingMorningTimeInfo;

    }

    public void setOpeningMorningTimeInfo(Time openingMorningTimeInfo) {

        this.openingMorningTimeInfo = openingMorningTimeInfo;

    }

    public Time getCloseMorningTimeInfo() {

        return closeMorningTimeInfo;

    }

    public void setCloseMorningTimeInfo(Time closeMorningTimeInfo) {

        this.closeMorningTimeInfo = closeMorningTimeInfo;

    }

    public Time getOpeningAfternoonTimeInfo() {

        return openingAfternoonTimeInfo;

    }

    public void setOpeningAfternoonTimeInfo(Time openingAfternoonTimeInfo) {

        this.openingAfternoonTimeInfo = openingAfternoonTimeInfo;

    }

    public Time getCloseAfternoonTimeInfo() {

        return closeAfternoonTimeInfo;
    }

    public void setCloseAfternoonTimeInfo(Time closeAfternoonTimeInfo) {

        this.closeAfternoonTimeInfo = closeAfternoonTimeInfo;

    }


    public Weekdays[] getClosedDayOfWeekInfo() {

        return closedDayOfWeekInfo;

    }

    public void setClosedDayOfWeekInfo(Weekdays closedDayOfWeekInfo) {

        this.closedDayOfWeekInfo[0] = closedDayOfWeekInfo;
        this.closedDayOfWeekInfo[1] = Weekdays.SUNDAY;

    }

}