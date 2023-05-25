package com.example.onlinebrokerage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class customerdatafetch extends AppCompatActivity {

    FirebaseAuth aurth;
    FirebaseUser user;
    int k=0;
    FirebaseFirestore fstore;
    EditText loc;
    Button search;
    TextView name,houseownername,emails,phone,whatsapp,preferfamilytype,housetype,furniture,address,area,city,state,advance,rentfee;
    EditText rate,bachelorfamily,areas;
    DocumentReference documentReference1;
    Spinner spinners,spinner1,spinner2;
    String []familytype={"None","Family","Bachelor"};
    String []propertytype={"None","Apartment","IndependentHouse"};
    String []furnitures={"None","SemiFurnished","Not Furnished","Furnished"};


    RecyclerView recyclerView;
    ArrayList<userdetails> userdetailsArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    EditText location,rentalot,adv;
    Button searches;

    TextView nop;
    String proptype;
    String fam;
    String furtype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerdatafetch);

        aurth = FirebaseAuth.getInstance();
        user = aurth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        fstore = FirebaseFirestore.getInstance();
        spinners=findViewById(R.id.spinner);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>(customerdatafetch.this, R.layout.drop_file,familytype);
        arrayAdapter.setDropDownViewResource(R.layout.drop_file);
        ArrayAdapter <String> arrayAdapter1=new ArrayAdapter<>(customerdatafetch.this,R.layout.drop_file,propertytype);

        arrayAdapter1.setDropDownViewResource(R.layout.drop_file);

        ArrayAdapter <String> arrayAdapter2=new ArrayAdapter<>(customerdatafetch.this,R.layout.drop_file,furnitures);

        arrayAdapter1.setDropDownViewResource(R.layout.drop_file);

        spinners.setAdapter(arrayAdapter);
        spinner1.setAdapter(arrayAdapter1);
        spinner2.setAdapter(arrayAdapter2);


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        userdetailsArrayList=new ArrayList<userdetails>();
        myAdapter=new MyAdapter(this,userdetailsArrayList);
        recyclerView.setAdapter(myAdapter);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        location=findViewById(R.id.location);
        rentalot=findViewById(R.id.rentallot);
        adv=findViewById(R.id.adv);
        searches=findViewById(R.id.searches);
        nop=findViewById(R.id.noprop);
        area=findViewById(R.id.area);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                proptype=propertytype[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fam=familytype[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                furtype=furnitures[i].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        searches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userdetailsArrayList.clear();
                EventChangeListener();



            }
        });







    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        proptype=propertytype[position].toString();
        fam=familytype[position].toString();


        Toast.makeText(getApplicationContext(), "Selected User: " + propertytype[position], Toast.LENGTH_SHORT).show();
    }

    private void EventChangeListener() {


        progressDialog.dismiss();
        db.collection("RENTHOUSEOWNERDETAILS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {

                   String lo=location.getText().toString();
                   String l=lo.toLowerCase();
                   String adva=adv.getText().toString();
                   String rent=rentalot.getText().toString();
                   String ar=area.getText().toString().toLowerCase();


                   if(lo.isEmpty()&&adva.isEmpty()&&rent.isEmpty() &&fam.equals("None")&&proptype.equals("None")&&furtype.equals("None")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           //String dloc=document.getString("City").toLowerCase();
                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;

                       }
                   }

                   else if(!lo.isEmpty()&&adva.isEmpty()&&rent.isEmpty() &&fam.equals("None")&&proptype.equals("None")&&furtype.equals("None")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                          // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           if(dloc.equals(l)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }
                   }
                   else if(!lo.isEmpty()&&adva.isEmpty()&&!rent.isEmpty() &&fam.equals("None")&&proptype.equals("None")&&furtype.equals("None")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }
                   }

                   else if(!lo.isEmpty()&&!adva.isEmpty()&&!rent.isEmpty() &&fam.equals("None")&&proptype.equals("None")&&furtype.equals("None")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           int da=Integer.parseInt(document.getString("AdvanceAmount"));
                           int av=Integer.parseInt(adva);
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000 &&da>=av-2000&&da<=av+2000) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }
                   }

                   else if(!lo.isEmpty()&&!adva.isEmpty()&&!rent.isEmpty() &&fam.equals("Family")&&proptype.equals("None")&&furtype.equals("None")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           int da=Integer.parseInt(document.getString("AdvanceAmount"));
                           int av=Integer.parseInt(adva);
                           String f=document.getString("PreferredFamilyType");
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000 &&da>=av-2000&&da<=av+2000&&fam.equals(f)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }
                   }

                   else if(!lo.isEmpty()&&!adva.isEmpty()&&!rent.isEmpty() &&fam.equals("Bachelor")&&proptype.equals("None")&&furtype.equals("None")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           int da=Integer.parseInt(document.getString("AdvanceAmount"));
                           int av=Integer.parseInt(adva);
                           String f=document.getString("PreferredFamilyType");
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000 &&da>=av-2000&&da<=av+2000&&fam.equals(f)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }



                   }
                   else if(!lo.isEmpty()&&!adva.isEmpty()&&!rent.isEmpty() &&fam.equals("Bachelor")&&proptype.equals("IndependentHouse")&&furtype.equals("None")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           int da=Integer.parseInt(document.getString("AdvanceAmount"));
                           int av=Integer.parseInt(adva);
                           String f=document.getString("PreferredFamilyType");
                           String p=document.getString("HouseType");
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000 &&da>=av-2000&&da<=av+2000&&fam.equals(f)&&proptype.equals(p)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }



                   }

                   else if(!lo.isEmpty()&&!adva.isEmpty()&&!rent.isEmpty() &&fam.equals("Bachelor")&&proptype.equals("Apartment")&&furtype.equals("None")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           int da=Integer.parseInt(document.getString("AdvanceAmount"));
                           int av=Integer.parseInt(adva);
                           String f=document.getString("PreferredFamilyType");
                           String p=document.getString("HouseType");
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000 &&da>=av-2000&&da<=av+2000&&fam.equals(f)&&proptype.equals(p)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }



                   }
                   else if(!lo.isEmpty()&&!adva.isEmpty()&&!rent.isEmpty() &&fam.equals("Bachelor")&&proptype.equals("Apartment")&&furtype.equals("Furnished")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           int da=Integer.parseInt(document.getString("AdvanceAmount"));
                           int av=Integer.parseInt(adva);
                           String f=document.getString("PreferredFamilyType");
                           String p=document.getString("HouseType");
                           String fu=document.getString("FurnitureType");
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000 &&da>=av-2000&&da<=av+2000&&fam.equals(f)&&proptype.equals(p)&&furtype.equals(fu)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }



                   }


                   else if(!lo.isEmpty()&&!adva.isEmpty()&&!rent.isEmpty() &&fam.equals("Bachelor")&&proptype.equals("Apartment")&&furtype.equals("Not Furnished")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           int da=Integer.parseInt(document.getString("AdvanceAmount"));
                           int av=Integer.parseInt(adva);
                           String f=document.getString("PreferredFamilyType");
                           String p=document.getString("HouseType");
                           String fu=document.getString("FurnitureType");
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000 &&da>=av-2000&&da<=av+2000&&fam.equals(f)&&proptype.equals(p)&&furtype.equals(fu)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }



                   }


                   else if(!lo.isEmpty()&&!adva.isEmpty()&&!rent.isEmpty() &&fam.equals("Bachelor")&&proptype.equals("Apartment")&&furtype.equals("SemiFurnished")) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           int da=Integer.parseInt(document.getString("AdvanceAmount"));
                           int av=Integer.parseInt(adva);
                           String f=document.getString("PreferredFamilyType");
                           String p=document.getString("HouseType");
                           String fu=document.getString("FurnitureType");
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000 &&da>=av-2000&&da<=av+2000&&fam.equals(f)&&proptype.equals(p)&&furtype.equals(fu)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }



                   }

                   else if(!lo.isEmpty()&&!adva.isEmpty()&&!rent.isEmpty() &&fam.equals("Bachelor")&&proptype.equals("Apartment")&&furtype.equals("SemiFurnished") && !ar.isEmpty()) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           int drent=Integer.parseInt(document.getString("Rentfee"));
                           int r=Integer.parseInt(rent);
                           int da=Integer.parseInt(document.getString("AdvanceAmount"));
                           int av=Integer.parseInt(adva);
                           String are=document.getString("Area").toLowerCase();
                           String f=document.getString("PreferredFamilyType");
                           String p=document.getString("HouseType");
                           String fu=document.getString("FurnitureType");
                           if(dloc.equals(l)&& drent>=r-2000&&drent<=r+2000 &&da>=av-2000&&da<=av+2000&&fam.equals(f)&&proptype.equals(p)&&furtype.equals(fu)&&ar.equals(are)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }



                   }

                   else if(!lo.isEmpty()&&adva.isEmpty()&&rent.isEmpty() &&fam.equals("None")&&proptype.equals("None")&&furtype.equals("None")&&!ar.isEmpty()) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           // String email = document.getString("EmailAddress")
                           String dloc=document.getString("City").toLowerCase();
                           String are=document.getString("Area").toLowerCase();
                           if(dloc.equals(l)&& ar.equals(are)) {

                               userdetailsArrayList.add(document.toObject(userdetails.class));
                               myAdapter.notifyDataSetChanged();
                               k = 1;
                           }
                       }
                   }


                if(userdetailsArrayList.size()==0)
                      nop.setText("!!! No Results Available !!!");



                }





            }



        });






    }
}