package com.example.all.staffapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Staff_Adapter extends RecyclerView.Adapter<Staff_Adapter.AdapterHolder> implements Filterable {

    private Context mCtx;
    String status;
    private int doll;
    List<Data_List> mData;
    List<Data_List> mDataFiltered;

    public Staff_Adapter(Context mCtx, List<Data_List> mData) {
        this.mCtx = mCtx;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.stall_list, parent,false);
//        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(layoutParams);
        return new AdapterHolder(view);
    }
    @Override
    public void onBindViewHolder(final AdapterHolder holder, final int position) {
        final Data_List info = mDataFiltered.get(position);
        holder.date.setText("Date Added:"+info.getDate_Created());
        holder.name.setText("Name:      "+info.getFname() +" "+info.getLname());
        holder.gender.setText("Gender:  "+info.getGender());
        holder.phone.setText("Phone:    "+info.getPhone());
        holder.address.setText("Address:"+info.getAddress());
        holder.salary.setText("Salary: Shs. "+info.getSalary());
        holder.car_here.setOnClickListener(view -> {
            Intent resultIntent = new Intent(mCtx, Details.class);
            resultIntent.putExtra("staff_id",""+info.getId());
            resultIntent.putExtra("phone",""+info.getPhone());
            resultIntent.putExtra("fname",""+info.getFname());
            resultIntent.putExtra("lname",""+info.getLname());
            resultIntent.putExtra("address",""+info.getAddress());
            resultIntent.putExtra("salary",""+info.getSalary());
            resultIntent.putExtra("gender",""+info.getGender());
            mCtx.startActivity(resultIntent);
        });
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return myFilterData;
    }


    private Filter myFilterData = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String key=constraint.toString();
            if (key.isEmpty()){
                mDataFiltered=mData;
            }
            else{
                List<Data_List> FilteredList=new ArrayList<>();
                mDataFiltered=FilteredList;
            }
            FilterResults  filterResults=new FilterResults();
            filterResults.values=mDataFiltered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mDataFiltered=(List<Data_List>)results.values;
            notifyDataSetChanged();
        }
    };
    class AdapterHolder extends RecyclerView.ViewHolder {
        private CardView car_here;
        private Button details;
        private TextView name,phone,gender,date,address,salary;
        public AdapterHolder(View itemView) {
            super(itemView);
            car_here=itemView.findViewById(R.id.car_here);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            date=itemView.findViewById(R.id.date);
            gender=itemView.findViewById(R.id.gender);
            address=itemView.findViewById(R.id.address);
            salary=itemView.findViewById(R.id.salary);
        }
    }

}