package com.mobile.physiolink.model.user.availability;

import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AvailableHoursManager
{
    private HashMap<String, String[]> dateToHoursMap;

    private static final String[] availableHours = new String[] {"9:00-10:00",
        "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00",
        "15:00-16:00", "16:00-17:00"};

    private static final String[] availableHoursWeekend = new String[] {"9:00-10:00",
            "10:00-11:00", "11:00-12:00", "12:00-13:00"};

    public AvailableHoursManager(int month, int year)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dateToHoursMap = new HashMap<>(maxDays);
        for (int day = 1; day <= maxDays; ++day)
        {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                dateToHoursMap.put(year + "-" + month + "-" + day, availableHoursWeekend);
            else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                dateToHoursMap.put(year + "-" + month + "-" + day, new String[0]);
            else
                dateToHoursMap.put(year + "-" + month + "-" + day, availableHours);
        }
    }

    public void setDateToHoursMap(HashMap<String, String[]> dateToHoursMap)
    {
        this.dateToHoursMap = dateToHoursMap;
    }

    public HashMap<String, String[]> getDateToHoursMap()
    {
        return dateToHoursMap;
    }

    public String[] getAvailableHoursOfDate(String date)
    {
        return dateToHoursMap.get(date);
    }

    public void setAvailableHoursOfDate(String date, String[] hours)
    {
        dateToHoursMap.put(date, hours);
    }

    @Override
    public String toString() {
        String map = "";
        for (Map.Entry<String, String[]> entry : dateToHoursMap.entrySet())
        {
            map += entry.getKey() + ", {" + Arrays.toString(entry.getValue()) + "}\n\n";
        }
        return map;
    }
}
