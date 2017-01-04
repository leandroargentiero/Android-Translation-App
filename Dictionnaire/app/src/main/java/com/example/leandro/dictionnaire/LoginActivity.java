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

public class LoginActivity extends AppCompatActivity {

    private EditText mLoginEmail, mLoginPaswoord;
    private Button mBtnLogin, mBtnNewRegistreer;

    private FirebaseAuth mAuth;

    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mProgress = new ProgressDialog(this);

        mLoginEmail = (EditText) findViewById(R.id.txtLoginEmail);
        mLoginPaswoord = (EditText) findViewById(R.id.txtLoginPaswoord);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnNewRegistreer = (Button) findViewById(R.id.btnNewRegistreer);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        mBtnNewRegistreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerIntent);
            }
        });

    }

    private void checkLogin() {
        String email = mLoginEmail.getText().toString().trim();
        String paswoord = mLoginPaswoord.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(paswoord)){

            // Progression dialog setup
            mProgress.setMessage("Bezig met in te loggen...");
            mProgress.show();

            mAuth.signInWithEmailAndPassword(email, paswoord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        mProgress.dismiss();

                        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);

                    }else{

                        mProgress.dismiss();
                        Toast.makeText(LoginActivity.this, "Login mislukt! Probeer nog eens.", Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }else{

            Toast.makeText(LoginActivity.this, "Login mislukt! Gelieve alle velden in te vullen.", Toast.LENGTH_SHORT).show();

        }

    }

}
