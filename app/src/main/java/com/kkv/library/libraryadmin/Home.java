package com.kkv.library.libraryadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity implements View.OnClickListener{

    Button adbook,allbook,profile,searchb,issueb,usersinlib;
    String uname;
    TextView u;
    private FirebaseAuth auth;
    Intent intent;
    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_home);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Home");
        uname=auth.getCurrentUser().getEmail();
        u= (TextView) findViewById(R.id.funame);
        u.setText(u.getText().toString()+uname+"!");
        adbook=findViewById(R.id.faddbook);
        adbook.setOnClickListener(this);
        searchb=findViewById(R.id.searchb);
        searchb.setOnClickListener(this);
        issueb=findViewById(R.id.issueb);
        issueb.setOnClickListener(this);
        usersinlib=findViewById(R.id.user);
        usersinlib.setOnClickListener(this);
        allbook=findViewById(R.id.fdisplayallbooks);
        allbook.setOnClickListener(this);
        profile=findViewById(R.id.fsprofile);
        profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.faddbook:
                intent = new Intent(Home.this, AddBook.class);
                startActivity(intent);
                break;
            case R.id.fdisplayallbooks:
                intent = new Intent(Home.this, AllBooks.class);
                startActivity(intent);
                break;
            case R.id.fsprofile:
                intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
                break;
            case R.id.searchb:
                intent = new Intent(Home.this, SearchBook.class);
                startActivity(intent);
                break;
            case R.id.issueb:
                intent = new Intent(Home.this, IssueBook.class);
                startActivity(intent);
                break;
            case R.id.user:
                intent = new Intent(Home.this, User.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        auth.signOut();
                        Intent i=new Intent(Home.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
