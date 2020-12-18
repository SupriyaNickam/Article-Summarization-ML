package com.example.precis;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText urlText;
    private TextView summaryView;
    private String summary;



    @RequiresApi(api = VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3700B3")));

        Button getBtn = findViewById(R.id.button);
        Button shareBtn = findViewById(R.id.shareButton);
        Button clearBtn = findViewById(R.id.clearButton);



        urlText = findViewById(R.id.editTextUrl);
        summaryView = findViewById(R.id.textViewSummary);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Requests");


        getBtn.setOnClickListener(view -> {
            // Read the input field and push a new instance to the Firebase database
            if (Build.VERSION.SDK_INT >= VERSION_CODES.O) {
                mDatabase.push()
                        .setValue(new Summary(urlText.getText().toString()));
            }

            // Clear the input
            urlText.setText("");
            summaryView.setText(" ");
        });

        mDatabase.addValueEventListener(new ValueEventListener() { //attach listener

            @RequiresApi(api = VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //something changed!
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        summary = Objects.requireNonNull(snapshot.child("summaryText").getValue()).toString();
                        summaryView.setText(summary);
                        summaryView.setMovementMethod(new ScrollingMovementMethod());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { //update UI here if error occurred.

            }
        });

        shareBtn.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, summary);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        clearBtn.setOnClickListener(view -> summaryView.setText(" "));


    }
}