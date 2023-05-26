package com.mobile.physiolink.model.service;

import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.api.error.ResourceNotFindable;

public class Service implements ResourceNotFindable
{
    private String id;
    private String title;
    private String description;
    private double price;

    public Service(String id, String title, String description, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Service(String state)
    {
        this.id = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean isFound()
    {
        return !id.equals(Error.RESOURCE_NOT_FOUND);
    }
}
