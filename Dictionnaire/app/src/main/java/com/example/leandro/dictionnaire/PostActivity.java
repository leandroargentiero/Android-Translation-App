package com.example.leandro.dictionnaire;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostActivity extends AppCompatActivity {

    private EditText mWoord, mVertaling;
    private Button mBtnAdd;

    // Firebase Database reference
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUsers;

    // Firebase Database Authentication/Users
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    // Push notification set up
    NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Getting the current user
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        // Get root connection and create child node "woorden"
        mDatabase = FirebaseDatabase.getInstance().getReference().child("woorden");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("users").child(mCurrentUser.getUid());

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
        final String woord_val = mWoord.getText().toString().trim();
        final String vertaling_val = mVertaling.getText().toString().trim();
        builder = new NotificationCompat.Builder(this);

        // validate if the input fields are not empty
        if(!TextUtils.isEmpty(woord_val) && !TextUtils.isEmpty(vertaling_val)){

            // Push generates unique child id in db "woorden"
            final DatabaseReference newWoord = mDatabase.push();

            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newWoord.child("woord").setValue(woord_val);
                    newWoord.child("vertaling").setValue(vertaling_val);
                    newWoord.child("likes").setValue(0);
                    newWoord.child("studentID").setValue(mCurrentUser.getUid());
                    newWoord.child("studentnaam").setValue(dataSnapshot.child("name").getValue());

                    // Push notification
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle(dataSnapshot.child("name").getValue() + " voegde een nieuw woord toe");
                    builder.setContentText(woord_val + " werd aan de lijst toegevoegd.");
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(1, builder.build());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            startActivity(new Intent(PostActivity.this, MainActivity.class));


        } else{

            Toast.makeText(this, "Toevoegen mislukt! Gelieve alle velden in te vullen", Toast.LENGTH_SHORT).show();

        }
    }
}
