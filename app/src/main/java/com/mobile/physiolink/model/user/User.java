package com.mobile.physiolink.model.user;

import static com.mobile.physiolink.model.validator.LoginInputValidator.NOT_VALID;

import java.io.Serializable;

public class User implements Serializable
{
    private String username;
    private final String type;


    /* Default Constructor*/
    public User(String username, String type)
    {
        this.username = username;
        this.type = type;
    }

    /* Constructor for Optional */
    public User(String username)
    {
        this.username = username;
        this.type = "";
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
