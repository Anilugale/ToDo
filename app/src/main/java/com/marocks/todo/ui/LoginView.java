package com.marocks.todo.ui;

import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

public class LoginView extends AppCompatActivity implements HandleResponce {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
    }

    public void submit(View v){
        TextInputLayout emailId = (TextInputLayout) findViewById(R.id.email_text_input_layout);
                TextInputLayout pwd = (TextInputLayout) findViewById(R.id.pwd_text_input_layout);
        TextInputLayout domain = (TextInputLayout) findViewById(R.id.domain_text_input_layout);

        if(!Utile.isValidEmail(emailId.getEditText().getText())){
            emailId.setError("You need to enter a valid email id");
            emailId.getEditText().requestFocus();
        }else if(!Utile.isEmptyText(pwd.getEditText().getText())){
            emailId.setErrorEnabled(false);
            pwd.setError("You need to enter a valid password id");
            pwd.getEditText().requestFocus();
        }else if(!Utile.isEmptyText(domain.getEditText().getText())){
            pwd.setErrorEnabled(false);
            domain.setError("You need to enter a valid domain id");
            domain.getEditText().requestFocus();
        }else{
            v.requestFocus();

            JSONObject requestJson = new JSONObject();
            try {

                requestJson.put(Utile.emailJson,emailId.getEditText().getText().toString().trim());
                requestJson.put(Utile.passwordJson,pwd.getEditText().getText().toString().trim());
                requestJson.put(Utile.domainJson,domain.getEditText().getText().toString().trim());

                ApiUtil.jsonRequest(this,ApiUtil.signIn,requestJson, Request.Method.POST);
                progressDialog = ProgressDialog.show(this,"","Loading...",true,false);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onResponse(JSONObject response) {
        dismissProgressBar();
        if(response!=null){
            try {
                String autTokenJson= response.getString(Utile.autTokenJson);
                SharedPreferences sh = getSharedPreferences(getPackageName(),MODE_PRIVATE);
                sh.edit().putString(Utile.autTokenJson,autTokenJson).apply();
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        System.out.println(response.toString());

    }

    @Override
    public void onFailure(VolleyError error) {
        dismissProgressBar();
        if(error.networkResponse.statusCode == 401) {
            Utile.showErrorDialog(this, R.string.login401);
        }else if(error.networkResponse.statusCode == 422){
            Utile.showErrorDialog(this, R.string.login422);
        }
    }

    void dismissProgressBar(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
