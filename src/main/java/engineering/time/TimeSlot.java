package engineering.time;

import java.sql.Time;


public class TimeSlot {
    private int seatAvailable;
    private Time fromTime;
    private Time toTime;

    public TimeSlot(){}

    public TimeSlot(Time toTime, Time fromTime) {
        this.toTime = toTime;
        this.fromTime = fromTime;
    }


    public int getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(int seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    public Time getFromTime() {
        return fromTime;
    }

    public void setFromTime(Time fromTime) {
        this.fromTime = fromTime;
    }

    public Time getToTime() {
        return toTime;
    }

    public void setToTime(Time toTime) {
        this.toTime = toTime;
    }

}
