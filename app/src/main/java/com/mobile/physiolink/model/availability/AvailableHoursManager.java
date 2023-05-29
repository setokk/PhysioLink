package com.mobile.physiolink.model.availability;

import com.mobile.physiolink.util.date.DateFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            String monthPrefix = "";
            String dayPrefix = "";
            if (day <= 9)
                dayPrefix = "0";
            if (month <= 9)
                monthPrefix = "0";

            calendar.set(Calendar.DAY_OF_MONTH, day);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                dateToHoursMap.put(year + "-" + monthPrefix + month + "-" + dayPrefix + day, availableHoursWeekend);
            else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                dateToHoursMap.put(year + "-" + monthPrefix + month + "-" + dayPrefix + day, new String[0]);
            else
                dateToHoursMap.put(year + "-" + monthPrefix + month + "-" + dayPrefix + day, availableHours);
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

    public String[] getAvailableHoursOfDate(int year, int month, int day)
    {
        String date = DateFormatter.fixDatePrefixes(year, month, day);
        return Optional.ofNullable(dateToHoursMap.get(date))
                        .orElse(new String[0]);
    }

    public void setAvailableHoursOfDate(String date, String[] takenHours)
    {
        List<String> previousHours = new ArrayList<>(Arrays.asList(dateToHoursMap.get(date)));
        previousHours.removeAll(Arrays.asList(takenHours)); // Difference

        dateToHoursMap.put(date, previousHours.toArray(new String[0]));
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
