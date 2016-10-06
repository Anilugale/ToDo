package com.marocks.todo.api;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
  Created by anil on 30/8/16.
 */
public interface HandleResponce {
    public void onResponse(JSONObject response);
    public void onResponse(JSONArray response);
    public void onFailure(VolleyError error);
    public Context getContext();
}
