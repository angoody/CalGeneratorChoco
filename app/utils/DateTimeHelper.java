package utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

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
}
