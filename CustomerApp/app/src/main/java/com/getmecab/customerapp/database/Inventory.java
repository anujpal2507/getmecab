package com.getmecab.customerapp.database;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 4/10/16.
 */
public class Inventory {
    String modelName;
    int routeId;
    int seats;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
