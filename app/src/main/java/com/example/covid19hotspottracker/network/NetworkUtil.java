package com.example.covid19hotspottracker.network;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.covid19hotspottracker.utils.Constants;

import org.json.JSONObject;

import java.util.HashMap;

public class NetworkUtil {

    static MyCallback callbackMain;
    static AffectedAreaInterface affectedAreaInterfaceMain;
    private static ProgressDialog dialog;

   public interface MyCallback {
        void onCityListGenerated(JSONObject jsonObject);

    }
    public interface AffectedAreaInterface {
        void onAffectedAreaFetched(JSONObject jsonObject);

    }

    public NetworkUtil(AffectedAreaInterface affectedAreaInterface){

       affectedAreaInterfaceMain=affectedAreaInterface;


    }
    public NetworkUtil(MyCallback callback){
callbackMain=callback;
    }

    public JsonObjectRequest getCities(Context context){
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading cities");
        dialog.setCancelable(false);
        dialog.show();


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,Constants.BASE_URL + Constants.CITIES_URL, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response!=null){
                    dialog.dismiss();
                    callbackMain.onCityListGenerated(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                if(error!=null)
                System.out.println("Error is "+error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjectRequest;



    }

    public  JsonObjectRequest getAffectedAreas(Context context){
        dialog = new ProgressDialog(context);
        dialog.setMessage("Fetching affected areas");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String,Object> hashMap=new HashMap<>();
//"cityId":2,
//	"timeStamp":"2123213"
        hashMap.put("cityId",2);
        hashMap.put("timeStamp",0);


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,Constants.BASE_URL + Constants.LOCATION_DATA_URL, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response!=null){
                    dialog.dismiss();
                    System.out.println("affected areas are"+response.toString());
                    affectedAreaInterfaceMain.onAffectedAreaFetched(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                if(error!=null)
                    System.out.println("Error is "+error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjectRequest;



    }

}