package com.mobile.physiolink.ui.psf.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.service.api.error.Error;
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

public class ServicesViewModel extends ViewModel
{
    private MutableLiveData<List<Service>> services;

    public void loadServices()
    {
        ServiceDAO.getInstance().getServices(new Callback() {
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
                    List<Service> newServices = new ArrayList<>(jsonServices.length());

                    for (int i = 0; i < jsonServices.length(); ++i)
                    {
                        JSONObject element = jsonServices.getJSONObject(i);
                        newServices.add(new Service(element.getString("id"),
                                element.getString("title"),
                                element.getString("description"),
                                element.getDouble("price")));
                    }
                    services.postValue(newServices);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public MutableLiveData<List<Service>> getServices()
    {
        if (services == null)
            services = new MutableLiveData<>();

        return services;
    }
}
