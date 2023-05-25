package com.example.onlinebrokerage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class customerloginpage extends AppCompatActivity {

    EditText emailaddress,pass;
    Button login;

    ProgressDialog progdialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth aurth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerloginpage);
        emailaddress = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.loginbutton);
        aurth=FirebaseAuth.getInstance();
        user=aurth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }

    private void performLogin() {


        String email=emailaddress.getText().toString();
        String password=pass.getText().toString();
        progdialog=new ProgressDialog(this);


        if(!email.matches(emailPattern) && (email.length() == 0))
        {
            emailaddress.setError("Enter  a Valid Email");
        }
        else if ((password.isEmpty()||password.length()<6))
        {
            pass.setError("Password must contain atleast 6 letters");
        }


        else {
            progdialog.setMessage("Please wait while Login");
            progdialog.setTitle("Login");
            progdialog.setCanceledOnTouchOutside(false);
            progdialog.show();

            aurth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progdialog.dismiss();
                        sendusertonextActivity();
                        Toast.makeText(customerloginpage.this,"LOGIN  SUCCESSFULL",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progdialog.dismiss();
                        Toast.makeText(customerloginpage.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendusertonextActivity() {
        Intent i=new Intent(customerloginpage.this,customerfrontpage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }



}