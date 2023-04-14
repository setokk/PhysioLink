package com.mobile.physiolink.model.user;

public class PSF extends User
{
    public PSF(long id,
               String username,
               String type)
    {
        super(id, username, type);
    }

    public PSF(String username)
    {
        super(username);
    }
}
