package com.example.onlinebrokerage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class houseownerdetails extends AppCompatActivity {

    FirebaseFirestore db;
    TextView housename,houseemail,housephone,housewhatsappno,housefamilytype,houseaddress,housefurnituretype,housetype,houserentfee,houseadvance;
    private RecyclerView recyclerView;
    private ArrayList<model> list;
    private Adapter adapter;
    TextView houseinterested;
    FirebaseFirestore fstore;
    FirebaseAuth aurth;
    FirebaseUser user;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houseownerdetails);

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
        houseinterested=findViewById(R.id.houseinterestedperson);


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
                            houseinterested.setText(document.getString("Interested Person"));

                        }
                    }
                }

            }
        });
    }
}