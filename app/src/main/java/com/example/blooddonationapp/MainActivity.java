package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Adapter.useradapter;
import com.example.blooddonationapp.Model.users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;

   private NavigationView navigationView;

   private CircleImageView circleImageView;

   private TextView navigationfullname,navigationemail,navigationtype,navigationbloodgroup;

   private DatabaseReference databaseReference;

   private RecyclerView recyclerView;

   private ProgressBar progressBar;

   private List<users>usersList;

   private useradapter userAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.usertoolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Blood Donation Application");


        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationviewid);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        usersList = new ArrayList<>();
        userAdapter = new useradapter(MainActivity.this, usersList);

        recyclerView.setAdapter(userAdapter);

        DatabaseReference recyclerreference = FirebaseDatabase.getInstance().getReference().child("User details").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid());

        recyclerreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String useradptertype = snapshot.child("type").getValue().toString();
//                if (useradptertype.equals("donor")) {
//                    readRecipients();
//                } else {
//                    readDonors();
//                }
//            }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        circleImageView =  navigationView.getHeaderView(0).findViewById(R.id.imagecircleview);
        navigationfullname=  navigationView.getHeaderView(0).findViewById(R.id.userfullname);
        navigationemail =  navigationView.getHeaderView(0).findViewById(R.id.useremailid);
        navigationtype =  navigationView.getHeaderView(0).findViewById(R.id.donortype);
        navigationbloodgroup =  navigationView.getHeaderView(0).findViewById(R.id.userbloodgroup);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("User details").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
           if (snapshot.exists())
           {
               String name = snapshot.child("name").getValue().toString();
               navigationfullname.setText(name);

               String email = snapshot.child("emailid").getValue().toString();
               navigationemail.setText(email);

               String donortype= snapshot.child("type").getValue().toString();
               navigationtype.setText(donortype);

               String bloodgroup = snapshot.child("bloodgroup").getValue().toString();
               navigationbloodgroup.setText(bloodgroup);


           }
           }


           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId())
               {
                   case R.id.menuprofile:
                   Intent intent = new Intent(MainActivity.this,profileactivity.class);
                   startActivity(intent);
                   finish();
                   return  true;

                   case R.id.logout:
                       FirebaseAuth.getInstance().signOut();
                       Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
                       startActivity(intent1);
                       break;
               }

               return true;
           }
       });


    }


    private void readDonors() {

        DatabaseReference readreference = FirebaseDatabase.getInstance().getReference().child("User details");
        Query query = readreference.orderByChild("type").equalTo("donor");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usersList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    users readuser = dataSnapshot.getValue(users.class);
                    usersList.add(readuser);

                }

                userAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                if (usersList.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "No recipient present", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readRecipients() {
        DatabaseReference readreference = FirebaseDatabase.getInstance().getReference().child("User details");
        Query query = readreference.orderByChild("type").equalTo("recipient");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usersList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    users readuser = dataSnapshot.getValue(users.class);
                    usersList.add(readuser);

                }

                userAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                if (usersList.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "No recipient present", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}