package com.kkv.library.libraryadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayUser extends AppCompatActivity {
static String uname;
static int finei;
DatabaseReference userd;
ListView lv;
MyAdapterBBooks a;
ArrayList<UserBooks> books;
StringBuffer sb;
TextView name;
    static TextView fine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        lv= (ListView) findViewById(R.id.uubooklist);
        books=new ArrayList<>();
        name=findViewById(R.id.uname);
        fine=findViewById(R.id.ufine);
        Intent intent = getIntent();
        uname=intent.getStringExtra("uname");
        userd= FirebaseDatabase.getInstance().getReference("Users").child(uname);
        name.setText(uname.toUpperCase()+"'s");
        a=new MyAdapterBBooks(this,uname);
        lv.setAdapter(a);
        a.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                sb = new StringBuffer();
                UserBooks b= (UserBooks) a.getItem(pos);
                sb.append("Id:"+b.id+"\n");
                sb.append("Issue Date:"+b.issuedate+"\n");
                sb.append("Renewal Date:"+b.renewdate+"\n");
                sb.append("Fine:"+b.fine+"\n");
                userd= FirebaseDatabase.getInstance().getReference("Users").child(uname).child(b.id);
                AlertDialog.Builder bbb=new AlertDialog.Builder(DisplayUser.this);
                bbb.setCancelable(true);
                bbb.setTitle("Did member returened this book?");
                bbb.setMessage(sb.toString());
                bbb.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userd.removeValue();
                    }

                });
                bbb.setNegativeButton("No", null);
                bbb.show();
            }
        });

        a.notifyDataSetChanged();
    }
    static public void updateFine(){
        fine.setText("Fine="+finei);
    }

    }
