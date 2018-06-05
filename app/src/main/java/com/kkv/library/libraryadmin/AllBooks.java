package com.kkv.library.libraryadmin;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class AllBooks extends AppCompatActivity {
private ActionBar toolbar;
    ArrayList<String> books;
    ListView lv;
    ListAdapter la;
    DataSnapshot ddd;
    StringBuffer sb;
    DatabaseReference book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);
        toolbar = getSupportActionBar();
        toolbar.setTitle("All Books");
        try{
            book = FirebaseDatabase.getInstance().getReference("Books");
            lv= (ListView) findViewById(R.id.lvb);
            books=new ArrayList<>();
            la=new ArrayAdapter<>(AllBooks.this,android.R.layout.simple_list_item_1,books);
            retrieve();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    sb = new StringBuffer();
                    int i=0;
                    for (DataSnapshot ds : ddd.getChildren()) {
                        if(i==pos){
                            Book b=ds.getValue(Book.class);
                            sb.append("Book Name:"+b.bookname+"\n");
                            sb.append("Author Name:"+b.authername+"\n");
                            sb.append("No. Of Copies Available:"+b.count+"\n");
                            sb.append("Id:"+b.id+"\n");
                            break;
                        }
                        i++;
                    }
                    AlertDialog.Builder bbb=new AlertDialog.Builder(AllBooks.this);
                    bbb.setCancelable(true);
                    bbb.setTitle("Book Details:");
                    bbb.setMessage(sb.toString());
                    bbb.show();
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void fetchData(DataSnapshot dataSnapshot)
    {
        books.clear();
        ddd=dataSnapshot;
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Book b=ds.getValue(Book.class);
            books.add(b.bookname);
        }
    }
    public void retrieve() {
        book.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ddd=dataSnapshot;
                fetchData(dataSnapshot);
                lv.setAdapter(la);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
