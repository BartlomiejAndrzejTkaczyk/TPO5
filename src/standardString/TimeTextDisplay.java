package standardString;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeTextDisplay {

    public static String introduceInfoFormat(ZonedDateTime from, ZonedDateTime to){
        String pattern = "d MMMM yyyy (EEEE)";
        String minuteFrom = from.getMinute()<9?"0"+from.getMinute():String.valueOf(from.getMinute());
        String minuteTo = to.getMinute()<9?"0"+to.getMinute():String.valueOf(to.getMinute());
        return String.format(
                "Od %s godz. %d:%s do %s godz. %d:%s",
                from.format(DateTimeFormatter.ofPattern(pattern)),
                from.getHour(),
                minuteFrom,
                to.format(DateTimeFormatter.ofPattern(pattern)),
                to.getHour(),
                minuteTo
        );
    }

    public static  String introduceInfoFormat(LocalDate from, LocalDate to){
        String pattern = "d MMMM yyyy (EEEE)";
        return String.format(
                "Od %s do %s",
                from.format(DateTimeFormatter.ofPattern(pattern)),
                to.format(DateTimeFormatter.ofPattern(pattern))
        );
    }

    public static String passedDateFormat(long days, double weeks){
        String daysPostfix = "dni";
        String weeksPrefix = "tygodni";

        if (days == 1) daysPostfix="dzień";

        DecimalFormat df = new DecimalFormat("#####.##");
        String formatted = df.format(weeks);
        formatted = formatted.replace(",",".");

        return String.format(
                " - mija: %d %s, %s %s",
                days, daysPostfix, weeksPrefix, formatted
        ) ;
        //" - mija: " + days +" "+ daysPostfix + ", " + weeksPrefix + " " + weeks;
    }

    public static String passedCalendarDateFormat(long years, long months, long days){
        String postfixYears = "lat";
        String postfixMonths = "miesięcy";
        String postfixDays = "dni";

        if (years == 1) postfixYears = "rok";
        else if (years>=2 && years<=4) postfixYears = "lata";

        if (months == 1) postfixMonths = "miesiąc";
        else if (months>=2 && months<=4) postfixMonths = "miesiące";

        if (days==1) postfixDays="dzień";

        StringBuilder res = new StringBuilder();
        res.append(" - kalendarzowo:");
        if (years != 0){
            res.append(
                    String.format(
                            ", %d %s",
                            years, postfixYears
                    )
            );
        }
        if (months != 0){
            res.append(
                    String.format(
                            ", %d %s",
                            months, postfixMonths
                    )
            );
        }
        if (days != 0){
            res.append(
                    String.format(
                            ", %d %s",
                            days, postfixDays
                    )
            );
        }
        res.deleteCharAt(res.indexOf(","));
//                String.format(
//                        " - kalendarzowo: %d %s, %d %s, %d %s",
//                        years, postfixYears, months, postfixMonths, days, postfixDays);
        return res.toString();
    }

    public static String passedCalendarTimeFormat(int hours, int minutes){
        return String.format(
                " - godzin: %d, minut: %d",
                hours, minutes
        );
    }
}
