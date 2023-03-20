package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.Toolbar;
//import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileactivity extends AppCompatActivity {

    private TextView profiletextview,profileemailid,profiletype,profilephonenumber,profilebloodgroup,profileaddress;
    private CircleImageView profilecircleview;
    private Button profilebackbutton;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);

       toolbar= findViewById(R.id.profiletoolbar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        profiletextview =findViewById(R.id.profiletextview);
        profileemailid = findViewById(R.id.profileemailid);
        profiletype= findViewById(R.id.profiletype);
        profilephonenumber=findViewById(R.id.profilephonenumber);
        profilebloodgroup=findViewById(R.id.profilebloodgroup);
        profileaddress=findViewById(R.id.profileaddress);
        profilecircleview= findViewById(R.id.profileactivityimage);
        profilebackbutton= findViewById(R.id.profilebackbutton);



//
//
//
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DatabaseReference profilereference = FirebaseDatabase.getInstance().getReference().child("User details").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        profilereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    profiletextview.setText(snapshot.child("name").getValue().toString());
                    profileemailid.setText(snapshot.child("emailid").getValue().toString());
                    profilephonenumber.setText(snapshot.child("phonenumber").getValue().toString());
                    profileaddress.setText(snapshot.child("address").getValue().toString());
                    profiletype.setText(snapshot.child("type").getValue().toString());
                    profilebloodgroup.setText(snapshot.child("bloodgroup").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

              profilebackbutton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent = new Intent(profileactivity.this,MainActivity.class);
                      startActivity(intent);
                      finish();

                  }
              });


    }


}