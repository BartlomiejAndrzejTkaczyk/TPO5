/**
 *
 *  @author Tkaczyk Bartłomiej S22517
 *
 */

package zad1;

import standardString.TimeTextDisplay;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Time {
    public static String passed(String fromStr, String toStr) {
        try {
            if (fromStr.contains("T"))
                return passedDateTime(fromStr, toStr);
            return passedDate(fromStr, toStr);
        } catch (DateTimeException e){
            return "*** " + e;
        }
    }

    private static String passedDateTime(String fromStr, String toStr){
        StringBuilder resBuilder = new StringBuilder("");

        ZonedDateTime from = ZonedDateTime.of( LocalDateTime.parse(fromStr), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime to = ZonedDateTime.of( LocalDateTime.parse(toStr), ZoneId.of("Europe/Warsaw"));

        // introduce info
        resBuilder.append(TimeTextDisplay.introduceInfoFormat(from, to));
        resBuilder.append("\n");

        // passed date
        long days = ChronoUnit.DAYS.between(from.toLocalDate(), to.toLocalDate());
        double months = days/7.0;

        resBuilder.append(TimeTextDisplay.passedDateFormat(days, months));
        resBuilder.append("\n");


        // passed time

        long hours = ChronoUnit.HOURS.between(from, to);
        long min = ChronoUnit.MINUTES.between(from, to);
        resBuilder.append(TimeTextDisplay.passedCalendarTimeFormat((int) hours, (int) min));
        resBuilder.append("\n");
        // passed calendar date
        Period period = Period.between(from.toLocalDate(), to.toLocalDate());
        int periodYears = period.getYears();
        int periodMonths = period.getMonths();
        int periodDays = period.getDays();

        resBuilder.append(TimeTextDisplay.passedCalendarDateFormat(periodYears, periodMonths, periodDays));

        return resBuilder.toString();
    }

    private static String passedDate(String fromStr, String toStr){
        StringBuilder resBuilder = new StringBuilder("");

        LocalDate from = LocalDate.parse(fromStr);
        LocalDate to = LocalDate.parse(toStr);

        // introduce info
        resBuilder.append(TimeTextDisplay.introduceInfoFormat(from, to));
        resBuilder.append("\n");

        // passed time
        long days = ChronoUnit.DAYS.between(from, to);
        double months = days/7.0;

        resBuilder.append(TimeTextDisplay.passedDateFormat(days, months));
        resBuilder.append("\n");

        // passed calendar time
        Period period = Period.between(from, to);
        int periodYears = period.getYears();
        int periodMonths = period.getMonths();
        int periodDays = period.getDays();

        resBuilder.append(TimeTextDisplay.passedCalendarDateFormat(periodYears, periodMonths, periodDays));

        return resBuilder.toString();
    }

    private static String timeBetween(LocalDate from, LocalDate to){
        return "";
    }
}
