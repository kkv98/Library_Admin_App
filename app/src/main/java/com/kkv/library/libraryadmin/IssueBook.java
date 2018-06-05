package com.kkv.library.libraryadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class IssueBook extends AppCompatActivity implements View.OnClickListener{
    Button s;
    EditText et;
    ListView lv;
    ArrayList<Book> books;
    MyAdapterBook a;
    StringBuffer sb;
    Book b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        s = (Button) findViewById(R.id.bbsearch);
        lv= (ListView) findViewById(R.id.booklist);
        et = (EditText) findViewById(R.id.bookname);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        s.setOnClickListener(this);
        books=new ArrayList<>();
        et = (EditText) findViewById(R.id.bookname);
        a=new MyAdapterBook(this);
        lv.setAdapter(a);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                sb = new StringBuffer();

                b= (Book) a.getItem(pos);
                sb.append("Book Name:"+b.bookname+"\n");
                sb.append("Author Name:"+b.authername+"\n");
                sb.append("No. Of Copies Available:"+b.count+"\n");
                sb.append("Id:"+b.id+"\n");
                new AlertDialog.Builder(IssueBook.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(b.bookname)
                        .setMessage("Are you sure you want to Issue this book?")
                        .setPositiveButton("Issue Book", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i=new Intent(IssueBook.this,SelectUser.class);
                                i.putExtra("book",b.id);
                                startActivity(i);
                            }

                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });


    }

    @Override
    public void onClick(View view) {
        a=new MyAdapterBook(this,et.getText().toString());
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != this.getCurrentFocus())
            imm.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
        lv.setAdapter(a);
        a.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                sb = new StringBuffer();

                b= (Book) a.getItem(pos);
                sb.append("Book Name:"+b.bookname+"\n");
                sb.append("Author Name:"+b.authername+"\n");
                sb.append("No. Of Copies Available:"+b.count+"\n");
                sb.append("Id:"+b.id+"\n");
                new AlertDialog.Builder(IssueBook.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(b.bookname)
                        .setMessage("Are you sure you want to Issue this book?")
                        .setPositiveButton("Issue Book", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               Intent i=new Intent(IssueBook.this,SelectUser.class);
                               i.putExtra("book",b.id);
                               startActivity(i);
                            }

                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        a.notifyDataSetChanged();
    }
}
