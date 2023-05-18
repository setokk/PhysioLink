package com.mobile.physiolink.util;

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
}
