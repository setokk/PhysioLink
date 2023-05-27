package com.mobile.physiolink.service.dao;

import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;

import java.io.IOException;
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
        RequestFacade.getRequest(API.GET_SERVICE + id, callback);
    }

    public void getDoctorServices(long doctorId, Callback callback)
    {
        RequestFacade.getRequest(API.GET_SERVICES_OF + doctorId, callback);
    }
}
