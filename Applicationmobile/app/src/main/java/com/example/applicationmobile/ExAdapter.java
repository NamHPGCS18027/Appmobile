package com.example.applicationmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExAdapter extends RecyclerView.Adapter<ExAdapter.MyViewHolder> {

    private final List<ex> exlisttrip;
    private final Context context;

    public ExAdapter (List<ex> exlisttrip , Context context){

        this.exlisttrip = exlisttrip;
        this.context = context;
    }

    @NonNull
    @Override
    public ExAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ex_row , parent , false);
        return new ExAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExAdapter.MyViewHolder holder, int position) {
        ex ex = exlisttrip.get(position);
        if (ex == null){
            return;
        }
        holder.TXTEX_Type.setText(ex.getEX_Type());
        holder.TXTEX_Time.setText(ex.getEX_Time());
        holder.TXTEX_Expense.setText(ex.getEX_Expense());
        holder.BTN_EX_Delete.setOnClickListener(view -> {
            DBhelper db =new DBhelper(context);
            Boolean checkdelete = db.deletesEXtripdata(ex.getEX_id());
            if(checkdelete){
                Toast.makeText(context, "Entry Deleted", Toast.LENGTH_SHORT).show();
                ((viewDetail)context).finish();
                ((viewDetail)context).overridePendingTransition(0, 0);
                Intent intent =new Intent(context,viewDetail.class);

                trip trip = db.getTripbyID(ex.getEXTRIP_id());

                Bundle bundle = new Bundle();
                bundle.putString("_id",trip.get_id());
                bundle.putString("trip_name",trip.getTrip_name());
                bundle.putString("trip_destination",trip.getTrip_destination());
                bundle.putString("trip_date",trip.getTrip_date());
                bundle.putString("trip_risk",trip.getTrip_risk());
                bundle.putString("trip_Description",trip.getTrip_Description());

                intent.putExtras(bundle);
                context.startActivity(intent);
                ((viewDetail)context).overridePendingTransition(0, 0);
            }else {
                Toast.makeText(context, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (exlisttrip != null){
            return exlisttrip.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  TXTEX_Type,TXTEX_Expense,TXTEX_Time;
        ImageView BTN_EX_Delete;
        LinearLayout ex_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TXTEX_Type = itemView.findViewById(R.id.tyleEXtrip);
            TXTEX_Expense = itemView.findViewById(R.id.EXtripprice);
            TXTEX_Time = itemView.findViewById(R.id.timeEXvalue);
            BTN_EX_Delete = itemView.findViewById(R.id.deleteExbtn);

            ex_layout= itemView.findViewById(R.id.ex_layout);
        }

    }
}

