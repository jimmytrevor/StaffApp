package com.example.all.staffapp;

import static com.example.all.staffapp.HttpApis.delete;
import static com.example.all.staffapp.HttpApis.insert;
import static com.example.all.staffapp.HttpApis.update;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

public class Details extends  AppCompatActivity {
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

        fname.setText(getIntent().getStringExtra("fname"));
        lname.setText(getIntent().getStringExtra("lname"));
        gender.setText(getIntent().getStringExtra("gender"));
        phone.setText(getIntent().getStringExtra("phone"));
        address.setText(getIntent().getStringExtra("address"));
        salary.setText(getIntent().getStringExtra("salary"));

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(fname.getText().toString().trim()) ){
                    Toast.makeText(Details.this, "First Name is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(lname.getText().toString().trim()) ){
                    Toast.makeText(Details.this, "Last Name is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone.getText().toString().trim()) || phone.getText().toString().trim().length() != 10 ){
                    Toast.makeText(Details.this, "Phone is invalid", Toast.LENGTH_SHORT).show();
                }
                else if (!gender.getText().toString().trim().equalsIgnoreCase("Male") && !gender.getText().toString().trim().equalsIgnoreCase("female")){
                    Toast.makeText(Details.this, "Gender is invalid", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(address.getText().toString().trim())  ){
                    Toast.makeText(Details.this, "Address is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(salary.getText().toString().trim())  || Integer.parseInt(salary.getText().toString().trim()) < 5000) {
                    Toast.makeText(Details.this, "Salary must be greater than 5000", Toast.LENGTH_SHORT).show();
                }
                else{
//                    save marks
                    options();

                }
            }
        });
    }

    public  void  options(){
        AlertDialog.Builder builder= new AlertDialog.Builder(Details.this)
                .setCancelable(false)
                .setMessage("Select the Option You want to Perform")
                .setPositiveButton("Delete", (dialogInterface, i) -> delete_staff())
                .setNeutralButton("Update", (dialogInterface, i) -> update_staff())
                .setNegativeButton("None", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog= builder.create();
        dialog.show();
    }
    private void update_staff() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Staff Info...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.contains("staff_updated")) {
                    Toast.makeText(Details.this, "Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Details.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(Details.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("staff_id",getIntent().getStringExtra("staff_id"));
                params.put("fname",fname.getText().toString().trim());
                params.put("lname",lname.getText().toString().trim());
                params.put("gender",gender.getText().toString().trim());
                params.put("phone",phone.getText().toString().trim());
                params.put("address",address.getText().toString().trim());
                params.put("salary",salary.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Details.this);
        requestQueue.add(request);
    }

    public void view_patient(View view) {
        startActivity(new Intent(Details.this,AllStaff.class));
    }
    public void add_patient(View view) {

    }

    private void delete_staff() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, delete, (Response.Listener<String>) response -> {
            progressDialog.dismiss();
            if (response.contains("staff_deleted")) {
                Toast.makeText(Details.this, "Staff Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Details.this,AddStaff.class));
                finish();
            }
            else {
                Toast.makeText(Details.this, response, Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(Details.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("staff_id",getIntent().getStringExtra("staff_id"));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Details.this);
        requestQueue.add(request);
    }

}