package com.example.applicationmobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class viewDetail extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    TextView trip_id3,deleteid, tripidTXT, tripnameTXT,tripdestinationTXT,tripdateTXT,tripriskTXT,tripDescriptionTXT;
    String _id,trip_name,trip_destination ,trip_date,trip_risk,trip_Description;
    Button UpdateBtn , Deletebtn , updatetrip ,trip_date3 ;
    EditText trip_name3 ,  trip_destination3  , trip_Description3 ;
    Switch trip_risk3;
    TextView EXT_id;
    EditText EX_Expense;
    Button Add_expenses , addexdetailbtn;
    DBhelper DB;
    RecyclerView recyclerViewEX;
    ExAdapter exAdapter;
    ImageView Addimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        recyclerViewEX = findViewById(R.id.recyclerViewEX);
        tripidTXT = findViewById(R.id.tripid);
        tripnameTXT = findViewById(R.id.detailtripname);
        tripdestinationTXT = findViewById(R.id.detailtripDestination);
        tripdateTXT = findViewById(R.id.detailtripdate);
        tripriskTXT = findViewById(R.id.detailtype);
        tripDescriptionTXT = findViewById(R.id.detailDescription);
        UpdateBtn = findViewById(R.id.UpdateBtn);
        Deletebtn = findViewById(R.id.DeleteBtn);
        Add_expenses = findViewById(R.id.Add_expenses);
        Addimg = findViewById(R.id.Addimg);

        getAndSetIntentData();

        Addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewDetail.this , viewimglist.class);
                startActivity(intent);
            }
        });

        // UPDATE TRIP
        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dialog_update,null);

                initDatePicker();
                trip_id3=dialogView.findViewById(R.id.tripid3);
                trip_name3= dialogView.findViewById(R.id.Name_of_trip3);
                trip_destination3=dialogView.findViewById(R.id.Destination3);
                trip_date3 = dialogView.findViewById(R.id.TripDate3);
                trip_risk3 = dialogView.findViewById(R.id.risk3);
                trip_Description3 = dialogView.findViewById(R.id.Description3);
                updatetrip = dialogView.findViewById(R.id.UpdatetripBtn3);
                DB = new DBhelper(viewDetail.this);

                trip_id3.setText(_id);
                trip_name3.setText(trip_name);
                trip_destination3.setText(trip_destination);
                trip_date3.setText(trip_date);
                trip_Description3.setText(trip_Description);

                trip_date3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datePickerDialog.show();
                    }
                });

                updatetrip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String _idTXT = trip_id3.getText().toString();
                        String trip_nameTXT= trip_name3.getText().toString();
                        String trip_destinationTXT= trip_destination3.getText().toString();
                        String trip_dateTXT= trip_date3.getText().toString();
                        String trip_riskTXT;
                        String trip_DescriptionTXT= trip_Description3.getText().toString();

                        if (trip_risk3.isChecked()) {
                            trip_riskTXT = "Owner";
                        } else {
                            trip_riskTXT ="Tenant";
                        }

                        Boolean checkupdatedata = DB.updatestripdata(_idTXT,trip_nameTXT,trip_destinationTXT,trip_dateTXT,trip_riskTXT,trip_DescriptionTXT);
                        if (checkupdatedata == true)
                            Toast.makeText(viewDetail.this,"Entry Updated" , Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(viewDetail.this,"New Entry Not Updated" , Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });

        //DELETE TRIP
        Deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2=new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView2 = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dialog_delete,null);
                deleteid= dialogView2.findViewById(R.id.deleteid);
                DB =new DBhelper(viewDetail.this);

                deleteid.setText(_id);

                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String _idTXT= deleteid.getText().toString();

                        Boolean checkdeletedata = DB.deletestripdata(_idTXT);
                        if (checkdeletedata==true) {
                            Toast.makeText(viewDetail.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(viewDetail.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(viewDetail.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
                        }
//                        dialogInterface.dismiss();
                    }
                });

                builder2.setView(dialogView2);
                builder2.setCancelable(false);
                builder2.show();
            }
        });

        //ADD EXPENSES
        Add_expenses.setOnClickListener(new View.OnClickListener() {
            String[] items = {"Food" , "Travel" , "Other"};
            AutoCompleteTextView EXTypeEXE;
            ArrayAdapter<String> adapterItem;
            String EX_Type;
            TextView EX_Time;
            int t1Hour , t1Minute;
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder3=new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView3 = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dialog_addexpenses,null);
                EX_Expense = dialogView3.findViewById(R.id.EX_Expense);
                EX_Time = dialogView3.findViewById(R.id.EX_Time);
                addexdetailbtn = dialogView3.findViewById(R.id.addexdetailbtn);
                EXT_id = dialogView3.findViewById(R.id.EX_id);

                EXTypeEXE = dialogView3.findViewById(R.id.EX_TypeEXE);

                adapterItem =new ArrayAdapter<String>(viewDetail.this,R.layout.list_itemdropdown,items);

                EXTypeEXE.setAdapter(adapterItem);

                EXTypeEXE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();
                        EX_Type = item;
                    }
                });

                EX_Time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                viewDetail.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {
                                        t1Hour = hourofday;
                                        t1Minute = minute;
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.set(0,0,0,t1Hour,t1Minute);
                                        EX_Time.setText(DateFormat.format("hh:mm aa",calendar));
                                    }
                                },12,0,false
                        );
                        timePickerDialog.updateTime(t1Hour,t1Minute);
                        timePickerDialog.show();
                    }
                });

                DB =new DBhelper(viewDetail.this);
                EXT_id.setText(_id);

                addexdetailbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String EXTRIP_idTXT = EXT_id.getText().toString();
                        String EX_TypeTXT = EX_Type;
                        String EX_ExpenseTXT = EX_Expense.getText().toString();
                        String EX_TimeTXT = EX_Time.getText().toString();

                        Boolean checkupdatedata = DB.insertEXdata(EX_TypeTXT,EX_ExpenseTXT+" VND",EX_TimeTXT,EXTRIP_idTXT);
                        if (checkupdatedata == true) {
                            Toast.makeText(viewDetail.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                            recreate();
                        }
                        else
                            Toast.makeText(viewDetail.this,"New Entry Not Inserted" , Toast.LENGTH_SHORT).show();
                    }
                });



                builder3.setView(dialogView3);
                builder3.setCancelable(true);
                builder3.show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(viewDetail.this);
        recyclerViewEX.setLayoutManager(linearLayoutManager);
        exAdapter =new ExAdapter(getListextrip(),this);
        recyclerViewEX.setAdapter(exAdapter);
    }

    //get data
    void getAndSetIntentData(){
        String error = "No data";
        Bundle bundle = getIntent().getExtras();
        _id = bundle.getString("_id",error);
        trip_name = bundle.getString("trip_name", error);
        trip_destination = bundle.getString("trip_destination", error);
        trip_date = bundle.getString("trip_date", error);
        trip_risk = bundle.getString("trip_risk", error);
        trip_Description = bundle.getString("trip_Description", error);

        tripidTXT.setText(_id);
        tripnameTXT.setText(trip_name);
        tripdestinationTXT.setText(trip_destination);
        tripdateTXT.setText(trip_date);
        tripriskTXT.setText(trip_risk);
        tripDescriptionTXT.setText(trip_Description);

    }

    // date picker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day,month,year);
                trip_date3.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(viewDetail.this, style, dateSetListener, year, month, day);
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

    List<ex> getListextrip() {
        ImageView imgemty;
        TextView nodata;

        imgemty = findViewById(R.id.imgemty);
        nodata = findViewById(R.id.nodate);
        DB = new DBhelper(this);
        List<ex> list = new ArrayList<>();
        Cursor cursor = DB.getEXdata(_id);
        if (cursor.getCount() == 0){
            imgemty.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()) {
                list.add(new ex(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4)));
            }
        }
        return list;
    }
}