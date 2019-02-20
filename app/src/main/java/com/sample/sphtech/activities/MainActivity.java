package com.sample.sphtech.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.sample.sphtech.R;
import com.sample.sphtech.adapters.MobileDataAdapter;
import com.sample.sphtech.models.MobileDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.substring;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://data.gov.sg/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MobileDataModel> mobileDataLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mobileDataLists = new ArrayList<>();

        loadUrlData();

    }

    private void loadUrlData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject1 = new JSONObject(response);
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("result");

                    JSONArray array = jsonObject2.getJSONArray("records");

                    //--quarterly view
                    /*for (int i = 0; i < array.length(); i++){

                        JSONObject jo = array.getJSONObject(i);

                        //-- set to be greater that 2007 --> 2008-onwards
                        if(Integer.parseInt(substring(jo.getString("quarter"),0,4)) > 2004) {

                            MobileDataModel mobileData = new MobileDataModel(jo.getString("volume_of_mobile_data"), jo.getString("quarter"),
                                        jo.getString("_id"));
                            mobileDataLists.add(mobileData);
                        }
                    }*/



                    //--yearly view
                    double overallMobileData = 0;
                    int year = 2008;
                    String id = "0";

                    for (int i = 0; i < array.length(); i++){

                        JSONObject jo = array.getJSONObject(i);

                        if(Integer.parseInt(substring(jo.getString("quarter"),0,4)) >= year){

                            if(Integer.parseInt(substring(jo.getString("quarter"),0,4)) != year) {

                                year = Integer.parseInt(substring(jo.getString("quarter"),0,4));
                                overallMobileData = Double.valueOf(jo.getString("volume_of_mobile_data"));

                            }else{

                                year = Integer.parseInt(substring(jo.getString("quarter"),0,4));
                                overallMobileData += Double.valueOf(jo.getString("volume_of_mobile_data"));

                                if(Float.valueOf(jo.getString("volume_of_mobile_data")) < Float.valueOf(array.getJSONObject(i-1).getString("volume_of_mobile_data"))){
                                    id = array.getJSONObject(i-1).getString("quarter") + "  " + array.getJSONObject(i-1).getString("volume_of_mobile_data") +
                                            "/" + jo.getString("quarter") + "  " + jo.getString("volume_of_mobile_data");
                                }else{
                                    id = "0";
                                }
                            }

                            if(substring(jo.getString("quarter"),5,7).equalsIgnoreCase("Q4") &&
                                    Integer.parseInt(substring(jo.getString("quarter"),0,4)) == year){

                            //if(Integer.parseInt(substring(array.getJSONObject(i+1).getString("quarter"),0,4)) >
                            //        year &&
                            //        substring(array.getJSONObject(i+1).getString("quarter"),5,7).equalsIgnoreCase("Q1")){

                                String data = String.format("%f",overallMobileData);

                                MobileDataModel mobileData = new MobileDataModel(data, jo.getString("quarter"), id);
                                mobileDataLists.add(mobileData);
                            }
                        }
                    }

                    adapter = new MobileDataAdapter(mobileDataLists, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}