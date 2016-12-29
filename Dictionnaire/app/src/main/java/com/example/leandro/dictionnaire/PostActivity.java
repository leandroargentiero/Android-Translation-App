package com.example.leandro.dictionnaire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {

    private EditText mWoord, mVertaling;
    private Button mBtnAdd;
    // Firebase Database reference
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Get root connection and create child node "woorden"
        mDatabase = FirebaseDatabase.getInstance().getReference().child("woorden");

        // find input fields & button by id
        mWoord = (EditText) findViewById(R.id.txtWoord);
        mVertaling = (EditText) findViewById(R.id.txtVertaling);
        mBtnAdd = (Button) findViewById(R.id.btnAdd);

        // btnAdd OnClickListner
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });
    }

    private void startPosting() {
        String woord_val = mWoord.getText().toString().trim();
        String vertaling_val = mVertaling.getText().toString().trim();

        // validate if the input fields are not empty
        if(!TextUtils.isEmpty(woord_val) && !TextUtils.isEmpty(vertaling_val)){

            // Push generates unique child id in db "woorden"
            DatabaseReference newWoord = mDatabase.push();

            newWoord.child("woord").setValue(woord_val);
            newWoord.child("vertaling").setValue(vertaling_val);
            newWoord.child("likes").setValue(0);

            startActivity(new Intent(PostActivity.this, MainActivity.class));


        } else{

            Toast.makeText(this, "Toevoegen mislukt! Gelieve alle velden in te vullen", Toast.LENGTH_SHORT).show();

        }

    }
}
