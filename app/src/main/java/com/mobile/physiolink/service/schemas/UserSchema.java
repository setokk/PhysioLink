package com.mobile.physiolink.service.schemas;

public class UserSchema
{
    public final String username;
    public final String password;
    public final String name;
    public final String surname;
    public final String email;
    public final String address;
    public final String phoneNumber;

    public UserSchema(String username,
                      String password,
                      String name,
                      String surname,
                      String email,
                      String phoneNumber,
                      String address)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
