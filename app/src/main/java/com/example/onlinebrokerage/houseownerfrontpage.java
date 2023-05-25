package com.example.onlinebrokerage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class houseownerfrontpage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button postproperty,viewproperty;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    DatabaseReference reference;
    String userId;
    TextView name;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houseownerfrontpage);

        drawerLayout =findViewById(R.id.drawer_layout);
       navigationView=findViewById(R.id.nav_view);
       toolbar=(Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       navigationView.bringToFront();
       ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
       drawerLayout.addDrawerListener(toggle);
       toggle.syncState();
       navigationView.setNavigationItemSelectedListener(this);

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("HOUSEOWNER");
        userId=user.getUid();
        name=findViewById(R.id.name);

        auth=FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();

        userId=auth.getCurrentUser().getUid();

        DocumentReference documentReference=fstore.collection("HOUSEOWNER").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("username"));
            }
        });

        postproperty=findViewById(R.id.postproperty);
        postproperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(houseownerfrontpage.this,typeofproperty.class));
            }
        });

        viewproperty=findViewById(R.id.viewproperty);
        viewproperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(houseownerfrontpage.this, viewhouseownerproperty.class);
                startActivity(i);



            }
        });



    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.logout:
            {
                startActivity(new Intent(this,frontpage.class));
                break;
            }
            case R.id.ProfileDetais:
            {
                break;

            }
        }
        return true;
    }
}