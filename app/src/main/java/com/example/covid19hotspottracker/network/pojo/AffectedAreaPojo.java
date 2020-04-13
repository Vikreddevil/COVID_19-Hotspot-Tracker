package com.example.covid19hotspottracker.network.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AffectedAreaPojo {

    @SerializedName("data")
    private ArrayList<AffectedAreaDataResponsePojo> affectedAreaDataResponsePojoArrayList;

    public ArrayList<AffectedAreaDataResponsePojo> getAffectedAreaDataResponsePojoArrayList() {
        return affectedAreaDataResponsePojoArrayList;
    }

    public void setAffectedAreaDataResponsePojoArrayList(ArrayList<AffectedAreaDataResponsePojo> affectedAreaDataResponsePojoArrayList) {
        this.affectedAreaDataResponsePojoArrayList = affectedAreaDataResponsePojoArrayList;
    }
//    {"data":[{"lat":"19.138929","cityId":2,"long":"72.846336","areaName":"Momin Nagar, Jogeshwari"},{"long":"75.828667","areaName":"Adarsh Nagar, Jogeshwari","lat":"26.900471","cityId":2},{"long":"72.8390731","areaName":"Jogeshwari Market, Jogeshwari","lat":"19.1415949","cityId":2},{"areaName":"Amboli Police Station, Jogeshwari","lat":"19.1415949","cityId":2,"long":"72.8390731"},{"lat":"19.1361565","cityId":2,"long":"72.8436883","areaName":"Millat Hospital, Jogeshwari"},{"long":"72.8386253","areaName":"Shastri Nagar, Jogeshwari","lat":"19.1398985","cityId":2}]}
}
