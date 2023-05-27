package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.service.dao.ServiceDAO;

public class DoctorServicesViewModel extends ViewModel
{
    private MutableLiveData<Service[]> doctorServices;

    public void loadDoctorServices(long doctorId)
    {
        // TODO: SERVICES OF DOC

    }

    public MutableLiveData<Service[]> getDoctorServices()
    {
        if (doctorServices == null)
            doctorServices = new MutableLiveData<>();

        return doctorServices;
    }
}
