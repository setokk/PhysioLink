package com.mobile.physiolink.model.appointment;

public class AppointmentBuilder
{
    private Appointment appointment;

    public AppointmentBuilder()
    {
        appointment = new Appointment();
    }

    public Appointment build() {
        return appointment;
    }

    public AppointmentBuilder setId(long id) {
        appointment.setId(id);
        return this;
    }

    public AppointmentBuilder setDoctorId(long doctorId) {
        appointment.setDoctorId(doctorId);
        return this;
    }

    public AppointmentBuilder setPatientId(long patientId) {
        appointment.setPatientId(patientId);
        return this;
    }

    public AppointmentBuilder setDate(String date) {
        appointment.setDate(date);
        return this;
    }

    public AppointmentBuilder setHour(String hour) {
        appointment.setHour(hour);
        return this;
    }

    public AppointmentBuilder setMessage(String message) {
        appointment.setMessage(message);
        return this;
    }

    public AppointmentBuilder setDocName(String docName) {
        appointment.setDocName(docName);
        return this;
    }

    public AppointmentBuilder setDocSurname(String docSurname) {
        appointment.setDocSurname(docSurname);
        return this;
    }

    public AppointmentBuilder setDocAddress(String docAddress) {
        appointment.setDocAddress(docAddress);
        return this;
    }

    public AppointmentBuilder setDocCity(String docCity) {
        appointment.setDocCity(docCity);
        return this;
    }

    public AppointmentBuilder setDocPostalCode(String docPostalCode) {
        appointment.setDocPostalCode(docPostalCode);
        return this;
    }

    public AppointmentBuilder setPatName(String patName) {
        appointment.setPatName(patName);
        return this;
    }

    public AppointmentBuilder setPatSurname(String patSurname) {
        appointment.setPatSurname(patSurname);
        return this;
    }

    public AppointmentBuilder setPatAmka(String patAmka) {
        appointment.setPatAmka(patAmka);
        return this;
    }

    public AppointmentBuilder setServiceTitle(String serviceTitle) {
        appointment.setServiceTitle(serviceTitle);
        return this;
    }

    public AppointmentBuilder setServiceDescription(String serviceDescription) {
        appointment.setServiceDescription(serviceDescription);
        return this;
    }

    public AppointmentBuilder setServicePrice(double servicePrice) {
        appointment.setServicePrice(servicePrice);
        return this;
    }

    public AppointmentBuilder setConfirmed(boolean isConfirmed)
    {
        appointment.setConfirmed(isConfirmed);
        return this;
    }

    public AppointmentBuilder setImageURL(String imageURL)
    {
        appointment.setImageURL(imageURL);
        return this;
    }
}