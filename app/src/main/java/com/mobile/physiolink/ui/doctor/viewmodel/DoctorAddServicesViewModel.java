package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorAddServicesViewModel extends ViewModel {

    private MutableLiveData<Service[]> newDoctorServices;

    public void loadNewDoctorServices(long doctorId){
        RequestFacade.getRequest(API.GET_EXCLUDED_DOCTOR_SERVICES + doctorId , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                if (res.contains(Error.RESOURCE_NOT_FOUND))
                    return;

                try
                {
                    JSONArray jsonServices = new JSONObject(res).getJSONArray("services");
                    Service[] services = new Service[jsonServices.length()];

                    for (int i = 0; i < jsonServices.length(); ++i)
                    {
                        JSONObject element = jsonServices.getJSONObject(i);
                        services[i] = new Service(element.getString("id"),
                                element.getString("title"),
                                element.getString("description"),
                                element.getDouble("price"));
                    }
                    newDoctorServices.postValue(services);
                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public MutableLiveData<Service[]> getNewDoctorServices()
    {
        if (newDoctorServices == null)
            newDoctorServices = new MutableLiveData<>();

        return newDoctorServices;
    }
}
