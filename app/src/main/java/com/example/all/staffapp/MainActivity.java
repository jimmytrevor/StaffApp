package com.example.all.staffapp;

import static com.example.all.staffapp.HttpApis.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check_profile_existence();
        setContentView(R.layout.activity_main);
        btn_login=findViewById(R.id.btn_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString().toString())){
                    username.setError("Login name is required");
                }
                else if (TextUtils.isEmpty(password.getText().toString().toString())){
                    password.setError("Login password is required");
                }
                else{
                    login_mtd();
                }
            }
        });
    }

    private void login_mtd() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("UPDATE");
        progressDialog.setMessage("Please Wait ...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.contains("true_user")) {
                    String final_results=response.replace("true_user","");
                    try {
                        JSONObject jsonObject=new JSONObject(final_results);
                        SharedPreferences sharedPreferences = getSharedPreferences(PhoneDatabase.local_db(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("profile", "on");
                        editor.putString("user_id", jsonObject.getString("id"));
                        editor.putString("fname", jsonObject.getString("fname"));
                        editor.putString("lname", jsonObject.getString("lname"));
                        editor.putString("gender", jsonObject.getString("gender"));
                        editor.putString("phone", jsonObject.getString("phone"));
                        editor.putString("username", jsonObject.getString("username"));
                        editor.putString("date_created", jsonObject.getString("date_created"));
                        editor.apply();
                        Toast.makeText(MainActivity.this,"Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, Dashboard.class));
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username.getText().toString().trim());
                params.put("password",password.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    private void check_profile_existence() {
        SharedPreferences sharedPreferences = getSharedPreferences(PhoneDatabase.local_db(), Context.MODE_PRIVATE);
        String pref = sharedPreferences.getString("profile", null);
        if (pref == null || pref == "") {

        }
        else{
            startActivity(new Intent(MainActivity.this, Dashboard.class));
            finish();
        }
    }
}