package com.mobile.physiolink.model.user;

import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.api.error.ResourceNotFindable;

public class User implements ResourceNotFindable
{
    private long id;
    private String username;
    private final String type;

    public User() {this.username = Error.RESOURCE_NOT_FOUND; this.type = "";}

    /* Default Constructor */
    public User(long id,
                String username,
                String type)
    {
        this.id = id;
        this.username = username;
        this.type = type;
    }

    /* Used when authenticating users (see UserAuth.java) */
    public static User invalidUser()
    {
        return new User(-1, Error.INVALID_CREDENTIALS, "");
    }

    public boolean isValid()
    {
        return !username.equals(Error.INVALID_CREDENTIALS);
    }

    @Override
    public boolean isFound()
    {
        return !username.equals(Error.RESOURCE_NOT_FOUND);
    }

    public long getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public boolean isPSF()
    {
        return type.equalsIgnoreCase("PSF");
    }

    public boolean isDoctor()
    {
        return type.equalsIgnoreCase("Doctor");
    }

    public boolean isPatient()
    {
        return type.equalsIgnoreCase("Patient");
    }
}
