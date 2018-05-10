package com.example.amila.autocare.Views.offers;

/**
 * Created by pavilion 15 on 07-May-18.
 */

public class Offer {
    String store_lat, store_lng, title, description, store_name;

    public Offer(String store_lat, String store_lng, String title, String description, String store_name) {
        this.store_lat = store_lat;
        this.store_lng = store_lng;
        this.title = title;
        this.description = description;
        this.store_name = store_name;
    }

    public Offer() {

    }

    public String getStore_lat() {
        return store_lat;
    }

    public void setStore_lat(String store_lat) {
        this.store_lat = store_lat;
    }

    public String getStore_lng() {
        return store_lng;
    }

    public void setStore_lng(String store_lng) {
        this.store_lng = store_lng;
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

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
}
