package com.mobile.physiolink.service.schemas;

public class DoctorSchema extends UserSchema
{
    public final String afm;

    public DoctorSchema(String username,
                        String password,
                        String name,
                        String surname,
                        String email,
                        String phoneNumber, String afm)
    {
        super(username, password, name, surname, email, phoneNumber);
        this.afm = afm;
    }
}
