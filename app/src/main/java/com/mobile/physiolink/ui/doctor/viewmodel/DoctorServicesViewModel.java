package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.ServiceDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorServicesViewModel extends ViewModel
{
    private MutableLiveData<Service[]> doctorServices;

    public void loadDoctorServices(long doctorId)
    {
        ServiceDAO.getInstance().getDoctorServices(doctorId, new Callback()
        {
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
                    doctorServices.postValue(services);
                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public MutableLiveData<Service[]> getDoctorServices()
    {
        if (doctorServices == null)
            doctorServices = new MutableLiveData<>();

        return doctorServices;
    }
}
