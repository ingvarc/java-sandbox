package io.github.ingvarc.jdk8;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * Class to show the usage of the Java 8 date and time API.
 */
public class DateTimeAPI {

    public static void main(String... args) {
        localDateTime();
        localDateTimeInformation();
        additionAndSubtraction();
        periods();
        durations();
        year();
        parsingAndFormatting();
        temporalAdjuster();
        conversion();
        timestamps();
    }

    private static void localDateTime() {
        // dates, e.g. 2018-03-25
        LocalDate.now();    // the current date from the system clock in the default time-zone
        LocalDate.of(2018, Month.MARCH, 25);   // 25th March, 2018
        LocalDate.of(2018, 4, 1);       // 1st April, 2018 (months finally begin with 1)
        LocalDate.ofYearDay(2018, 256);         // the 256th day of 2018 (2018-09-13)

        // times, e.g. 09:47:19.195589500
        LocalTime.now();    // the current time from the system clock in the default time-zone.
        LocalTime.of(0, 0); // 00:00
        LocalTime.of(1, 5, 10); // 01:05:10
        LocalTime.ofSecondOfDay(1234);  // 1234th second of day or 00:20:34

        // date and time, e.g. 2018-05-04T09:54:17.532960100
        LocalDateTime.now();    // the current date-time from the system clock in the default time-zone
        LocalDateTime.of(2018, 5, 4, 9, 54);         // 2018-05-04 09:54
        LocalDateTime.of(2018, Month.DECEMBER, 24, 12, 0);  // 2018-12-24 12:00

        // By default LocalDate and LocalTime use the system clock in the default time zone.
        // To change this we can provide a timezone or an alternative clock implementation.
        LocalTime.now(ZoneId.of("America/New_York"));   // the current time from the system clock in New York time-zone
        LocalTime.now(ZoneId.of("Asia/Tokyo"));         // the current time from the system clock in Tokyo time-zone
        LocalTime.now(Clock.systemUTC());               // the current time from the system clock in UTC time-zone
    }

    private static void localDateTimeInformation() {
        LocalDate date = LocalDate.of(2018, 2, 28);
        LocalDate.of(2018, 2, 27).isBefore(date); // true

        // information about the month
        Month march = date.getMonth();  // FEBRUARY
        march.getValue();   // 2
        march.minLength();  // 28
        march.maxLength();  // 29
        march.firstMonthOfQuarter();    // JANUARY

        // information about the year
        date.getYear();         // 2018
        date.getDayOfYear();    // 59
        date.lengthOfYear();    // 365
        date.isLeapYear();      // false

        // information about the day
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        dayOfWeek.getValue();   // 3
        dayOfWeek.name();       // WEDNESDAY

        // time information
        LocalTime time = LocalTime.of(8, 45);   // 08:45
        time.getHour();         // 8
        time.getSecond();       // 0
        time.getMinute();       // 45
        time.toSecondOfDay(); // 31500
    }

    private static void year() {
        Year currentYear = Year.now();
        Year twoThousand = Year.of(2000);

        currentYear.isAfter(twoThousand);       // true
        currentYear.isBefore(Year.of(3000));    // true
        currentYear.isLeap();   // false for 2018
        currentYear.length();   // 365 for 2018

        LocalDate date = Year.of(2018).atDay(75);   // the seventy fifth day of 2018 (2018-03-16)
    }

    private static void additionAndSubtraction() {
        LocalDate.now().minusDays(1);   // yesterday
        LocalDate.now().plusDays(1);    //tomorrow

        LocalDate.now().plusWeeks(1);
        LocalDate.now().plusMonths(1);
        LocalDate.now().plusYears(1);

        LocalDateTime.now().minusHours(12).minusMinutes(15);    // 12 hours and 15 minutes ago
    }

    private static void periods() {
        LocalDate firstDate = LocalDate.of(2012, 12, 21);   // 2012-12-21
        LocalDate secondDate = LocalDate.of(2018, 5, 23);   // 2018-05-23

        Period period = Period.between(firstDate, secondDate);  // years = 5, months = 2, days = 2
        period.getDays();       // 2
        period.getMonths();     // 2
        period.getYears();      // 5
        period.isNegative();    // false

        Period threeYearsSixMonthsSevenDays = Period.ofYears(3).plusMonths(6).plusDays(7);

        // add three years, six months and seven days to 2012-12-21
        firstDate.plus(threeYearsSixMonthsSevenDays);   // 2016-06-28
    }

