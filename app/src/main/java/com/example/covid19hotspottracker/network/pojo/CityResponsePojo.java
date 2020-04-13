package com.example.covid19hotspottracker.network.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityResponsePojo {
    //{"data":[{"cityId":2,"version":"1","timestamp":"123123123","cityName":"Mumbai"},{"version":"1","timestamp":"213123","cityName":"Pune","cityId":1}]}

    @SerializedName("data")
    private ArrayList<CityDataResponsePojo> cityDataResponsePojoArrayList;


    public ArrayList<CityDataResponsePojo> getCityDataResponsePojoArrayList() {
        return cityDataResponsePojoArrayList;
    }

    public void setCityDataResponsePojoArrayList(ArrayList<CityDataResponsePojo> cityDataResponsePojoArrayList) {
        this.cityDataResponsePojoArrayList = cityDataResponsePojoArrayList;
    }
}
