package com.mobile.physiolink.service.schemas;

public class UserSchema
{
    public final String username;
    public final String password;
    public final String name;
    public final String surname;
    public final String email;
    public final String city;
    public final String address;
    public final String postalCode;
    public final String phoneNumber;

    public UserSchema(String username,
                      String password,
                      String name,
                      String surname,
                      String email,
                      String phoneNumber,
                      String city,
                      String address,
                      String postalCode)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
    }
}
