package com.mobile.physiolink.model.user;

public class Patient extends User
{
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String city;
    private String address;
    private String postalCode;
    private long doctorId;
    private String amka;

    public Patient(long id,
                  String username,
                  String type,
                  String name,
                  String surname,
                  String email,
                  String phoneNumber,
                  String amka,
                  String city,
                  String address,
                  String postalCode, long doctorId)
    {
        super(id, username, type);

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.amka = amka;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
        this.doctorId = doctorId;
    }

    public Patient(long id,
                   String username,
                   String type)
    {
        super(id, username, type);
    }

    public Patient(String state) { super(state); }

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

    public String getAmka()
    {
        return amka;
    }

    public String getCity()
    {
        return city;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public long getDoctorId()
    {
        return doctorId;
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

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public void setDoctorId(long doctorId)
    {
        this.doctorId = doctorId;
    }

    public void setAmka(String amka)
    {
        this.amka = amka;
    }

    @Override
    public String toString()
    {
        return "Patient{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", doctorId=" + doctorId +
                ", amka='" + amka + '\'' +
                '}';
    }
}
