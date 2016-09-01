package com.marocks.todo.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.marocks.todo.AppController;

import org.json.JSONObject;

/**
   Created by anil on 30/8/16.
 */
public class ApiUtil {

    public static final String baseUrl = "http://happytodo.int2root.com/";
    public static final String signUp = baseUrl+"/v1/signup";
    public static final String signIn = baseUrl+"/v1/signin";


    public static final String TAG = ApiUtil.class.getSimpleName();

    public static void jsonRequest(final HandleResponce handleResponce,String url,JSONObject jsonObject,int method){
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        System.out.println(jsonObject.toString());




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
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }
}
