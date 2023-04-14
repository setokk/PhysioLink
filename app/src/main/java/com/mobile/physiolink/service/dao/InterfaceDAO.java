package com.mobile.physiolink.service.dao;

import com.google.android.gms.tasks.Task;

public interface InterfaceDAO<T>
{
     Task<Void> create(T item);
     Task<Void> update(T item);
     Task<Void> delete(T item);
     T get(T item);
}
