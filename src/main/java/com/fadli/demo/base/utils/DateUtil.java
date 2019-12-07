package com.fadli.demo.base.utils;

import com.fadli.demo.base.exceptions.BusinessException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {


    public static int getYear(Date datexPDate) {
        Calendar clndrCal;

        clndrCal = new GregorianCalendar();
        clndrCal.setLenient(false);
        clndrCal.setTime(datexPDate);

        return clndrCal.get(Calendar.YEAR);

    }

    public static int getMonth(Date datexPDate) {
        Calendar clndrCal;
        int intCalendarMonth;

        clndrCal = new GregorianCalendar();
        clndrCal.setLenient(false);
        clndrCal.setTime(datexPDate);
        intCalendarMonth = clndrCal.get(Calendar.MONTH);

        return calendarMonthToInt(intCalendarMonth);

    }

    public static String getYearToString(Date datexPDate) {
        Calendar clndrCal;
        String strngYear;

        clndrCal = new GregorianCalendar();
        clndrCal.setLenient(false);
        clndrCal.setTime(datexPDate);
        strngYear = Integer.toString(clndrCal.get(Calendar.YEAR));

        return strngYear;
    }

    public static String getMonthToString(Date datexPDate) {

        int intMonth = getMonth(datexPDate);

        String strngMonth;

        if (intMonth < 10) {
            strngMonth = "0" + intMonth;

        } else {
            strngMonth = "" + intMonth;
        }

        return strngMonth;

    }

    public static String getYearMonthString(Date datexPDate) {
        String yearString = getYearToString(datexPDate);
        String monthString = getMonthToString(datexPDate);

        return yearString.concat(monthString);
    }

    public static String format(Date datexPDate, String strngPPattern) {

        SimpleDateFormat sdfmtFormatter;

        sdfmtFormatter = new SimpleDateFormat(strngPPattern);

        return sdfmtFormatter.format(datexPDate);

    }

    private static int calendarMonthToInt(int intPCalendarMonth) {

        if (intPCalendarMonth == Calendar.JANUARY)
            return 1;
        else if (intPCalendarMonth == Calendar.FEBRUARY)
            return 2;
        else if (intPCalendarMonth == Calendar.MARCH)
            return 3;
        else if (intPCalendarMonth == Calendar.APRIL)
            return 4;
        else if (intPCalendarMonth == Calendar.MAY)
            return 5;
        else if (intPCalendarMonth == Calendar.JUNE)
            return 6;
        else if (intPCalendarMonth == Calendar.JULY)
            return 7;
        else if (intPCalendarMonth == Calendar.AUGUST)
            return 8;
        else if (intPCalendarMonth == Calendar.SEPTEMBER)
            return 9;
        else if (intPCalendarMonth == Calendar.OCTOBER)
            return 10;
        else if (intPCalendarMonth == Calendar.NOVEMBER)
            return 11;
        else if (intPCalendarMonth == Calendar.DECEMBER)
            return 12;
        else
            return 1;
    }

    public static LocalDate fromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date setTimeToZero(Date date) {
        if (date == null) return null;

        LocalDate localdate = fromDate(date);
        return Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertIso8601ToDate(String iso8601) {
        if (iso8601 == null) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSXXX").withResolverStyle(ResolverStyle.STRICT);
        ZonedDateTime dateTime = null;
        try {
            iso8601 = iso8601.replace(" ", "+");
            dateTime = ZonedDateTime.parse(iso8601, formatter);
        } catch (DateTimeParseException e) {
            throw new BusinessException("invalid.format.datetime",
                    "Supported format: yyyy-MM-ddTHH:mm:ss.SSSXXX (example: 2017-05-30T23:45:56.987+07:00)");
        }
        return setTimeToZero(Date.from(dateTime.toInstant()));
    }

    public static String convertDateToIso8601(Date date) {
        if (date == null) return null;
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .withZone(ZoneId.systemDefault())
                .format(date.toInstant());
    }

    public static Date getSystemDate() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
