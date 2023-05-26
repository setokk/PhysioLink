package com.mobile.physiolink.ui.patient.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.user.Doctor;

public class PatientHomeViewModel
{
    private MutableLiveData<Doctor> doctor;
    private MutableLiveData<Appointment> upcomingAppointment;
}
