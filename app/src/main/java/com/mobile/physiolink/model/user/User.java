package com.mobile.physiolink.model.user;

import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.api.error.ResourceNotFindable;

import java.io.Serializable;

public class User implements ResourceNotFindable, Serializable
{
    private long id;
    private String username;
    private final String type;
    private String imageURL;

    /* Used for returning invalid or empty objects */
    public User(String state) {this.username = state; this.type = "";}

    /* Default Constructor */
    public User(long id,
                String username,
                String type)
    {
        this.id = id;
        this.username = username;
        this.type = type;
        this.imageURL = Error.RESOURCE_NOT_FOUND; // Default value
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

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public boolean hasImage() { return !imageURL.equals("Resource not found"); }

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
