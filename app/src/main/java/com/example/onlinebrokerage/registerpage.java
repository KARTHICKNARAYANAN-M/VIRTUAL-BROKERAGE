package com.example.onlinebrokerage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class registerpage extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText typeusername, emailaddress, typepassword, typerepassword;
    Button register,google;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progdialog;
    FirebaseAuth aurth;
    FirebaseUser user;

    RadioButton customer;
    RadioButton HouseOwner;


    FirebaseFirestore fstore;


    String userID;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);
        typeusername = findViewById(R.id.typeusername);
        emailaddress = findViewById(R.id.emailaddress);
        typepassword = findViewById(R.id.typepassword);
        typerepassword = findViewById(R.id.typerepassword);
        customer = findViewById(R.id.customer);
        HouseOwner = findViewById(R.id.HouseOwner);
        register = findViewById(R.id.register);
        aurth = FirebaseAuth.getInstance();
        user = aurth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        google=findViewById(R.id.google);
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(registerpage.this,registergoogle.class);
                startActivity(i);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuthentication();
            }
        });


    }

    private void performAuthentication() {
        String name = typeusername.getText().toString();
        String email = emailaddress.getText().toString();
        String password = typepassword.getText().toString();
        String repassword = typerepassword.getText().toString();

        progdialog = new ProgressDialog(this);


        if (!email.matches(emailPattern) && (email.length() == 0)) {
            emailaddress.setError("Enter  a Valid Email");
        } else if ((password.isEmpty() || password.length() < 6)) {
            typepassword.setError("Password must contain atleast 6 letters");
        } else if (!password.equals(repassword)) {
            typerepassword.setError("Password Does Not Match");
        } else if (name.isEmpty()) {
            typeusername.setError("Username Must Not Be Empty");
        } else if (!(customer.isChecked()) && !(HouseOwner.isChecked())) {
            Toast.makeText(registerpage.this, "Please Choose The User Type", Toast.LENGTH_SHORT).show();
        } else {
            progdialog.setMessage("Please wait while registration");
            progdialog.setTitle("Registration");
            progdialog.setCanceledOnTouchOutside(false);
            progdialog.show();

            if (HouseOwner.isChecked()) {
                aurth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task ->
                {
                    if (task.isSuccessful()) {

                        progdialog.dismiss();


                        userID = aurth.getCurrentUser().getUid();

                        DocumentReference documentreference = fstore.collection("HOUSEOWNER").document(userID);
                        Map<String, Object> houseownerdata = new HashMap<>();
                        houseownerdata.put("username", name);
                        houseownerdata.put("email", email);
                        houseownerdata.put("password", password);
                        Toast.makeText(registerpage.this,"PROFILE CREATED FOR HOUSEOWNER"+userID,Toast.LENGTH_SHORT).show();
                        Toast.makeText(registerpage.this,"PLEASE LOGIN TO CONTINUE.."+userID,Toast.LENGTH_SHORT).show();

                        documentreference.set(houseownerdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                                Log.d(TAG, "onSuccess: Profile created for HouseOwner Profile" + userID);
                            }
                        });

                        sendusertohouseowneractivity();


                    }
                    else
                        progdialog.dismiss();
                        Toast.makeText(registerpage.this,"Registration Failed"+task.getException(),Toast.LENGTH_SHORT).show();


                });

            } else if (customer.isChecked()) {
                aurth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task ->
                {
                    if (task.isSuccessful()) {

                        progdialog.dismiss();
                        userID = aurth.getCurrentUser().getUid();

                        DocumentReference documentreference = fstore.collection("CUSTOMER").document(userID);

                        Map<String, Object> customerdata = new HashMap<>();
                        customerdata.put("username", name);
                        customerdata.put("email", email);
                        customerdata.put("password", password);
                        Toast.makeText(registerpage.this,"PROFILE CREATED FOR CUSTOMER"+userID,Toast.LENGTH_SHORT).show();
                        Toast.makeText(registerpage.this,"PLEASE LOGIN INTO CONTINUE.."+userID,Toast.LENGTH_SHORT).show();


                        documentreference.set(customerdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: Profile created for Customer" + userID);


                            }


                        });

                        sendusertocustomeractivity();

                    }
                    else
                        progdialog.dismiss();
                        Toast.makeText(registerpage.this,"Registration Failed"+task.getException(),Toast.LENGTH_SHORT).show();

                });


            }
        }
    }

    private void sendusertohouseowneractivity() {
        Intent i = new Intent(registerpage.this, houseownerloginpage.class);
        startActivity(i);
    }


    private void sendusertocustomeractivity() {
        Intent i = new Intent(registerpage.this, customerloginpage.class);
        startActivity(i);
    }







}