    private static void durations() {
        Instant firstInstant = Instant.ofEpochSecond(1527057000);   // 2018-05-23 06:30
        Instant secondInstant = Instant.ofEpochSecond(1527057300);  // 2018-05-23 06:35

        Duration duration = Duration.between(firstInstant, secondInstant);
        duration.getSeconds();  // 300

        Duration negativeDuration = Duration.between(secondInstant, firstInstant);
        negativeDuration.getSeconds();      // -300
        negativeDuration.abs().toMinutes(); // 5

        Duration.ofHours(8).getSeconds();   // 28800 seconds
    }

    private static void parsingAndFormatting() {
        LocalDateTime dateTime = LocalDateTime.of(2018, Month.MAY, 25, 13, 30); // 2018-05-25T13:30

        dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);  // basic ISO date format (20180525)
        dateTime.format(DateTimeFormatter.ISO_WEEK_DATE);   // ISO week date (2018-W21-5)
        dateTime.format(DateTimeFormatter.ISO_DATE_TIME);   // ISO date time (2018-05-25T13:30:00)
        dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")); // using a custom pattern (25.05.2018)
        dateTime.format(DateTimeFormatter.ofPattern("d. MMMM yyyy", new Locale("de"))); // german date formatting (25. Mai 2018)
        dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(new Locale("de")));    // using short german date and time formatting (25.05.18 13:30)

        // parsing date strings
        LocalDate.parse("2018-05-25");  // 2018-05-25
        LocalDate.parse("2018-W21-5", DateTimeFormatter.ISO_WEEK_DATE); // 2018-05-25
        LocalDate.parse("25.05.2015", DateTimeFormatter.ofPattern("dd.MM.yyyy")); // 2018-05-25
    }

    private static void temporalAdjuster() {
        LocalDate localDate = LocalDate.of(2018, Month.MAY, 25); // 2018-05-25

        localDate.with(TemporalAdjusters.firstDayOfMonth());    // the first day of may 2018 (2018-05-01)
        localDate.with(TemporalAdjusters.lastDayOfMonth());     // the last day of may 2018 (2018-05-31)
        localDate.with(TemporalAdjusters.lastDayOfYear());      // the last day of 2018 (2018-12-31)
        localDate.with(TemporalAdjusters.firstDayOfNextMonth());    // the first day of june 2018 (2018-06-01)
        localDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)); // next saturday (2018-05-26)
    }

    private static void conversion() {
        LocalDateTime.of(LocalDate.now(), LocalTime.now()); // LocalDate and LocalTime -> LocalDateTime
        LocalDateTime.now().toLocalDate();  // LocalDateTime -> LocalDate
        LocalDateTime.now().toLocalTime();  // LocalDateTime -> LocalTime

        LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Tokyo"));    // Instant -> LocalDateTime
        LocalDateTime.now().toInstant(ZoneOffset.ofHours(-6));  // LocalDateTime -> Instant

        // convert from/to old date/calendar/timezone classes
        new Date().toInstant();
        Calendar.getInstance().toInstant();
        TimeZone.getDefault().toZoneId();
        new GregorianCalendar().toZonedDateTime();
        Date.from(Instant.now());
        TimeZone.getTimeZone(ZoneId.of("Asia/Tokyo"));
        GregorianCalendar.from(ZonedDateTime.now());
    }

    private static void timestamps() {
        Instant.ofEpochSecond(1527336000);      // from unix timestamp, 2018-05-26T09:00:00Z
        Instant.ofEpochMilli(1527325200000L);   // same time in millis

        Instant.parse("2018-05-26T10:30:00Z"); // parsing from ISO 8601

        Instant.now().toString();       // current time as ISO 8601 format
        Instant.now().getEpochSecond(); // current time as unix timestamp
        Instant.now().toEpochMilli();   // current time in millis

        Instant.now().plusSeconds(30);  // add 10 seconds to the current time
        Instant.now().plus(5, ChronoUnit.DAYS); // add 5 days to the current time
        Instant.now().minus(Duration.ofDays(2));    // subtract 5 days from the current time
    }
}