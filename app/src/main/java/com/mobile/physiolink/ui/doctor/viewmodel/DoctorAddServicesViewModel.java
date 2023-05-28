package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.service.Service;

public class DoctorAddServicesViewModel extends ViewModel {

    private MutableLiveData<Service[]> newDoctorServices;

    public void loadNewDoctorServices(long id){

    }

    public MutableLiveData<Service[]> getNewDoctorServices()
    {
        if (newDoctorServices == null)
            newDoctorServices = new MutableLiveData<>();

        return newDoctorServices;
    }
}
