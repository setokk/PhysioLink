package com.mobile.physiolink.service.dao;

import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import okhttp3.Callback;

public class ServiceDAO implements InterfaceDAO<String, Service>
{
    private static ServiceDAO serviceDAO = null;
    private ServiceDAO() {}

    public static ServiceDAO getInstance(){
        if(serviceDAO == null)
            serviceDAO = new ServiceDAO();
        return serviceDAO;
    }
    @Override
    public void create(Service item, Callback callback) throws IOException
    {
        HashMap<String, String> keyValues = new HashMap<>(4);
        keyValues.put("id", item.getId());
        keyValues.put("title", item.getTitle());
        keyValues.put("description", item.getDescription());
        keyValues.put("price", String.valueOf(item.getPrice()));

        RequestFacade.postRequest(API.CREATE_SERVICE, keyValues, callback);
    }

    @Override
    public void update(String id, Service item, Callback callback)
    {
        HashMap<String, String> keyValues = new HashMap<>(3);
        keyValues.put("title", item.getTitle());
        keyValues.put("description", item.getDescription());
        keyValues.put("price", String.valueOf(item.getPrice()));

        RequestFacade.postRequest(API.EDIT_SERVICE, keyValues, callback);
    }

    @Override
    public void delete(String id, Callback callback) {

    }

    @Override
    public void get(String id, Callback callback)
    {
        try {
            String encodedID = URLEncoder.encode(id,
                    StandardCharsets.UTF_8.toString());
            RequestFacade.getRequest(API.GET_SERVICE + encodedID, callback);
        }
        catch (Exception ignored) {}
    }

    public void linkServiceToDoctor(String serviceId, long doctorId, Callback callback)
    {
        HashMap<String, String> keyValues = new HashMap<>(2);
        keyValues.put("service_id", serviceId);
        keyValues.put("doctor_id", String.valueOf(doctorId));

        RequestFacade.postRequest(API.LINK_SERVICE_TO_DOCTOR, keyValues, callback);
    }

    public void deleteServiceFromDoctor(String serviceId, long doctorId, Callback callback)
    {
        HashMap<String, String> keyValues = new HashMap<>(2);
        keyValues.put("service_id", serviceId);
        keyValues.put("doctor_id", String.valueOf(doctorId));

        RequestFacade.postRequest(API.DELETE_DOCTOR_SERVICE, keyValues, callback);
    }

    public void getServices(Callback callback)
    {
        RequestFacade.getRequest(API.GET_SERVICES, callback);
    }

    public void getDoctorServices(long doctorId, Callback callback)
    {
        RequestFacade.getRequest(API.GET_SERVICES_OF + doctorId, callback);
    }
}
