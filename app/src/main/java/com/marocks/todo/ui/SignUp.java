package com.marocks.todo.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.marocks.todo.R;
import com.marocks.todo.Utile;
import com.marocks.todo.api.ApiUtil;
import com.marocks.todo.api.HandleResponce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity implements HandleResponce {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        SharedPreferences sh = getSharedPreferences(getPackageName(),MODE_PRIVATE);
        if(sh.getString(Utile.autTokenJson,null) != null){
            init();
        }
    }

    public void submit(View view) {
        TextInputLayout emailId = (TextInputLayout) findViewById(R.id.email_text_input_layout);
        TextInputLayout userName = (TextInputLayout) findViewById(R.id.username_text_input_layout);
        TextInputLayout pwd = (TextInputLayout) findViewById(R.id.pwd_text_input_layout);
        TextInputLayout rePwd = (TextInputLayout) findViewById(R.id.confirmPwd_text_input_layout);
        TextInputLayout domain = (TextInputLayout) findViewById(R.id.domain_text_input_layout);

        if(!Utile.isEmptyText(userName.getEditText().getText())){
            userName.setError("You need to enter a user name");
            userName.getEditText().requestFocus();
        }else if(!Utile.isValidEmail(emailId.getEditText().getText())){
            userName.setErrorEnabled(false);
            emailId.setError("You need to enter a valid email id");
            emailId.getEditText().requestFocus();
        }else if(!Utile.isEmptyText(pwd.getEditText().getText())){
            emailId.setErrorEnabled(false);
            pwd.setError("You need to enter a valid password id");
            pwd.getEditText().requestFocus();
        }else if(!Utile.isEmptyText(rePwd.getEditText().getText()) ){
            pwd.setErrorEnabled(false);
            rePwd.setError("You need to enter a valid confirm password");
            rePwd.getEditText().requestFocus();
        }else if(!rePwd.getEditText().getText().toString().trim().equals(pwd.getEditText().getText().toString().trim())  ){
            pwd.setErrorEnabled(false);
            rePwd.setError("Confirm password not match");
            rePwd.getEditText().requestFocus();
        }else if(!Utile.isEmptyText(domain.getEditText().getText())){
            rePwd.setErrorEnabled(false);
            domain.setError("You need to enter a valid domain id");
            domain.getEditText().requestFocus();
        }else{
            view.requestFocus();

                JSONObject requestJson = new JSONObject();
            try {
                requestJson.put(Utile.nameJson,userName.getEditText().getText().toString().trim());
                requestJson.put(Utile.emailJson,emailId.getEditText().getText().toString().trim());
                requestJson.put(Utile.passwordJson,pwd.getEditText().getText().toString().trim());
                requestJson.put(Utile.password_confirmationJson,rePwd.getEditText().getText().toString().trim());
                requestJson.put(Utile.domainJson,domain.getEditText().getText().toString().trim());

                ApiUtil.jsonRequest(this,ApiUtil.signUp,requestJson, Request.Method.POST);
                progressDialog = ProgressDialog.show(this,"","Loading...",true,false);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onResponse(JSONObject response) {

        dismissProgressBar();
        System.out.println(response.toString());
        startActivity(new Intent(this,LoginView.class));
        finish();
    }

    @Override
    public void onResponse(JSONArray response) {

    }

    @Override
    public void onFailure(VolleyError error) {
        dismissProgressBar();
    }

    void dismissProgressBar(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
