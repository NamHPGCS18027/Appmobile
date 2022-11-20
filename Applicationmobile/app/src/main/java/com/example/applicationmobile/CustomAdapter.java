package com.example.applicationmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<trip> mListTrip;
    private List<trip> mListTripfull;

    public CustomAdapter( Context context , List<trip> mListTrip){
        this.context = context;
        this.mListTrip = mListTrip;
        this.mListTripfull = mListTrip;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        trip trip = mListTrip.get(position);
        if (trip == null){
            return;
        }
        holder.IDTXT.setText(trip.get_id());
        holder.tripnameTXT.setText(trip.getTrip_name());
        holder.tripdestinationTXT.setText(trip.getTrip_destination());
        holder.tripdayTXT.setText(trip.getTrip_date());
        holder.tripriskTXT.setText(trip.getTrip_risk());
        holder.tripDescriptionTXT.setText(trip.getTrip_Description());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("_id",trip.get_id());
                bundle.putString("trip_name",trip.getTrip_name());
                bundle.putString("trip_destination",trip.getTrip_destination());
                bundle.putString("trip_date",trip.getTrip_date());
                bundle.putString("trip_risk",trip.getTrip_risk());
                bundle.putString("trip_Description",trip.getTrip_Description());

                Intent intent = new Intent(context, viewDetail.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListTrip != null){
            return mListTrip.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView IDTXT, tripnameTXT,tripdestinationTXT,tripdayTXT,tripriskTXT,tripDescriptionTXT;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IDTXT = itemView.findViewById(R.id.TripIdTXT);
            tripnameTXT = itemView.findViewById(R.id.tripnameTXT);
            tripdayTXT = itemView.findViewById(R.id.tripdayTXT);
            tripdestinationTXT = itemView.findViewById(R.id.textView);
            tripriskTXT= itemView.findViewById(R.id.textView2);
            tripDescriptionTXT= itemView.findViewById(R.id.textView3);
            mainLayout= itemView.findViewById(R.id.mainLayout);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    mListTrip =mListTripfull;
                }else {
                    List<trip> list = new ArrayList<>();
                    for (trip item : mListTripfull){
                        if (item.getTrip_name().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(item);
                        }
                    }
                    mListTrip = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListTrip;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListTrip = (List<trip>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
