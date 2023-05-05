package com.mobile.physiolink.service.schemas;

public class DoctorSchema extends UserSchema
{
    public final String afm;
    public final String physioName;

    public DoctorSchema(String username,
                        String password,
                        String name,
                        String surname,
                        String email,
                        String phoneNumber,
                        String afm,
                        String address,
                        String physioName)
    {
        super(username, password, name, surname, email, phoneNumber, address);

        this.afm = afm;
        this.physioName = physioName;
    }
}
