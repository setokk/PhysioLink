package com.mobile.physiolink.service.schemas.dao;

import java.io.IOException;

/**
 * Generic interface for DAO classes (Data Access Object). <br>
 * Used for accessing objects from the database.
 * @param <S> The data type of the id column in the DB.
 * @param <T> The schema of the object we want to access/modify/create.
 * @param <K> The object we want to access/modify/create.
 */
public interface InterfaceDAO<S, T, K>
{
     void create(T item) throws IOException;
     void update(S id, T item);
     void delete(S id);
     K get(S id);

}
