package com.example.smartproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DoorActivityAdapter adapter;
    List<DoorActivity> doorActivityList;
    Button homeButton; // Home button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize List and Adapter
        doorActivityList = new ArrayList<>();
        adapter = new DoorActivityAdapter(doorActivityList);
        recyclerView.setAdapter(adapter);

        // Initialize Home Button
        homeButton = findViewById(R.id.homeButton); // Link the button from XML
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close HistoryActivity
            }
        });

        // Fetch Data from Firebase
        String userId = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : "default_user"; // Replace with default value if not using Firebase Authentication

        FirebaseDatabase.getInstance().getReference("door_activity")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        doorActivityList.clear(); // Clear old data
                        for (DataSnapshot data : snapshot.getChildren()) {
                            DoorActivity activity = data.getValue(DoorActivity.class);
                            if (activity != null) {
                                doorActivityList.add(activity);
                            }
                        }
                        adapter.notifyDataSetChanged(); // Update RecyclerView
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle errors
                    }
                });


    }
}
