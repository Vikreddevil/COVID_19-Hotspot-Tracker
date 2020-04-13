package com.example.covid19hotspottracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.covid19hotspottracker.network.NetworkUtil;
import com.example.covid19hotspottracker.network.pojo.CityResponsePojo;
import com.example.covid19hotspottracker.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GettingStartedActivity extends AppCompatActivity implements NetworkUtil.MyCallback{

    private ArrayAdapter arrayAdapter;
    private Spinner citySpinner;
    private TextView next_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        next_text_view=findViewById(R.id.next_text_view);

        next_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent=new Intent(GettingStartedActivity.this,HomeScreenActivity.class);
                startActivity(intent);
            }
        });


        citySpinner=findViewById(R.id.city_spinner);
        RequestQueue queue = Volley.newRequestQueue(this);

        //Network call to get cities
        NetworkUtil networkUtil=new NetworkUtil(this);
        queue.add(networkUtil.getCities(this));

    }

    @Override
    public void onCityListGenerated(JSONObject jsonObject) {

        System.out.println("Data returned is "+jsonObject.toString());



        List<String> list=new ArrayList<>();
        list.addAll(Utils.getCityArrayList(jsonObject));
        arrayAdapter=new ArrayAdapter(this, R.layout.spinner_item_white_text, list);
        citySpinner.setAdapter(arrayAdapter);
    }
}
