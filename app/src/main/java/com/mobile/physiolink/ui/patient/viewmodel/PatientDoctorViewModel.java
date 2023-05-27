package com.mobile.physiolink.ui.patient.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.DoctorDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PatientDoctorViewModel extends ViewModel {
    private MutableLiveData<Doctor> doctor;
    private MutableLiveData<Service[]> services;

    public void loadDoctor(long doctorId){
        DoctorDAO.getInstance().get(doctorId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();

                try
                {
                    if (res.contains(Error.RESOURCE_NOT_FOUND))
                        return;

                    JSONObject jsonDoctor = new JSONObject(res).getJSONObject("doctor");
                    Doctor doctorObj = new Doctor(doctorId, jsonDoctor.getString("username"),
                            "doctor", jsonDoctor.getString("name"),
                            jsonDoctor.getString("surname"),
                            jsonDoctor.getString("email"),
                            jsonDoctor.getString("phone_number"),
                            jsonDoctor.getString("afm"),
                            jsonDoctor.getString("city"),
                            jsonDoctor.getString("address"),
                            jsonDoctor.getString("postal_code"),
                            jsonDoctor.getString("physio_name"));
                    doctor.postValue(doctorObj);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void loadServices(long doctorId){
        RequestFacade.getRequest(API.GET_SERVICES_OF + doctorId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                if (body.contains(Error.RESOURCE_NOT_FOUND))
                    return;
                try
                {
                    JSONObject res = new JSONObject(body);
                    JSONArray jsonServices = res.getJSONArray("services");
                    Service[] servicesFinal = new Service[jsonServices.length()];

                    for(int i=0;i<jsonServices.length();i++){
                        JSONObject element = jsonServices.getJSONObject(i);

                        servicesFinal[i]=
                                new Service("",
                                element.getString("title"),
                                element.getString("description"),
                                element.getDouble("price"));

                    }
                    services.postValue(servicesFinal);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public MutableLiveData<Doctor> getDoctor() {
        if (doctor == null)
            doctor = new MutableLiveData<>();
        return doctor;
    }

    public MutableLiveData<Service[]> getServices()
    {
        if(services == null)
            services = new MutableLiveData<>();
        return services;
    }
}
