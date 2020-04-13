package com.example.covid19hotspottracker.utils;

import com.example.covid19hotspottracker.network.pojo.AffectedAreaDataResponsePojo;
import com.example.covid19hotspottracker.network.pojo.AffectedAreaPojo;
import com.example.covid19hotspottracker.network.pojo.CityDataResponsePojo;
import com.example.covid19hotspottracker.network.pojo.CityResponsePojo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class Utils {


    public static ArrayList<String> getCityArrayList(JSONObject cityJsonObject){

        if(cityJsonObject!=null){

            Gson gson=new Gson();
            CityResponsePojo cityResponsePojo=gson.fromJson(cityJsonObject.toString(), CityResponsePojo.class);

            ArrayList<CityDataResponsePojo> cityDataResponsePojoArrayList=cityResponsePojo.getCityDataResponsePojoArrayList();
            ArrayList<String> cityList=new ArrayList<>();
            if(cityResponsePojo!=null&&cityDataResponsePojoArrayList!=null){
                for (int i=0;i<cityDataResponsePojoArrayList.size();i++){

                    cityList.add(cityDataResponsePojoArrayList.get(i).getCityName());

                }
                return cityList;
            }
            else
                return new ArrayList<>();
        }
        else
            return new ArrayList<>();

    }
    public static ArrayList<AffectedAreaDataResponsePojo> getAffectedAreaList(JSONObject affectedAreasJsonObject){

        Gson gson=new Gson();
        AffectedAreaPojo affectedAreaPojo=gson.fromJson(affectedAreasJsonObject.toString(), AffectedAreaPojo.class);
        ArrayList<AffectedAreaDataResponsePojo> affectedAreaDataResponsePojoArrayList= affectedAreaPojo.getAffectedAreaDataResponsePojoArrayList();
        return affectedAreaDataResponsePojoArrayList;
    }

}
