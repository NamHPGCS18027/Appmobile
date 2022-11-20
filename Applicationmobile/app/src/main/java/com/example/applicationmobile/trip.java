package com.example.applicationmobile;

import java.io.Serializable;
import java.util.List;

public class trip  implements Serializable  {
    protected   String _id;
    protected  String trip_name ;
    protected String trip_destination;
    protected String trip_date;
    protected String trip_risk;
    protected String trip_Description;

    public trip(String _id, String trip_name, String trip_destination, String trip_date, String trip_risk, String trip_Description) {
        this._id = _id;
        this.trip_name = trip_name;
        this.trip_destination = trip_destination;
        this.trip_date = trip_date;
        this.trip_risk = trip_risk;
        this.trip_Description = trip_Description;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_destination() {
        return trip_destination;
    }

    public void setTrip_destination(String trip_destination) {
        this.trip_destination = trip_destination;
    }

    public String getTrip_date() {
        return trip_date;
    }

    public void setTrip_date(String trip_date) {
        this.trip_date = trip_date;
    }

    public String getTrip_risk() {
        return trip_risk;
    }

    public void setTrip_risk(String trip_risk) {
        this.trip_risk = trip_risk;
    }

    public String getTrip_Description() {
        return trip_Description;
    }

    public void setTrip_Description(String trip_Description) {
        this.trip_Description = trip_Description;
    }
}
