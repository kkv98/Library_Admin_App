package com.kkv.library.libraryadmin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MyAdapterUser extends BaseAdapter {
    ArrayList<String> users;
    DatabaseReference user;
    DataSnapshot ddd;
    Context ca;
    String f;

    public MyAdapterUser(Context c, String find) {
        ca = c;
        f =find.toUpperCase() + ".*";
        user = FirebaseDatabase.getInstance().getReference("Users");
        users = new ArrayList<>();
        retrieve();
    }
    public MyAdapterUser(Context c) {
        ca=c;
        f=".*";
        user = FirebaseDatabase.getInstance().getReference("Users");
        users=new ArrayList<>();
        retrieve();
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) ca.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.userlist, viewGroup, false);
        TextView bname = row.findViewById(R.id.userlname);
        bname.setText(users.get(i));
        return row;
    }

    public void retrieve() {
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ddd = dataSnapshot;
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ca, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchData(DataSnapshot dataSnapshot) {
        try {
            users.clear();
            ddd = dataSnapshot;

            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                String b=ds.getKey();
                if (Pattern.matches(f, b.toUpperCase()))
                users.add(b);
                this.notifyDataSetChanged();
            }
        }catch (Exception e){

        }

    }
}
