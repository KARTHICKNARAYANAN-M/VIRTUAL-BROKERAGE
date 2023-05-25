package com.example.onlinebrokerage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

   private ArrayList<model>mlist;
   private Context context;

   public Adapter(Context context,ArrayList<model>mlist)
   {
       this.context=context;
       this.mlist=mlist;
   }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.imagelist,parent,false);


       return new Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(mlist.get(position).getImageUrl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
