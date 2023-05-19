package com.mobile.physiolink.model.user;

import com.mobile.physiolink.service.api.error.Error;

public class Doctor extends User
{
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String afm;
    private String city;
    private String address;
    private String postalCode;
    private String physioName;

    public Doctor(long id,
                  String username,
                  String type,
                  String name,
                  String surname,
                  String email,
                  String phoneNumber,
                  String afm,
                  String city,
                  String address,
                  String postalCode,
                  String physioName)
    {
        super(id, username, type);

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.afm = afm;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
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

    public String getCity()
    {
        return city;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setPhysioName(String physioName)
    {
        this.physioName = physioName;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setAfm(String afm)
    {
        this.afm = afm;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
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
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", physioName='" + physioName + '\'' +
                '}';
    }
}
