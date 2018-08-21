package utils;

import models.common.Period;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {

    // magic number=
    // millisec * sec * min * hours
    // 1000 * 60 * 60 * 24 = 86400000
    public static final long MAGIC=86400000L;

    public static Instant toInstant(String date, String format) {


        DateTimeFormatter FMT = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
                .toFormatter()
                .withZone(ZoneOffset.UTC);
        return FMT.parse(date, Instant::from);
    }

    public static String toString(Instant date, String format) {

          DateTimeFormatter FMT = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
                .toFormatter()
                .withZone(ZoneOffset.UTC);
        return FMT.format(date);
    }

    public static Instant toInstant(String date) {
        return toInstant(date, "yyyy-MM-dd");
    }

    public static String toString(Instant date) {
        return toString(date,"yyyy-MM-dd" );
    }

    public static String toString(int date) {
        return toString(toInstant(date));
    }

    public static int toDays(String string){
        return (int) toDays(toInstant(string));
    }


    public static int toDays(Instant instant){
        //  convert an instant to an integer and back again
        long currentTime=instant.toEpochMilli();
        currentTime=currentTime/MAGIC;
        return (int) currentTime;
    }

    public static Instant toInstant(int days) {
        //  convert integer back again to an instant
        long currentTime=(long) days*MAGIC;
        return Instant.ofEpochMilli(currentTime);
    }

    public static Date toDate(Instant instant)
    {
        return Date.from(instant);
    }

    public static Calendar toCalendar(Instant instant)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate(instant));
        return cal;
    }

    public static Calendar toCalendar(int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate(toInstant(days)));
        return cal;
    }

    public static Integer toHourBetweenDateWithoutHolydays(Period period)
    {
        return toHourBetweenDateWithoutHolydays(toInstant(period.getStart()), toInstant(period.getEnd()));
    }

    public static Integer toHourBetweenDateWithoutHolydays(Instant start, Instant end)
    {
        Calendar endDate = toCalendar(end);
        endDate.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        Calendar startDate = toCalendar(start);
        startDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (getWorkingDaysBetweenTwoDates(startDate, endDate)) * 7;
    }

    public static int getWorkingDaysBetweenTwoDates(Calendar startCal, Calendar endCal) {

        int workDays = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }

        do {
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

        return workDays+1;
    }

}
