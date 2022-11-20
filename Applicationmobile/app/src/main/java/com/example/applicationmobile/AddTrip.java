package com.example.applicationmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

public class AddTrip extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    Button trip_date , addtrip;
    EditText trip_name ,  trip_destination  , trip_Description ;
    Switch trip_risk;
    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        trip_date = findViewById(R.id.TripDate);
        trip_name = findViewById(R.id.Name_of_trip);
        trip_destination = findViewById(R.id.Destination);
        trip_risk = findViewById(R.id.risk);
        trip_Description = findViewById(R.id.Description);
        addtrip = findViewById(R.id.addtrip);
        DB = new DBhelper(this);
        initDatePicker();
        trip_date.setText(getTodayDate());
        trip_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        addtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trip_nameTXT= trip_name.getText().toString();
                String trip_destinationTXT= trip_destination.getText().toString();
                String trip_dateTXT= trip_date.getText().toString();
                String trip_riskTXT;
                String trip_DescriptionTXT= trip_Description.getText().toString();

                if (trip_risk.isChecked()) {
                    trip_riskTXT = "Owner";
                } else {
                    trip_riskTXT ="Tenant";
                }


                Boolean checkinsertdata = DB.insertstripdata(trip_nameTXT,trip_destinationTXT,trip_dateTXT,trip_riskTXT,trip_DescriptionTXT);
                if (checkinsertdata==true) {
                    Toast.makeText(AddTrip.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTrip.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(AddTrip.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day,month,year);
                trip_date.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(AddTrip.this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMounthFormat (month) + "/" + day + "/" +year ;
    }

    private String getMounthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "FUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        return "JAN";
    }
}