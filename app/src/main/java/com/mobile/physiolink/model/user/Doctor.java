package com.mobile.physiolink.model.user;

import com.mobile.physiolink.service.api.error.Error;

public class Doctor extends User
{
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String afm;
    private String address;
    private String physioName;

    public Doctor(long id,
                  String username,
                  String type,
                  String name,
                  String surname,
                  String email,
                  String phoneNumber,
                  String afm,
                  String address,
                  String physioName)
    {
        super(id, username, type);

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.afm = afm;
        this.address = address;
        this.physioName = physioName;
    }

    public Doctor(long id,
                  String username,
                  String type)
    {
        super(id, username, type);
    }

    public Doctor(String state){ super(state); }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getAfm()
    {
        return afm;
    }

    public String getPhysioName()
    {
        return physioName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setPhysioName(String physioName)
    {
        this.physioName = physioName;
    }

    @Override
    public String toString()
    {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", afm='" + afm + '\'' +
                '}';
    }
}
