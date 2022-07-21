package com.example.perfectelectronics;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectelectronics.Model.Sellers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class selleradapter extends RecyclerView.Adapter<selleradapter.ViewHolder>{
    private int selleritemlayout;
    private ArrayList seller_id;
    DatabaseReference rootref;



    public selleradapter(int selleritemlayout, ArrayList seller_id) {
        this.selleritemlayout = selleritemlayout;
        this.seller_id = seller_id;
    }

    @NonNull
    @Override
    public selleradapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(selleritemlayout,parent,false);
        selleradapter.ViewHolder myviewHolder = new selleradapter.ViewHolder(view);

        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final selleradapter.ViewHolder holder, final int position) {


        final TextView sellername = holder.sellername;
        final TextView sellerid = holder.sellerid;
        final ImageView details = holder.details;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Sellers").child(seller_id.get(position).toString()).child("sellerid").exists())
                {
                    Sellers data = snapshot.child("Sellers").child(seller_id.get(position).toString()).getValue(Sellers.class);
                    sellername.setText(data.getName() + "");
                    sellerid.setText(data.getSellerid() + "");
                    details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(view.getContext(), SellerprofileActivity.class);
                            i.putExtra("sellerid", sellerid.getText().toString());
                            view.getContext().startActivity(i);

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }





    @Override
    public int getItemCount() {
        return seller_id == null?0 : seller_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sellername,sellerid;
       ImageView details;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            sellername = itemView.findViewById(R.id.sellername);
            sellerid = itemView.findViewById(R.id.sellerid);
            details = itemView.findViewById(R.id.details);





        }
    }
}
