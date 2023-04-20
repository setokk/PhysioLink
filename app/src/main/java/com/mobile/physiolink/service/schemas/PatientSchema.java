package com.mobile.physiolink.service.schemas;

public class PatientSchema extends UserSchema
{
    public final String amka;
    public final long doctor_id;

    public PatientSchema(String username,
                         String password,
                         String name,
                         String surname,
                         String email,
                         String phoneNumber,
                         String address,
                         String amka, long doctor_id)
    {
        super(username, password, name, surname, email, phoneNumber, address);
        this.amka = amka;
        this.doctor_id = doctor_id;
    }
}
