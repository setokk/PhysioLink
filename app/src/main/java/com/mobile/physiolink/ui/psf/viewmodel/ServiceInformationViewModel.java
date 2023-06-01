package com.mobile.physiolink.ui.psf.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.ServiceDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ServiceInformationViewModel extends ViewModel
{
    private MutableLiveData<Service> service;

    public void loadService(String serviceId)
    {
        ServiceDAO.getInstance().get(serviceId, new Callback() {
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
                    JSONObject jsonService = new JSONObject(res).getJSONObject("service");
                    Service newService = new Service(serviceId,
                            jsonService.getString("title"),
                            jsonService.getString("description"),
                            jsonService.getDouble("price"));
                    service.postValue(newService);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public MutableLiveData<Service> getService()
    {
        if (service == null)
            service = new MutableLiveData<>();

        return service;
    }
}
