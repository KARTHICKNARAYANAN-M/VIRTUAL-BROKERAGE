package com.example.onlinebrokerage;


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

import android.os.Bundle;


public class frontpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);


 Button button1 = (Button) findViewById(R.id.customerlogin);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity1();
            }
        });


        Button button2 = (Button) findViewById(R.id.HouseOwner);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity2();
            }
        });

        Button button3 = (Button) findViewById(R.id.signup);
        button3
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity3();
            }
        });

    }


    public void openNewActivity1(){
        Intent intent = new Intent(this, customerloginpage.class);
        startActivity(intent);
    }

    public void openNewActivity2(){
        Intent intent = new Intent(this, houseownerloginpage.class);
        startActivity(intent);
    }

    public void openNewActivity3(){
        Intent intent = new Intent(this, registerpage.class);
        startActivity(intent);
    }
}