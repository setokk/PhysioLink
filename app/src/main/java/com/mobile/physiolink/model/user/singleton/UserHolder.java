package com.mobile.physiolink.model.user.singleton;

import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.model.user.PSF;
import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.model.user.User;

public class UserHolder
{
    private static User user;

    private UserHolder() {}

    public static void setInstance(User user)
    {
        UserHolder.user = user;
    }

    public static PSF psf() { return (PSF) user; }
    public static Doctor doctor() { return (Doctor) user; }
    public static Patient patient() { return (Patient) user; }
}
