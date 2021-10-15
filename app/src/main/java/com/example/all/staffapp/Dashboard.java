package com.example.all.staffapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sharedPreferences= getSharedPreferences(PhoneDatabase.local_db(), Context.MODE_PRIVATE);
    }

    public void logout(View view) {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
        startActivity( new Intent(Dashboard.this, MainActivity.class));
        finish();
    }

    public void view_staff(View view) {
        startActivity(new Intent(Dashboard.this,AllStaff.class));
    }

    public void add_staff(View view) {
        startActivity(new Intent(Dashboard.this,AddStaff.class));
    }
}