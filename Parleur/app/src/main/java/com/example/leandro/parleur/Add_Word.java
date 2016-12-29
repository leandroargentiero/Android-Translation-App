package com.example.leandro.parleur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leandro.parleur.Models.Word;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Word extends AppCompatActivity {
    //declaratie van de variabelen
    private Button btnAdd;
    private EditText txtWoord, txtVertaling;
    private int stemmen;
    private DatabaseReference mDatabaseReference;//locatie in db
    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAdd = (Button)findViewById(R.id.btnAdd); //Get button Add
        txtWoord = (EditText)findViewById(R.id.txtTest); //Get EditText Woord
        txtVertaling = (EditText)findViewById(R.id.txtVertaling); //Get EditText Vertaling
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(); //Get DB en locatie
        stemmen = 0;

        //btnAdd click event
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validatie bij toevoegen woord
                if(txtWoord.getText().toString().matches("") || txtVertaling.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Gelieve alle velden in te vullen.",
                            Toast.LENGTH_LONG).show();
                }else{
                    //nieuw Word object aanmaken en pushen naar de reference "woorden" in Firebase
                    word = new Word(txtWoord.getText().toString(), txtVertaling.getText().toString(), stemmen);
                    mDatabaseReference.child("woorden").push().setValue(word);

                    // Write & save to shared preferences file
                    final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putString("frans_woord", txtWoord.getText().toString());
                    editor.commit();

                    Toast.makeText(getApplication(),"Er werd een nieuw woord toegevoegd.",
                            Toast.LENGTH_LONG).show();

                    finish(); //sluit huidige activty en redirect naar MainActivty
                }

            }
        });
    }

    // Method voor het aanmaken en storen in shared preferences
    private void savePreferences(String key, String value){
        // Nieuwe shared preference aanmaken
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key, value);
        editor.apply();
    };

}
