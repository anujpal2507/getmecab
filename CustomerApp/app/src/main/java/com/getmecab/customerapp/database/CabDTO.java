package com.getmecab.customerapp.database;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 8/10/16.
 */
public class CabDTO {
    String distance;
    String route_id;
    String source;
    String time;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
