package com.mobile.physiolink.model.user;

public class Doctor extends User
{
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String afm;

    public Doctor(long id,
                  String username,
                  String type,
                  String name,
                  String surname,
                  String email,
                  String phoneNumber,
                  String afm)
    {
        super(id, username, type);

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.afm = afm;
    }

    public Doctor(long id,
                  String username,
                  String type)
    {
        super(id, username, type);
    }

    public Doctor(String username)
    {
        super(username);
    }

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
}
