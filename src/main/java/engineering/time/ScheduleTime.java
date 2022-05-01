package engineering.time;


import model.Saloon;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTime {
    private Time timeOpenAfternoon;
    private  int seatNumber;
    private  int slotTimeAfternoon;
    private  int slotTimeMorning;
    private  LocalTime intervalSlotTime;
    private List<TimeSlot> timeSlotList;
    private Time timeOpen;
    TimeSlot timeSlot;

    public ScheduleTime(Saloon saloonTimeSlots){
        timeSlotList = new ArrayList<>();
        timeOpen = saloonTimeSlots.getOpeningMorningTime();
        timeOpenAfternoon = saloonTimeSlots.getCloseAfternoonTime();
        seatNumber = saloonTimeSlots.getSeatNumber();


        intervalSlotTime = saloonTimeSlots.getSlotTime();
        slotTimeMorning = saloonTimeSlots.getNumberOfMorningSlots();
        slotTimeAfternoon = saloonTimeSlots.getNumberOfAfternoonSlots();

    }

        public List<TimeSlot> createSlotTime() {

            for (int i = 0; i < slotTimeMorning + slotTimeAfternoon; i++) {
                timeSlot = new TimeSlot();
                if(i == slotTimeMorning){
                    timeOpen = new Time(timeOpenAfternoon.getTime());
                }
                long in = timeOpen.getTime();
                timeOpen = new Time(in);
                timeSlot.setFromTime(timeOpen);

                int minute = intervalSlotTime.getMinute();
                int millisec = minute * 60000;

                timeOpen.setTime(timeOpen.getTime());
                long i2 = timeOpen.getTime() + millisec;
                timeOpen = new Time(i2);
                timeSlot.setToTime(timeOpen);
                timeSlot.setSeatAvailable(seatNumber);

                timeSlotList.add(timeSlot);

            }
            return timeSlotList;

        }

}
