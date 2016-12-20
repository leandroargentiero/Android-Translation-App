package com.example.leandro.parleur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add extends AppCompatActivity {
    //declaratie van de variabelen
    private Button btnAdd;
    private EditText txtWoord, txtVertaling;
    private DatabaseReference mDatabaseReference; //locatie in db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAdd = (Button)findViewById(R.id.btnAdd); //Get button Add
        txtWoord = (EditText)findViewById(R.id.txtWoord); //Get EditText Woord
        txtVertaling = (EditText)findViewById(R.id.txtVertaling); //Get EditText Vertaling
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(); //Get DB en locatie

        //btnAdd click event
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validatie bij toevoegen woord
                if(txtWoord.getText().toString().matches("") || txtVertaling.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Gelieve alle velden in te vullen.",
                            Toast.LENGTH_LONG).show();
                }else{
                    Word word = new Word(txtWoord.getText().toString(), txtVertaling.getText().toString());
                    mDatabaseReference.child("woorden").push().setValue(word);
                    finish(); //sluit huidige activty en redirect naar MainActivty
                }

            }
        });


    }

}
