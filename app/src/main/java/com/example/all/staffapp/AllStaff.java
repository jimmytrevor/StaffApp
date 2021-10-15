package com.example.all.staffapp;

import static com.example.all.staffapp.HttpApis.select;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllStaff extends AppCompatActivity {
    private int type;
    private HashMap<String, String> ResultHash = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    java.util.List<Data_List> List;
    private Staff_Adapter adapter;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_staff);
        sharedPreferences= getSharedPreferences(PhoneDatabase.local_db(), Context.MODE_PRIVATE);

        List = new ArrayList<>();
        adapter = new Staff_Adapter(AllStaff.this, List);
        recyclerView = findViewById(R.id.staff_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllStaff.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        view_my_transactions();
    }

    private void view_my_transactions() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...................");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest request = new StringRequest(Request.Method.POST, select, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    if (response.equals("found_some") || response.contains("found_some")) {
                        String final_results = response.replace("found_some", "");
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(final_results);
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject jsonObject = array.getJSONObject(i);
                                List.add(new Data_List(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("fname"),
                                        jsonObject.getString("lname"),
                                        jsonObject.getString("phone"),
                                        jsonObject.getString("gender"),
                                        jsonObject.getString("date_created"),
                                        jsonObject.getString("admins_id"),
                                        jsonObject.getString("address"),
                                        jsonObject.getString("salary")
                                ));
                                recyclerView.setAdapter(adapter);
                                progressDialog.dismiss();
                                //adding the product to product list
                            }
                            //creating adapter object and setting it to recyclerview
                        } catch (Exception e) {
                            Toast.makeText(AllStaff.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AllStaff.this, "" + response, Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(ViewInformation.this,Dashboard.class));
//                            finish();
                        progressDialog.dismiss();

                    }
                } catch (Exception e) {
                    Toast.makeText(AllStaff.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(AllStaff.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AllStaff.this);
        requestQueue.add(request);
    }

    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    protected void onRestart() {
        recreate();
        super.onRestart();
    }
}
