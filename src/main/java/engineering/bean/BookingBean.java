package engineering.bean;


import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class BookingBean {
    private  Date date;
    private  String saloonName;
    private String closedDate;
    private Time toTime;
    private Time fromTime;
    private String nameCustomer;
    private String surnameCustomer;




    public BookingBean(){}

    public BookingBean(Date date, String saloonName){
        this.saloonName =saloonName;
        this.date = date;
    }

    public BookingBean(String saloonName, Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("EEEEEE", Locale.ENGLISH);
        String dayOfWeek =  formatter.format(date);
        this.closedDate=dayOfWeek;

        this.saloonName = saloonName;
    }

    public void setToTime(Time toTime) {
        this.toTime = toTime;
    }

    public void setClosedDate(String date) {
        this.closedDate = date;
    }

    public void setFromTime(Time fromTime) {
        this.fromTime = fromTime;
    }

    public Time getToTime() {
        return toTime;
    }

    public Time getFromTime() {
        return fromTime;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public String getSurnameCustomer() {
        return surnameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public void setSurnameCustomer(String surnameCustomer) {
        this.surnameCustomer = surnameCustomer;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date date) {
        String dayOfWeek = new SimpleDateFormat("EEEE").format(date);
        this.closedDate = dayOfWeek;
    }

    public String getSaloonName() {
        return saloonName;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }
}
