package com.mobile.physiolink.util.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public final class DateFormatter
{
    private static DateTimeFormatter alphanumeric = DateTimeFormatter.ofPattern("MMM dd, yyyy")
            .withLocale(new Locale("el", "GR"));

    public static String formatToAlphanumeric(int year, int month, int day)
    {
        return alphanumeric.format(LocalDate.of(year, month, day));
    }

    public static String fixDatePrefixes(int year, int month, int day)
    {
        String monthPrefix = "";
        String dayPrefix = "";
        if (day <= 9)
            dayPrefix = "0";
        if (month <= 9)
            monthPrefix = "0";

        return year + "-" + monthPrefix + month + "-" + dayPrefix + day;
    }
}
