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
                         String address,
                         String amka, long doctorId)
    {
        super(username, password, name, surname, email, phoneNumber, address);
        this.amka = amka;
        this.doctorId = doctorId;
    }
}
