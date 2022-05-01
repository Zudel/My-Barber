package engineering.bean;


import java.sql.Time;

public class TimeSlotBean {
    private int seatAvailable;
    private Time fromTime;
    private Time toTime;


    public Time getFromTime() {
        return fromTime;
    }

    public Time getToTime() {
        return toTime;
    }


    public void setFromTime(Time fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(Time toTime) {
        this.toTime = toTime;
    }

    public int getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(int seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

}
