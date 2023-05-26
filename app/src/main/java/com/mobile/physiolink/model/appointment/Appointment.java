package com.mobile.physiolink.model.appointment;

public class Appointment
{
    private Appointment appointment;

    private long id;
    private long doctorId;
    private long patientId;
    private String date;
    private String hour;
    private String message;

    // Extra Doctor fields
    private String docName;
    private String docSurname;
    private String docAddress;
    private String docCity;
    private String docPostalCode;

    // Extra Patient fields
    private String patName;
    private String patSurname;
    private String patAmka;

    public Appointment()
    {
        appointment = new Appointment();
        date = "";
        hour = "";
        message = "";
    }

    public Appointment build() {
        return appointment;
    }

    public long getId() {
        return id;
    }

    public Appointment setId(long id) {
        appointment.setId(id);
        return this;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public Appointment setDoctorId(long doctorId) {
        appointment.setDoctorId(doctorId);
        return this;
    }

    public long getPatientId() {
        return patientId;
    }

    public Appointment setPatientId(long patientId) {
        appointment.setPatientId(patientId);
        return this;
    }

    public String getDate() {
        return date;
    }

    public Appointment setDate(String date) {
        appointment.setDate(date);
        return this;
    }

    public String getHour() {
        return hour;
    }

    public Appointment setHour(String hour) {
        appointment.setHour(hour);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Appointment setMessage(String message) {
        appointment.setMessage(message);
        return this;
    }

    public String getDocName() {
        return docName;
    }

    public Appointment setDocName(String docName) {
        appointment.setDocName(docName);
        return this;
    }

    public String getDocSurname() {
        return docSurname;
    }

    public Appointment setDocSurname(String docSurname) {
        appointment.setDocSurname(docSurname);
        return this;
    }

    public String getDocAddress() {
        return docAddress;
    }

    public Appointment setDocAddress(String docAddress) {
        appointment.setDocAddress(docAddress);
        return this;
    }

    public String getDocCity() {
        return docCity;
    }

    public Appointment setDocCity(String docCity) {
        appointment.setDocCity(docCity);
        return this;
    }

    public String getDocPostalCode() {
        return docPostalCode;
    }

    public Appointment setDocPostalCode(String docPostalCode) {
        appointment.setDocPostalCode(docPostalCode);
        return this;
    }

    public String getPatName() {
        return patName;
    }

    public Appointment setPatName(String patName) {
        appointment.setPatName(patName);
        return this;
    }

    public String getPatSurname() {
        return patSurname;
    }

    public Appointment setPatSurname(String patSurname) {
        appointment.setPatSurname(patSurname);
        return this;
    }

    public String getPatAmka() {
        return patAmka;
    }

    public Appointment setPatAmka(String patAmka) {
        appointment.setPatAmka(patAmka);
        return this;
    }
}
