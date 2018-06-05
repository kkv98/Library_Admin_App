package com.kkv.library.libraryadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login;
    EditText e1,e2;
    private FirebaseAuth auth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null) {
            ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()==true ) {
                startActivity(new Intent(Login.this, Home.class));
                finish();
            }
            else{
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.butmlogin);
        login.setOnClickListener(this);
        progressBar=findViewById(R.id.pb);
        e1=(EditText)findViewById(R.id.ev1);
        e2=(EditText)findViewById(R.id.ev2);
        auth = FirebaseAuth.getInstance();
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.butmlogin:
                String email = e1.getText().toString();
                final String password = e2.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        if (password.length() < 6) {
                                            Toast.makeText(Login.this, "Check Your Password", Toast.LENGTH_LONG).show();

                                        } else {
                                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(Login.this, Home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }
    
}
