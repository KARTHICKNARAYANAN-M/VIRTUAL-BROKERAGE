package com.example.onlinebrokerage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {



    Context context;
    FirebaseFirestore db;
    ArrayList <userdetails>userarraydetails;
    FirebaseAuth aurth;
    FirebaseUser user;
    String userID;
    String useremail;
    FirebaseFirestore fstore;

    public MyAdapter(Context context, ArrayList<userdetails> userarraydetails) {
        this.context = context;
        this.userarraydetails = userarraydetails;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        userdetails u=userarraydetails.get(position);
        holder.honame.setText(u.HouseOwnerName);
        holder.holocation.setText(u.City);
        holder.horentfee.setText(u.Rentfee);
        holder.hofamilytype.setText(u.PreferredFamilyType);
        holder.hoadvance.setText(u.AdvanceAmount);
        holder.hofurnituretype.setText(u.FurnitureType);
        holder.clicktoviewpropertydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=u.HouseOwnerName;
                String b=u.EmailAddress;
                Intent i=new Intent(context,viewfullhouseownerdetails.class);
                i.putExtra("name",a);
                i.putExtra("emailadd",b);
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return userarraydetails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView honame,holocation,horentfee,hofamilytype,hoadvance,hofurnituretype;
        Button clicktoviewpropertydetails,delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            honame=itemView.findViewById(R.id.honame);
            holocation=itemView.findViewById(R.id.holocation);
            horentfee=itemView.findViewById(R.id.horentfee);
            hofamilytype=itemView.findViewById(R.id.hofamiltytype);
            hoadvance=itemView.findViewById(R.id.hoadvance);
            hofurnituretype=itemView.findViewById(R.id.hofurniture);
            clicktoviewpropertydetails=itemView.findViewById(R.id.clicktoviewfulldetails);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
