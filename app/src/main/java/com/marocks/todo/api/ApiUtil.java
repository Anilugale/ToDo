package com.marocks.todo.api;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.marocks.todo.AppController;
import com.marocks.todo.Utile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 Created by anil on 30/8/16.
 */
public class ApiUtil {

    public static final String baseUrl = "http://happytodo.int2root.com/";
    public static final String signUp = baseUrl+"v1/signup";
    public static final String signIn = baseUrl+"v1/signin";
    public static final String todos = baseUrl+"v1/todos";


    public static final String TAG = ApiUtil.class.getSimpleName();
    public static Gson gson= new Gson();

    public static void jsonRequest(final HandleResponce handleResponce,String url,JSONObject jsonObject,int method){
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        if(jsonObject!=null) {
            System.out.println(jsonObject.toString());
        }




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,
                url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        handleResponce.onResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                handleResponce.onFailure(error);
            }


        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                                SharedPreferences sh = handleResponce.getContext().getSharedPreferences(handleResponce.getContext().getPackageName(),MODE_PRIVATE);
                String auth =sh.getString(Utile.autTokenJson,null);
                headers.put("Content-Type", "application/json");
                if(auth!=null && !auth.isEmpty()) {
                    headers.put("Authorization", auth);
                    Log.e(TAG, "getHeaders: "+auth);
                }
                return headers;

            }
        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    public static void jsonArrayRequest(final HandleResponce handleResponce,String url,JSONObject jsonObject,int method){
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        if(jsonObject!=null) {
            System.out.println(jsonObject.toString());
        }




        JsonArrayRequest jsonObjReq = new JsonArrayRequest(method,
                url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        handleResponce.onResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                handleResponce.onFailure(error);
            }


        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                SharedPreferences sh = handleResponce.getContext().getSharedPreferences(handleResponce.getContext().getPackageName(),MODE_PRIVATE);
                String auth =sh.getString(Utile.autTokenJson,null);


                headers.put("Content-Type", "application/json");
                if(auth!=null && !auth.isEmpty()) {
                    headers.put("Authorization", auth);
                    Log.e(TAG, "getHeaders: "+auth);
                }
                return headers;

            }
        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
