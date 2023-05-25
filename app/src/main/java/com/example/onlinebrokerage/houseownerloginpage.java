package com.example.onlinebrokerage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class houseownerloginpage extends AppCompatActivity {



    EditText emailaddress,pass;
    Button login;

    ProgressDialog progdialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth aurth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houseownerloginpage);

        emailaddress = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.loginbutton);
        aurth=FirebaseAuth.getInstance();
        user=aurth.getCurrentUser();
        pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

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
                        Toast.makeText(houseownerloginpage.this,"LOGIN  SUCCESSFULL",Toast.LENGTH_SHORT).show();
                        sendusertonextactivity();
                    }
                    else
                    {
                        progdialog.dismiss();
                        Toast.makeText(houseownerloginpage.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void sendusertonextactivity() {
        Intent i=new Intent(this,houseownerfrontpage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


}