package com.kkv.library.libraryadmin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelectUser extends AppCompatActivity implements View.OnClickListener{
    private ActionBar toolbar;
    Button s;
    EditText et;
    ListView lv;
    MyAdapterUser a;
    Book temp;
    String curdateval,curdate,rewdate;
    Users te;
    DatabaseReference book,curuser,root,modbook;
    String t,u;
    int icount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        root=FirebaseDatabase.getInstance().getReference("Date");
        root.setValue(ServerValue.TIMESTAMP);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        Intent i=getIntent();
        t=  i.getStringExtra("book");
        toolbar = getSupportActionBar();
        toolbar.setTitle("Select User");
        s = (Button) findViewById(R.id.bbusearch);
        lv= (ListView) findViewById(R.id.userlist);
        et = (EditText) findViewById(R.id.username);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        s.setOnClickListener(this);
        a=new MyAdapterUser(this);
        lv.setAdapter(a);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                u = (String) a.getItem(pos);
                book = FirebaseDatabase.getInstance().getReference("Books").child(t);
                modbook = FirebaseDatabase.getInstance().getReference();
                //curuser = FirebaseDatabase.getInstance().getReference("Users").child(u).child("BorrowedBooks");
                curuser = FirebaseDatabase.getInstance().getReference("Users").child(u);
                book.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        temp = dataSnapshot.getValue(Book.class);
                        /*Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(ServerValue.TIMESTAMP.);
                        SimpleDateFormat fmt = new SimpleDateFormat("dd MM yyyy");
                        fmt.format(cal.getTime());*/
                        root.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                curdateval = dataSnapshot.getValue().toString();
                                // Toast.makeText(SelectUser.this,curdateval, Toast.LENGTH_SHORT).show();
                                long i = Long.parseLong(curdateval);
                                SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
                                Date cu = new Date(i);
                                curdate = sfd.format(cu);
                                Calendar c = Calendar.getInstance();
                                try {
                                    c.setTime(sfd.parse(curdate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                c.add(Calendar.DATE, 5);
                                rewdate = sfd.format(c.getTime());
                                icount=Integer.parseInt(temp.count);
                                if(icount>0) {
                                    final Boolean[] ch = new Boolean[1];
                                    curuser.addListenerForSingleValueEvent(
                                            new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    ch[0] =dataSnapshot.hasChild(temp.id);
                                                    if(!ch[0])
                                                    {
                                                        temp.count = "" + (icount-1);
                                                        modbook.child("Books").child(temp.id)
                                                                .setValue(temp);
                                                        Toast.makeText(SelectUser.this, temp.bookname + " is Successfully Issued.", Toast.LENGTH_SHORT).show();

                                                    }
                                                    else {
                                                        Toast.makeText(SelectUser.this, temp.bookname + " is Successfully Renewed.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            }
                                    );

                                    curuser.child(temp.id).setValue(new UserBooks(temp.id, curdate, rewdate, 0));


                                }else {
                                    Toast.makeText(SelectUser.this, temp.bookname + " is Not Available In Library NOW.", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //curuser.setValue(new UserBooks());



            }
        });

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

                    u = (String) a.getItem(pos);
                    book = FirebaseDatabase.getInstance().getReference("Books").child(t);
                    modbook = FirebaseDatabase.getInstance().getReference();
                    //curuser = FirebaseDatabase.getInstance().getReference("Users").child(u).child("BorrowedBooks");
                curuser = FirebaseDatabase.getInstance().getReference("Users").child(u);
                book.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            temp = dataSnapshot.getValue(Book.class);
                        /*Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(ServerValue.TIMESTAMP.);
                        SimpleDateFormat fmt = new SimpleDateFormat("dd MM yyyy");
                        fmt.format(cal.getTime());*/
                            root.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    curdateval = dataSnapshot.getValue().toString();
                                    // Toast.makeText(SelectUser.this,curdateval, Toast.LENGTH_SHORT).show();
                                    long i = Long.parseLong(curdateval);
                                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
                                    Date cu = new Date(i);
                                    curdate = sfd.format(cu);
                                    Calendar c = Calendar.getInstance();
                                    try {
                                        c.setTime(sfd.parse(curdate));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    c.add(Calendar.DATE, 5);
                                    rewdate = sfd.format(c.getTime());
                                    int icount=Integer.parseInt(temp.count);
                                    if(icount>0) {
                                    curuser.child(temp.id).setValue(new UserBooks(temp.id, curdate, rewdate, 0));
                                        temp.count = "" + (icount-1);
                                        modbook.child("Books").child(temp.id)
                                                .setValue(temp);
                                        Toast.makeText(SelectUser.this, temp.bookname + " is Successfully Issued.", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(SelectUser.this, temp.bookname + " is Not Available In Library NOW.", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //curuser.setValue(new UserBooks());



            }
        });
        a.notifyDataSetChanged();
    }
}

