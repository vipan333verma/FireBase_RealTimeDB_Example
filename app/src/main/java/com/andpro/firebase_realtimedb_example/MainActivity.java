package com.andpro.firebase_realtimedb_example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference();
    DatabaseReference mDatabase  = FirebaseDatabase.getInstance().getReference("UserLocal");
    String uID = mDatabase.push().getKey();

    EditText fname,lname,email,password,confirmpassword,contact;
    String fnameIn,lnameIn,emailIn,passwordIn,confirmpasswordIn,contactIn;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialization
        init();



    }

    private void setData() {

        if(uID != null)
        {
            mDatabase.child(uID).child("fName").setValue(fnameIn);
            mDatabase.child(uID).child("lName").setValue(lnameIn);
            mDatabase.child(uID).child("email").setValue(emailIn);
            mDatabase.child(uID).child("password").setValue(passwordIn);
            mDatabase.child(uID).child("confirm").setValue(confirmpasswordIn);
            mDatabase.child(uID).child("contact").setValue(contactIn);

            Toast.makeText(getApplicationContext(),"successfully registered",Toast.LENGTH_LONG).show();
            Intent intent =new Intent(MainActivity.this,LoginActivity.class);
            intent.putExtra("uId",uID);
            startActivity(intent);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"something is wrong",Toast.LENGTH_LONG).show();
        }



    }

    private void firebaseActivity() {

        reference.child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             String emailcheck= String.valueOf(dataSnapshot.child("email").getValue());
                if(emailcheck.equals(emailIn))
                {
                    Toast.makeText(getApplicationContext(),"This email is already in use",Toast.LENGTH_LONG).show();
                }else
                {
                    setData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String ee=databaseError.getMessage();
                Log.d("dd",ee);

            }
        });
    }

    private void getInputs() {
        fnameIn=fname.getText().toString().trim();
        lnameIn=lname.getText().toString().trim();
        emailIn=email.getText().toString().trim();
        passwordIn=password.getText().toString().trim();
        confirmpasswordIn=confirmpassword.getText().toString().trim();
        contactIn=contact.getText().toString().trim();

    }

    private void init() {
        fname=findViewById(R.id.editText3);
        lname=findViewById(R.id.editText4);
        email=findViewById(R.id.editText5);
        password=findViewById(R.id.editText6);
        confirmpassword=findViewById(R.id.editText7);
        contact=findViewById(R.id.editText8);

        register=findViewById(R.id.button2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputs();
                firebaseActivity();

            }
        });
    }
}
