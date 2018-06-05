package com.kkv.library.libraryadmin;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBook extends AppCompatActivity {

    EditText e1,e2,e3,e4;
    Button b;
    DatabaseReference book;
    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Add Books");
        e1= (EditText) findViewById(R.id.fbookname);
        e2= (EditText) findViewById(R.id.fauthorname);
        e3=(EditText) findViewById(R.id.fnoofcopy);
        e4=(EditText) findViewById(R.id.fbookid);
        b= (Button) findViewById(R.id.fadd);
        book = FirebaseDatabase.getInstance().getReference();
    }
    public void adddd(View view)
    {
        if(req(e1)&&req(e2)&&req(e3)&&req(e4)) {
            book.child("Books").child(e4.getText().toString())
                    .setValue(new Book(e1.getText().toString(),
                            e2.getText().toString(),
                            e3.getText().toString(),
                            e4.getText().toString()));

            e1.setText("");
            e2.setText("");
            e3.setText("");
            e4.setText("");
            Toast.makeText(AddBook.this,"Book Added",Toast.LENGTH_LONG).show();
        }
    }
    public boolean req(EditText ee)
    {
        if( TextUtils.isEmpty(ee.getText())){

            ee.setError( "This field is Required" );
            return false;

        }
        return true;
    }
}
