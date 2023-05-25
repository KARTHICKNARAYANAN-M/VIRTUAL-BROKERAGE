package com.example.onlinebrokerage;

import static com.example.onlinebrokerage.registerpage.TAG;

import android.Manifest.permission;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class postpropertypage extends AppCompatActivity {
   RecyclerView recyclerView;
   TextView textView;
   int k=0,p=0,i;
   int c=0,upload_count;
   Button upload,fupload;
   String userID;
    Button post;
    private ProgressDialog progressDialog;
    FirebaseAuth aurth;
    FirebaseUser user;
    FirebaseFirestore fstore;
    String image[]=new String[10];




String useremail;


    private StorageReference mStorage;


    ArrayList<Uri> uris=new ArrayList<>();
    String arr[]=new String[100];
   RecyclerAdapter adapter;
   private Uri ImageUri;


   private static final int READ_PERMISSION=101;
   EditText name,email,phoneno,whatsappno,address,area,city,state,advanceamount,rentfee;
   RadioButton Family,Bachelor,Apartment,Independenthouse,Furnished,Semifurnished,Notfurnished,Below5000,Above5000,Above10000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpropertypage);


        recyclerView=findViewById(R.id.gallery_images);
        upload=findViewById(R.id.uploadphotos);
        adapter=new RecyclerAdapter(uris);
        recyclerView.setAdapter(adapter);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Image Uploading");
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phoneno=findViewById(R.id.phoneno);
        whatsappno=findViewById(R.id.whatsappno);
        address=findViewById(R.id.address);
        area=findViewById(R.id.area);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        advanceamount=findViewById(R.id.advanceamount);
        aurth = FirebaseAuth.getInstance();
        user = aurth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        Family=findViewById(R.id.Family);
        Bachelor=findViewById(R.id.Bachelor);
        Apartment=findViewById(R.id.Apartment);
        Independenthouse=findViewById(R.id.Independenthouse);
        Furnished=findViewById(R.id.Furnished);
        Semifurnished=findViewById(R.id.SemiFurnished);
        Notfurnished=findViewById(R.id.NotFurnished);
        userID = aurth.getCurrentUser().getUid();
        rentfee=findViewById(R.id.rentfee);



        DocumentReference documentReference=fstore.collection("HOUSEOWNER").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                useremail=(value.getString("email"));
            }
        });



        recyclerView.setLayoutManager(new GridLayoutManager(postpropertypage.this,3));

        if(ContextCompat.checkSelfPermission(postpropertypage.this, permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(postpropertypage.this,new String[]{permission.READ_EXTERNAL_STORAGE},READ_PERMISSION);
        }

       post=findViewById(R.id.post);
        fupload=findViewById(R.id.upload);

        fupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadActivity();
            }
        });





        post.setOnClickListener(new View.OnClickListener() {
                   @Override




            public void onClick(View view) {
                       progressDialog.setCancelable(false);
                       progressDialog.setMessage("Please Wait while Posting YOur Property");
                       progressDialog.show();
                       p++;


                     String username=name.getText().toString();
                     String emailaddress=email.getText().toString();
                     String phonenum=phoneno.getText().toString();
                     String whatsappnum=whatsappno.getText().toString();
                       String addresses=address.getText().toString();
                       String areas=area.getText().toString();
                       String cities=city.getText().toString();
                       String states=state.getText().toString();
                       String advance=advanceamount.getText().toString();
                       String rentfees=rentfee.getText().toString();

                       DocumentReference documentreference = fstore.collection("RENTHOUSEOWNERDETAILS").document(name.getText().toString()+useremail);



                       progressDialog.dismiss();

                       if(username.isEmpty()) {
                           name.setError("Name cannot be empty");
                           Toast.makeText(postpropertypage.this,"Enter Your Name!",Toast.LENGTH_SHORT).show();

                       }
                       else if(emailaddress.isEmpty())
                       {
                           email.setError("Email cannot be empty");
                           Toast.makeText(postpropertypage.this, "Enter Your Email!", Toast.LENGTH_SHORT).show();
                       }

                       else if (phonenum.isEmpty()) {
                           phoneno.setError("PhoneNo cannot be empty");
                           Toast.makeText(postpropertypage.this, "Enter Your Phone No", Toast.LENGTH_SHORT).show();
                       }

                       else if (whatsappnum.isEmpty()) {
                               whatsappno.setError("Whatsapp cannot be empty");
                               Toast.makeText(postpropertypage.this, "Enter Your WhatsappNo!", Toast.LENGTH_SHORT).show();
                           }

                       else if(!Family.isChecked()&&!Bachelor.isChecked()) {
                           Family.setError("Please Choose One");
                           Toast.makeText(postpropertypage.this,"Please Choose Preferred Family Type!",Toast.LENGTH_SHORT).show();

                       }
                       else if(!Apartment.isChecked()&&!Independenthouse.isChecked())
                       {
                           Apartment.setError("Please Choose one");
                           Toast.makeText(postpropertypage.this,"Please Choose House Type",Toast.LENGTH_SHORT).show();

                       }

                       else if(!Furnished.isChecked()&&!Semifurnished.isChecked()&&!Notfurnished.isChecked())
                       {
                           Furnished.setError("Please Choose One");
                           Toast.makeText(postpropertypage.this,"Please Choose Furniture Type",Toast.LENGTH_SHORT).show();

                       }

                       else if(addresses.isEmpty()) {
                           name.setError("Address cannot be empty");
                           Toast.makeText(postpropertypage.this,"Enter Address!",Toast.LENGTH_SHORT).show();

                       }


                      else if(areas.isEmpty()) {
                           name.setError("Area cannot be empty");
                           Toast.makeText(postpropertypage.this,"Enter Area!",Toast.LENGTH_SHORT).show();

                       }

                       else if(cities.isEmpty()) {
                           name.setError("City cannot be empty");
                           Toast.makeText(postpropertypage.this,"Enter Your City!",Toast.LENGTH_SHORT).show();

                       }

                       else if(states.isEmpty()) {
                           name.setError("State cannot be empty");
                           Toast.makeText(postpropertypage.this,"Enter Your State!",Toast.LENGTH_SHORT).show();

                       }
                       else if(advance.isEmpty()) {
                           name.setError("Advance amount cannot be empty");
                           Toast.makeText(postpropertypage.this,"Please Choose Advance Amount!",Toast.LENGTH_SHORT).show();

                       }
                       else if(rentfees.isEmpty())
                       {
                           rentfee.setError("Please Fill The Rent Fee");
                           Toast.makeText(postpropertypage.this,"Please Choose Rent Fee!",Toast.LENGTH_SHORT).show();


                       }
                       else if(!useremail.equals(emailaddress))
                       {
                           email.setError("Please Enter Your Registered Mail");
                           Toast.makeText(postpropertypage.this,"Please Enter Your Registered Mail",Toast.LENGTH_SHORT).show();


                       }






                       else{

                           progressDialog.setMessage("Please wait while Posting Your Property");
                           progressDialog.setTitle("Property Posting");
                           progressDialog.setCanceledOnTouchOutside(false);
                           progressDialog.show();






                           Map<String, Object> hashmap = new HashMap<>();
                           hashmap.put("UserID",userID+p);
                           hashmap.put("HouseOwnerName", username);
                           hashmap.put("EmailAddress", emailaddress);
                           hashmap.put("PhoneNo", phonenum);
                           hashmap.put("WhatsappNo", whatsappnum);
                           if(Family.isChecked())
                               hashmap.put("PreferredFamilyType",Family.getText());
                           if(Bachelor.isChecked())
                               hashmap.put("PreferredFamilyType",Bachelor.getText());
                           if(Apartment.isChecked())
                               hashmap.put("House Type",Apartment.getText());
                           if(Independenthouse.isChecked())
                               hashmap.put("HouseType",Independenthouse.getText());
                           if(Furnished.isChecked())
                               hashmap.put("FurnitureType",Furnished.getText());
                           if(Semifurnished.isChecked())
                               hashmap.put("FurnitureType",Semifurnished.getText());
                           if(Notfurnished.isChecked())
                               hashmap.put("FurnitureType",Notfurnished.getText());


                           String interest="No one is interested in the property";

                           hashmap.put("Address", addresses);
                           hashmap.put("Area", areas);
                           hashmap.put("City", cities);
                           hashmap.put("State", states);
                           hashmap.put("AdvanceAmount", advance);
                           hashmap.put("Rentfee",rentfees);
                           hashmap.put("InterestedPerson",interest);



                          /* StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child( userID);
                           StorageReference a = ImageFolder.child("Property" + k);
                           DocumentReference d1=documentreference.collection("ImageUrl").document(userID);
                           Map<String,Object> hash1=new HashMap<>();
                           for(int i=0;i<uris.size();i++)
                           {
                               hash1.put("ImageUrl"+i,image[i]);

                           }
                           d1.set(hash1);*/




                               /*StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child( userID);
                               StorageReference a = ImageFolder.child("Property" + k);

                               for (upload_count = 0; upload_count < uris.size(); upload_count++) {
                                   Uri individualImage = uris.get(upload_count);
                                   final StorageReference ImageName = a.child("Image0");
                                   ImageName.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                       @Override
                                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                           if (taskSnapshot.getMetadata() != null) {
                                               if (taskSnapshot.getMetadata().getReference() != null) {
                                                   Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                   result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                       @Override
                                                       public void onSuccess(Uri uri) {
                                                           String imageUrl = uri.toString();
                                                           Toast.makeText(postpropertypage.this,imageUrl,Toast.LENGTH_SHORT).show();
                                                           hashmap.put("ImageUrl"+upload_count,imageUrl);
                                                           //createNewPost(imageUrl);
                                                       }
                                                   }).addOnFailureListener(new OnFailureListener() {
                                                       @Override
                                                       public void onFailure(@NonNull Exception e) {

                                                       }
                                                   });
                                               }
                                           }
                                       }
                                   });


                               }*/






                          documentreference.set(hashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Log.d(TAG, "onSuccess: Profile created for RENTPROPERTY" + userID);


                               }


                           });
                           Toast.makeText(postpropertypage.this,"Your Property Has Been Posted!",Toast.LENGTH_SHORT).show();
                           sendusertonextactivity();
                       }
                   }


               });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2)
                {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SELECT PICTURE"),1);

            }
        });



    }

    private void uploadActivity() {



        StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child(userID);
        StorageReference a = ImageFolder.child(name.getText().toString()+useremail);
        for (upload_count = 0; upload_count < uris.size(); upload_count++) {
            Uri individualImage = uris.get(upload_count);
            final StorageReference ImageName = a.child("Image"+upload_count);
            ImageName.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getMetadata() != null) {
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    image[upload_count]=imageUrl;
                                    String g=name.getText().toString();


                                    DocumentReference documentreference = fstore.collection("RENTHOUSEOWNERDETAILS").document(g+useremail);
                                    Map<String,Object>hash1=new HashMap<>();
                                    DocumentReference d1=documentreference.collection("ImageUrl").document();
                                    hash1.put("ImageUrl",imageUrl);
                                    d1.set(hash1);
                                    //createNewPost(imageUrl)
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    }

                }

            });


        }
        Toast.makeText(postpropertypage.this,"Your Photos Has Been Uploaded",Toast.LENGTH_SHORT).show();


    }


    private void sendusertonextactivity() {

        Intent i=new Intent(this,houseownerfrontpage.class);
        i.putExtra("Property",k);
        startActivity(i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&& resultCode== Activity.RESULT_OK)
        {
            if(data.getClipData()!=null)
            {
                int x=data.getClipData().getItemCount();


                for(int i=0;i<x;i++)
                {
                    uris.add(data.getClipData().getItemAt(i).getUri());
                }
                adapter.notifyDataSetChanged();
            }
            else if(data.getData()!=null)
            {
                Uri   imageURL=data.getData();
                uris.add(imageURL);
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child(userID);
        StorageReference a = ImageFolder.child(name.getText().toString()+useremail);
        a.delete();
        startActivity(new Intent(postpropertypage.this,houseownerfrontpage.class));


    }
}


