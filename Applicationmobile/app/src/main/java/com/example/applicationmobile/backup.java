package com.example.applicationmobile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class backup implements Serializable {
    protected Date _date;
    protected String _deviceName;
    protected List<trip> _Trips;
    protected List<ex> _extrips;

    public backup(Date date, String deviceName, List<trip> Trips ,List<ex> extrips) {
        _date = date;
        _deviceName = deviceName;
        _Trips = Trips;
        _extrips = extrips;
    }

    public Date getDate() {
        return _date;
    }

    public void setDate(Date date) {
        this._date = date;
    }

    public String getDeviceName() {
        return _deviceName;
    }

    public void setDeviceName(String deviceName) {
        this._deviceName = deviceName;
    }

    public List<trip> getTrips() {
        return _Trips;
    }

    public void setTrips(List<trip> trips) {
        _Trips = trips;
    }

    public List<ex> get_extrips() {
        return _extrips;
    }

    public void set_extrips(List<ex> _extrips) {
        this._extrips = _extrips;
    }
}
