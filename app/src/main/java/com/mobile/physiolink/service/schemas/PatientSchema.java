package com.mobile.physiolink.service.schemas;

public class PatientSchema extends UserSchema
{
    public final String amka;
    public final long doctorId;

    public PatientSchema(String username,
                         String password,
                         String name,
                         String surname,
                         String email,
                         String phoneNumber,
                         String city,
                         String address,
                         String postalCode,
                         String amka, long doctorId)
    {
        super(username, password, name, surname, email, phoneNumber, city, address, postalCode);

        this.amka = amka;
        this.doctorId = doctorId;
    }
}
