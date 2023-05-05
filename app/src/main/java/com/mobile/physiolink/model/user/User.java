package com.mobile.physiolink.model.user;

import static com.mobile.physiolink.service.validator.UserAuth.NOT_VALID;

import java.io.Serializable;

public class User implements Serializable
{
    private long id;
    private String username;
    private final String type;


    /* Default Constructor */
    public User(long id,
                String username,
                String type)
    {
        this.id = id;
        this.username = username;
        this.type = type;
    }

    /* Constructor for Optional */
    public User(String username)
    {
        this.username = username;
        this.type = "";
        this.id = -1;
    }

    public long getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public boolean isValid()
    {
        return !username.equals(NOT_VALID);
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
