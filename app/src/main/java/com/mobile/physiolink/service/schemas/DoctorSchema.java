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
                        String city,
                        String address,
                        String postalCode,
                        String physioName)
    {
        super(username, password, name, surname, email, phoneNumber, city, address, postalCode);

        this.afm = afm;
        this.physioName = physioName;
    }
}
