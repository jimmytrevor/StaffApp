package com.example.all.staffapp;

import static com.example.all.staffapp.HttpApis.insert;

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

import java.util.HashMap;
import java.util.Map;

public class AddStaff extends  AppCompatActivity {
    private Button btn_submit;
    private EditText fname;
    private EditText lname;
    private EditText gender;
    private EditText phone;
    private EditText address;
    private EditText salary;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        phone = findViewById(R.id.phone);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        salary = findViewById(R.id.salary);
        btn_submit = findViewById(R.id.btn_submit);

        sharedPreferences= getSharedPreferences(PhoneDatabase.local_db(), Context.MODE_PRIVATE);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(fname.getText().toString().trim()) ){
                    Toast.makeText(AddStaff.this, "First Name is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(lname.getText().toString().trim()) ){
                    Toast.makeText(AddStaff.this, "Last Name is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone.getText().toString().trim()) || phone.getText().toString().trim().length() != 10 ){
                    Toast.makeText(AddStaff.this, "Phone is invalid", Toast.LENGTH_SHORT).show();
                }
                else if (!gender.getText().toString().trim().equalsIgnoreCase("Male") && !gender.getText().toString().trim().equalsIgnoreCase("female")){
                    Toast.makeText(AddStaff.this, "Gender is invalid", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(address.getText().toString().trim())  ){
                    Toast.makeText(AddStaff.this, "Address is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(salary.getText().toString().trim())  || Integer.parseInt(salary.getText().toString().trim()) < 5000) {
                    Toast.makeText(AddStaff.this, "Salary must be greater than 5000", Toast.LENGTH_SHORT).show();
                }
                else{
//                    save marks
                    register_staff();

                }
            }
        });
    }

    private void register_staff() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.contains("staff_Created")) {
                    Toast.makeText(AddStaff.this, "Staff Account Created", Toast.LENGTH_SHORT).show();
                    fname.setText("");
                    lname.setText("");
                    gender.setText("");
                    phone.setText("");
                    address.setText("");
                    salary.setText("");
                    startActivity(new Intent(AddStaff.this,AllStaff.class));
                }
                else {
                    Toast.makeText(AddStaff.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(AddStaff.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("admin_id",sharedPreferences.getString("user_id",null));
                params.put("fname",fname.getText().toString().trim());
                params.put("lname",lname.getText().toString().trim());
                params.put("gender",gender.getText().toString().trim());
                params.put("phone",phone.getText().toString().trim());
                params.put("address",address.getText().toString().trim());
                params.put("salary",salary.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddStaff.this);
        requestQueue.add(request);
    }

    public void view_patient(View view) {
        startActivity(new Intent(AddStaff.this,AllStaff.class));
    }
    public void add_patient(View view) {

    }
}