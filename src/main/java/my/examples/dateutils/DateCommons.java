package my.examples.dateutils;

import java.util.Calendar;
import java.util.Date;

public class DateCommons {
    public static Date addDays(Date previousDate, int numberOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(previousDate);
        calendar.add(calendar.DATE, numberOfDays);
        return calendar.getTime();
    }

    public static Date getDate(int day, int month, int year) {
        return getDate(day, month, year, 0, 0, 0);
    }

    public static Date getDate(int day, int month, int year, int hour, int minute, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, seconds);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }
}