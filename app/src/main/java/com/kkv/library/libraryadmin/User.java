package com.kkv.library.libraryadmin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User extends AppCompatActivity implements View.OnClickListener{
    private ActionBar toolbar;
    Button s;
    EditText et;
    ListView lv;
    MyAdapterUser a;
    String sb;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = getSupportActionBar();
        toolbar.setTitle("All Users");
        s = (Button) findViewById(R.id.bbusearch);
        lv= (ListView) findViewById(R.id.userlist);
        et = (EditText) findViewById(R.id.username);
        s.setOnClickListener(this);
        a=new MyAdapterUser(this,"");
        a.notifyDataSetChanged();
        lv.setAdapter(a);
        a.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                sb=  a.getItem(pos).toString();
                i=new Intent(User.this,DisplayUser.class);
                i.putExtra("uname",sb);
                startActivity(i);

            }
        });
        a.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        a=new MyAdapterUser(this,et.getText().toString());
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != this.getCurrentFocus())
            imm.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
        a.notifyDataSetChanged();
        lv.setAdapter(a);
        a.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                sb=  a.getItem(pos).toString();
                i=new Intent(User.this,DisplayUser.class);
                i.putExtra("uname",sb);
                startActivity(i);

            }
        });
        a.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(User.this,Home.class);
        startActivity(i);
    }
}
