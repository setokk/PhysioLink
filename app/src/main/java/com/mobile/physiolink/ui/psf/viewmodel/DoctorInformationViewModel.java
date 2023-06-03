package com.mobile.physiolink.ui.psf.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.DoctorDAO;
import com.mobile.physiolink.service.dao.ServiceDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorInformationViewModel extends ViewModel
{
    private MutableLiveData<Doctor> doctor;
    private MutableLiveData<List<Service>> doctorServices;

    public void loadDoctor(long doctorId)
    {
        DoctorDAO.getInstance().get(doctorId, new Callback() {
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
                    JSONObject jsonDoctor = new JSONObject(res).getJSONObject("doctor");
                    Doctor newDoctor = new Doctor(doctorId, jsonDoctor.getString("username"),
                            "doctor", jsonDoctor.getString("name"),
                            jsonDoctor.getString("surname"),
                            jsonDoctor.getString("email"),
                            jsonDoctor.getString("phone_number"),
                            jsonDoctor.getString("afm"),
                            jsonDoctor.getString("city"),
                            jsonDoctor.getString("address"),
                            jsonDoctor.getString("postal_code"),
                            jsonDoctor.getString("physio_name"));

                    String imageURL = jsonDoctor.getString("image");
                    newDoctor.setImageURL(imageURL);

                    doctor.postValue(newDoctor);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

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
                    List<Service> services = new ArrayList<>(jsonServices.length());

                    for (int i = 0; i < jsonServices.length(); ++i)
                    {
                        JSONObject element = jsonServices.getJSONObject(i);
                        services.add(new Service(element.getString("id"),
                                element.getString("title"),
                                element.getString("description"),
                                element.getDouble("price")));
                    }
                    doctorServices.postValue(services);
                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public MutableLiveData<List<Service>> getDoctorServices()
    {
        if (doctorServices == null)
            doctorServices = new MutableLiveData<>();

        return doctorServices;
    }

    public MutableLiveData<Doctor> getDoctor()
    {
        if (doctor == null)
            doctor = new MutableLiveData<>();

        return doctor;
    }
}
