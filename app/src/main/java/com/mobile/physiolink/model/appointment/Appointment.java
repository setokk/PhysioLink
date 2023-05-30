package com.mobile.physiolink.model.appointment;

import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.api.error.ResourceNotFindable;

public class Appointment implements ResourceNotFindable
{
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

    // Extra Service fields
    private String serviceTitle;
    private String serviceDescription;
    private double servicePrice;
    private boolean isConfirmed;

    public Appointment()
    {
        date = "";
        hour = "";
        message = "";
    }

    public Appointment(String state)
    {
        this.date = state;
    }

    @Override
    public boolean isFound() {
        return !date.equals(Error.RESOURCE_NOT_FOUND);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocSurname() {
        return docSurname;
    }

    public void setDocSurname(String docSurname) {
        this.docSurname = docSurname;
    }

    public String getDocAddress() {
        return docAddress;
    }

    public void setDocAddress(String docAddress) {
        this.docAddress = docAddress;
    }

    public String getDocCity() {
        return docCity;
    }

    public void setDocCity(String docCity) {
        this.docCity = docCity;
    }

    public String getDocPostalCode() {
        return docPostalCode;
    }

    public void setDocPostalCode(String docPostalCode) {
        this.docPostalCode = docPostalCode;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getPatSurname() {
        return patSurname;
    }

    public void setPatSurname(String patSurname) {
        this.patSurname = patSurname;
    }

    public String getPatAmka() {
        return patAmka;
    }

    public void setPatAmka(String patAmka) {
        this.patAmka = patAmka;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
