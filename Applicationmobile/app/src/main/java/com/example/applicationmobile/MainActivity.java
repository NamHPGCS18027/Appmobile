package com.example.applicationmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    FloatingActionButton addbtn;
    DBhelper DB;
    CustomAdapter customAdapter;
    SearchView searchView;
    ImageView imgemty1;
    TextView nodata1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgemty1 = findViewById(R.id.imgemty1);
        nodata1 = findViewById(R.id.nodate1);
        recyclerView = findViewById(R.id.recyclerView);
        addbtn = findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddTrip.class);
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        customAdapter =new CustomAdapter(MainActivity.this,getListTrip());
        recyclerView.setAdapter(customAdapter);
    }

    public List<trip> getListTrip() {

        DB = new DBhelper(this);
        List<trip> list = new ArrayList<>();
        Cursor cursor = DB.getdata();
        if (cursor.getCount() == 0){
            imgemty1.setVisibility(View.VISIBLE);
            nodata1.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()) {
                list.add(new trip(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }
        }
        return list;
    }

    List<ex> getListextrip() {
        DB = new DBhelper(this);
        List<ex> list = new ArrayList<>();
        Cursor cursor = DB.getlistEXdata();
        if (cursor.getCount() == 0){

        }else {
            while (cursor.moveToNext()) {
                list.add(new ex(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4)));
            }
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.menu_delete){
            Toast.makeText(this,"delete",Toast.LENGTH_SHORT).show();
            DBhelper DB = new DBhelper(this);
            DB.deleteAllData();
            recreate();
        }
        if (item.getItemId()== R.id.cloudupload){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String deviceName = Build.MANUFACTURER
                    + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                    + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
            backup backup = new backup(new Date(),deviceName,getListTrip(),getListextrip());
            db.collection("Trip")
                    .add(backup)
                    .addOnSuccessListener(document -> {
                        Toast.makeText(this, "Backup successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                    Toast.makeText(this, "Not Backup successfully", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
            });

        }
        return super.onOptionsItemSelected(item);
    }
}