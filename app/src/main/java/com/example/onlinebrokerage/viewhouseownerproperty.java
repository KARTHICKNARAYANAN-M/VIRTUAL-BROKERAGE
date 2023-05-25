package com.example.onlinebrokerage;


import static com.example.onlinebrokerage.registerpage.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.remote.WatchChange;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class viewhouseownerproperty extends AppCompatActivity {
    private StorageReference storageReference;
    int c=0;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore fstore;
    String email;
    TextView title;
    String userId;
    String useremail;
    StorageReference storageReferences;

   RecyclerView recyclerView;
   ArrayList<userdetails>  userdetailsArrayList;
   MyAdapter myAdapter;
   FirebaseFirestore db;
   ProgressDialog progressDialog;
   TextView noprop;
   int k=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewhouseownerproperty);
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
        progressDialog.show();
        title=findViewById(R.id.title);
        user= FirebaseAuth.getInstance().getCurrentUser();
        userId=user.getUid();
        auth=FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userId=auth.getCurrentUser().getUid();
        noprop=findViewById(R.id.noprop);


        DocumentReference documentReference=fstore.collection("HOUSEOWNER").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                title.setText(value.getString("username")+"\n"+"Here ! Is Your Posted Property Details");
                useremail=value.getString("email");

            }
        });



        EventChangeListener();






    }

    private void EventChangeListener() {

      /* db.collection("RENTHOUSEOWNERDETAILS").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {
                    if(progressDialog.isShowing())
                         progressDialog.dismiss();
                    Log.e("FireStore Error",error.getMessage());
                    return;
                }
                if(value.getString)
                progressDialog.dismiss();
                for(DocumentChange dc:value.getDocumentChanges())
                {
                    if(dc.getType()== DocumentChange.Type.ADDED)
                    {

                        userdetailsArrayList.add(dc.getDocument().toObject(userdetails.class));
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
 progressDialog.dismiss();
        db.collection("RENTHOUSEOWNERDETAILS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot document:task.getResult())
                    {
                        userdetails user=document.toObject(userdetails.class);
                        userdetailsArrayList.add(user);

                    }
                }
            }
        });*/
progressDialog.dismiss();
        db.collection("RENTHOUSEOWNERDETAILS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String email=document.getString("EmailAddress");
                        if(useremail.equals(email)) {

                            userdetailsArrayList.add(document.toObject(userdetails.class));
                            myAdapter.notifyDataSetChanged();
                            k=1;
                        }
                    }
                }

            }
        });

         if(k==0)
              noprop.setText("!!!!!You Have not posted any property!!!!!");

    }
}