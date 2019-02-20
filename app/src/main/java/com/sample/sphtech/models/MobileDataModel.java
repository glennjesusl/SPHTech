package com.sample.sphtech.models;

public class MobileDataModel {

    private String volume_of_mobile_data;
    private String quarter;
    private String _id;

    public String getVolumeData() {
        return volume_of_mobile_data;
    }

    public String getQuarter() {
        return quarter;
    }

    public String getId() {
        return _id;
    }

    public MobileDataModel(String volume_of_mobile_data, String quarter, String _id) {
        this.volume_of_mobile_data = volume_of_mobile_data;
        this.quarter = quarter;
        this._id = _id;
    }
}