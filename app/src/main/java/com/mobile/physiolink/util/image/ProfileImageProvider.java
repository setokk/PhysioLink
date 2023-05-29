package com.mobile.physiolink.util.image;

import com.mobile.physiolink.R;

import java.util.Arrays;
import java.util.List;

public final class ProfileImageProvider
{
    private static final List<String> nameExceptions = Arrays.asList("Άρτεμις", "Αρτεμις");

    public static int getProfileImage(String name)
    {
        if (name.charAt(name.length() - 1) == 'ς' && !nameInExceptions(name))
            return R.drawable.prof_doctor;
        else
            return R.drawable.prof_doctoress;
    }

    private static boolean nameInExceptions(String name)
    {
        return nameExceptions.stream()
                    .anyMatch(name::equals);
    }
}
