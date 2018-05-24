package utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DateTimeHelper {

    // magic number=
    // millisec * sec * min * hours
    // 1000 * 60 * 60 * 24 = 86400000
    public static final long MAGIC=86400000L;

    public static Instant format(String date, String format) {


        DateTimeFormatter FMT = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
                .toFormatter()
                .withZone(ZoneOffset.UTC);
        return FMT.parse(date, Instant::from);
    }

    public static String format(Instant date, String format) {

          DateTimeFormatter FMT = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
                .toFormatter()
                .withZone(ZoneOffset.UTC);
        return FMT.format(date);
    }

    public static Instant format(String date) {
        DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC);
        return FMT.parse(date, Instant::from);
    }

    public static String format(Instant date) {
        return format(date,"yyyy-MM-dd" );
    }




    public static int InstantToDays (Instant instant){
        //  convert an instant to an integer and back again
        long currentTime=instant.toEpochMilli();
        currentTime=currentTime/MAGIC;
        return (int) currentTime;
    }

    public static Instant DaysToInstant(int days) {
        //  convert integer back again to an instant
        long currentTime=(long) days*MAGIC;
        return Instant.ofEpochMilli(currentTime);
    }
}
