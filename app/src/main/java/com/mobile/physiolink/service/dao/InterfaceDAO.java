package com.mobile.physiolink.service.dao;

import java.io.IOException;

import okhttp3.Callback;

/**
 * Generic interface for DAO classes (Data Access Object).<br>
 * Used for accessing objects from the database. <br><br>
 * The objects are accessed through the callback method that the each ViewModel class creates
 * and passes through to these methods. <br><br>
 * Each class that implements {@link InterfaceDAO} is expected to pass the callback object
 * to a {@link com.mobile.physiolink.service.api.RequestFacade} class method respectively.
 * <br>
 * @param <I> The data type of the id column in the DB.
 * @param <S> The schema of the object we want to access/modify/create.
 */
public interface InterfaceDAO<I, S>
{
     void create(S item, Callback callback) throws IOException;
     void update(I id, S item, Callback callback);
     void delete(I id, Callback callback);
     void get(I id, Callback callback);

}
