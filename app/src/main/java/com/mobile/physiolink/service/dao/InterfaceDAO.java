package com.mobile.physiolink.service.dao;

public interface InterfaceDAO<T>
{
     void create(T item);
     void update(T item);
     void delete(long id);
     T get(long id);
}
