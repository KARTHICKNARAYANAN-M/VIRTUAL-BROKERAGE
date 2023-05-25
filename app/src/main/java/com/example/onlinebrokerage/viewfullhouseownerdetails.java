package com.example.onlinebrokerage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewfullhouseownerdetails extends AppCompatActivity {

FirebaseFirestore db;
TextView housename,houseemail,housephone,housewhatsappno,housefamilytype,houseaddress,housefurnituretype,housetype,houserentfee,houseadvance;
private RecyclerView recyclerView;
private ArrayList<model> list;
private Adapter adapter;
TextView houseinterested;

Button book;
FirebaseFirestore fstore;
    FirebaseAuth aurth;
    FirebaseUser user;
    String userID;
    String name,email,names,emails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfullhouseownerdetails);
        Intent intent=getIntent();
        String a=intent.getExtras().getString("name");
        String b=intent.getExtras().getString("emailadd");
        db=FirebaseFirestore.getInstance();
        housename=findViewById(R.id.housename);
        houseemail=findViewById(R.id.houseemail);
        housephone=findViewById(R.id.housephone);
        housewhatsappno=findViewById(R.id.housewhatsappno);
        housefamilytype=findViewById(R.id.housefamiltytype);
        houseaddress=findViewById(R.id.houseaddress);
        housefurnituretype=findViewById(R.id.housefurnituretype);
        housetype=findViewById(R.id.housetype);
        houserentfee=findViewById(R.id.houserentfee);
        houseadvance=findViewById(R.id.houseadvance);
        recyclerView=findViewById(R.id.recycle1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstore=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        adapter=new Adapter(this,list);
        recyclerView.setAdapter(adapter);
        aurth = FirebaseAuth.getInstance();
        user = aurth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        userID = aurth.getCurrentUser().getUid();
        book=findViewById(R.id.book);
        houseinterested=findViewById(R.id.houseinterestedperson);
        //book.setVisibility(View.GONE);




        db.collection("RENTHOUSEOWNERDETAILS").document(a+""+b).collection("ImageUrl").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document:task.getResult()){
                        model model1=document.toObject(model.class);
                        list.add(model1);
                        adapter.notifyDataSetChanged();


                    }
               }
            }
        });





        db.collection("HOUSEOWNER").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                 for(QueryDocumentSnapshot document:task.getResult())
                 {
                     String em=document.getString("email");
                     if(em.equals(b))
                          book.setVisibility(View.GONE);
                     else
                         book.setVisibility(View.VISIBLE);
                 }
            }
        });




        db.collection("RENTHOUSEOWNERDETAILS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String name=document.getString("HouseOwnerName");
                        String em=document.getString("EmailAddress");
                        if(a.equals(name)&& b.equals(em)) {

                            housename.setText(document.getString("HouseOwnerName"));
                            houseemail.setText(document.getString("EmailAddress"));
                            housephone.setText(document.getString("PhoneNo"));
                            housewhatsappno.setText(document.getString("WhatsappNo"));
                            housefamilytype.setText(document.getString("PreferredFamilyType"));
                            houseaddress.setText(document.getString("Address")+","+document.getString("Area")+","+document.getString("City")+","+document.getString("State"));
                            housefurnituretype.setText(document.getString("FurnitureType"));
                            housetype.setText(document.getString("HouseType"));
                            houserentfee.setText(document.getString("Rentfee"));
                            houseadvance.setText(document.getString("AdvanceAmount"));


                        }
                    }
                }

            }
        });


        DocumentReference documentreference = fstore.collection("BOOKINGDETAILS").document(a+""+b);
        documentreference.addSnapshotListener(viewfullhouseownerdetails.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                houseinterested.setText(value.getString("InterestPerson"));
            }
        });




        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference1=fstore.collection("CUSTOMER").document(userID);

                documentReference1.addSnapshotListener(viewfullhouseownerdetails.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        names=value.getString("username");
                        emails=value.getString("email");
                        DocumentReference documentreference = fstore.collection("BOOKINGDETAILS").document(a+""+b);
                        Map<String, Object> hashmap = new HashMap<>();
                        hashmap.put("InterestPerson",names+" Is Interested In This Property");
                        documentreference.set(hashmap);
                        documentreference.addSnapshotListener(viewfullhouseownerdetails.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                houseinterested.setText(value.getString("InterestPerson"));
                            }
                        });

                    }
                });


            }
        });

    }
}