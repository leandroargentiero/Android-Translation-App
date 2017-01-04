package com.example.leandro.dictionnaire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNaam, mEmail, mPaswoord;
    private Button mBtnRegistreer;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mProgress = new ProgressDialog(this);

        mNaam = (EditText) findViewById(R.id.txtNaam);
        mEmail = (EditText) findViewById(R.id.txtEmail);
        mPaswoord = (EditText) findViewById(R.id.txtPaswoord);
        mBtnRegistreer = (Button) findViewById(R.id.btnRegistreer);

        mBtnRegistreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

    }

    private void startRegister() {
        final String naam = mNaam.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String paswoord = mPaswoord.getText().toString().trim();

        // Validate input fields if NOT EMPTY
        if(!TextUtils.isEmpty(naam) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(paswoord)){

            // Progression dialog setup
            mProgress.setMessage("Bezig met registreren...");
            mProgress.show();


            mAuth.createUserWithEmailAndPassword(email, paswoord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // if registration task is succesful then do...
                    if(task.isSuccessful()){
                        // Storing username also in db root->users->key->username...
                        String user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference current_user_db = mDatabase.child(user_id);

                        current_user_db.child("name").setValue(naam);

                        mProgress.dismiss();

                        // redirect user to main acitivity
                        Intent proceedIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        proceedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // user cannot go back
                        startActivity(proceedIntent);
                    }

                }
            });

        }else{

            Toast.makeText(this, "Registreren mislut! Gelieve alle velden in te vullen", Toast.LENGTH_SHORT).show();


        }
    }
}
