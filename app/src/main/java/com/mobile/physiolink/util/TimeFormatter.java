package com.mobile.physiolink.util;

public class TimeFormatter
{
    public static String formatToPM_AM(String hour)
    {
        String pmAm;
        double dHour = Double.parseDouble(hour);
        if (dHour < 12)
            pmAm = "πμ";
        else
            pmAm = "μμ";

        return hour + ":00 " + pmAm;
    }
}
