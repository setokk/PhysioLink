package com.mobile.physiolink.ui.patient.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.availability.AvailableHoursManager;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RequestAppointmentViewmodel extends ViewModel
{
    private MutableLiveData<AvailableHoursManager> dateToAvailableHours;

    public LiveData<AvailableHoursManager> getAvailableHours()
    {
        if (dateToAvailableHours == null)
            dateToAvailableHours = new MutableLiveData<>();

        return dateToAvailableHours;
    }

    public void loadAvailableHours(int month, int year, long doctorId)
    {
        HashMap<String, String> keyValues = new HashMap<>(2);
        keyValues.put("month", String.valueOf(month));
        keyValues.put("doctor_id", String.valueOf(doctorId));
        RequestFacade.postRequest(API.GET_UNAVAILABLE_HOURS, keyValues, new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String body = response.body().string();
                if (body.contains(Error.RESOURCE_NOT_FOUND))
                    return;

                try {
                    JSONObject res = new JSONObject(body);
                    JSONArray dates = res.getJSONArray("dates");
                    AvailableHoursManager hoursManager = new AvailableHoursManager(month, year);

                    for (int i = 0; i < dates.length(); ++i)
                    {
                        JSONObject element = dates.getJSONObject(i);

                        JSONArray startHours = element.getJSONArray("start_hours");
                        JSONArray endHours = element.getJSONArray("end_hours");
                        String[] takenHours = new String[startHours.length()];
                        for (int j = 0; j < takenHours.length; ++j)
                            takenHours[j] = startHours.getString(j)
                                            + "-" +
                                            endHours.getString(j);

                        hoursManager.setAvailableHoursOfDate(element.getString("date"),
                                takenHours);
                    }

                    dateToAvailableHours.postValue(hoursManager);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
