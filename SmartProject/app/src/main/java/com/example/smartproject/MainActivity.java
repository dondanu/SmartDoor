package com.example.smartproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText passwordBox;
    Button btnSubmit, viewHistoryButton; // View History button added
    TextView statusMessage;

    // Firebase Reference
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordBox = findViewById(R.id.passwordBox);
        btnSubmit = findViewById(R.id.btnSubmit);
        viewHistoryButton = findViewById(R.id.viewHistoryButton); // Initialize View History button
        statusMessage = findViewById(R.id.statusMessage);

        // Firebase connection
        databaseReference = FirebaseDatabase.getInstance().getReference("DoorStatus");

        // Unlock Button Logic
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordBox.getText().toString();
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // Get current timestamp

                if (password.equals("1234")) { // Correct password
                    databaseReference.setValue(1); // Set DoorStatus to 1 in Firebase
                    logDoorActivity("Opened", timestamp); // Log activity
                    statusMessage.setText("Password is correct! The door is open.");
                    Toast.makeText(MainActivity.this, "The door has been opened", Toast.LENGTH_SHORT).show();
                } else { // Incorrect password
                    databaseReference.setValue(0); // Set DoorStatus to 0 in Firebase
                    logDoorActivity("Closed", timestamp); // Log activity
                    statusMessage.setText("Password is incorrect.");
                    Toast.makeText(MainActivity.this, "The door could not be opened", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // View History Button Logic
        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent); // Navigate to HistoryActivity
            }
        });
    }

    // Method to log door activity into Firebase
    private void logDoorActivity(String status, String timestamp) {
        // Firebase node reference
        DatabaseReference activityRef = FirebaseDatabase.getInstance()
                .getReference("door_activity"); // No user-specific ID for now

        // Create activity object
        DoorActivity activity = new DoorActivity(timestamp, status);

        // Push activity data
        activityRef.push()
                .setValue(activity)
                .addOnSuccessListener(aVoid -> {
                    // Success message
                    Toast.makeText(MainActivity.this, "Activity logged!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Error message
                    Toast.makeText(MainActivity.this, "Failed to log activity!", Toast.LENGTH_SHORT).show();
                });

    }
}
