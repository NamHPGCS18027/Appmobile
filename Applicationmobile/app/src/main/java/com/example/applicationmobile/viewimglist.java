package com.example.applicationmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewimglist extends AppCompatActivity {

    private ImageView addtripbtn;
    private RecyclerView mRecylerView;
    private ImageAdapter mAdapter;
    private DatabaseReference mDatabaceRef;
    private List<uploadImage> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimglist);

        addtripbtn = findViewById(R.id.view_addtripbtn);
        mRecylerView = findViewById(R.id.recycler_view_image);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();
        mDatabaceRef = FirebaseDatabase.getInstance().getReference("uploadimgs");
        mDatabaceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    uploadImage upload = postSnapshot.getValue(uploadImage.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(viewimglist.this,mUploads);
                mRecylerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(viewimglist.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        addtripbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewimglist.this , add_img.class);
                startActivity(intent);
            }
        });
    }
}