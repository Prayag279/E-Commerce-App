package com.example.perfectelectronics;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectelectronics.Model.Orders;
import com.example.perfectelectronics.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class myorderadapter extends RecyclerView.Adapter<myorderadapter.ViewHolder>{
    private int myorderitemlayout;
    private ArrayList myorder_id;
    DatabaseReference rootref;
    String each_myorder_id;
    Orders orderdata;
    

    public myorderadapter(int myorderitemlayout, ArrayList myorder_id) {
        this.myorderitemlayout = myorderitemlayout;
        this.myorder_id = myorder_id;
    }

    @NonNull
    @Override
    public myorderadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(myorderitemlayout,parent,false);
        myorderadapter.ViewHolder myviewHolder = new myorderadapter.ViewHolder(view);

        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myorderadapter.ViewHolder holder, final int position) {


        final TextView wishlist_name = holder.wishlist_name;
        final TextView post = holder.post;
        final TextView method = holder.method;

        TextView line_cart = holder.line_cart;

        final TextView cart_price = holder.cart_price;
        final TextView cart_sellername = holder.cart_sellername;
        TextView cart_quantity = holder.cart_quantity;
        final TextView extra = holder.extra;
        final Button wishlist_cart = holder.wishlist_cart;
        final Button myorder_remove = holder.myorder_remove;
        post.setText(String.valueOf(position));
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot)
            {
                each_myorder_id = myorder_id.get(Integer.parseInt(post.getText().toString())).toString();
                extra.setText(each_myorder_id);

                if(snapshot.child("Orders").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(extra.getText().toString()).child("orderid").exists())
                {
                    orderdata = snapshot.child("Orders").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(extra.getText().toString()).getValue(Orders.class);
                    wishlist_name.setText(orderdata.getWholemodelname());
                    cart_price.setText("₹" + orderdata.getPrice());
                    cart_sellername.setText("Seller :- " + snapshot.child("Sellers").child(String.valueOf(orderdata.getSellerid())).child("name").getValue().toString());
                    method.setText("Paid by "+orderdata.getMethod());
                    holder.myorder_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {

                            HashMap<String, Object> wishlistdeletemap = new HashMap<>();
                            wishlistdeletemap.put("Deleted product", "Deleted Product");

                            rootref.child("Orders").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(orderdata.getOrderid())).removeValue();
                            rootref.child("Orders").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(orderdata.getOrderid())).updateChildren(wishlistdeletemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    Intent intent = new Intent(view.getContext(),MyorderActivity.class);
                                    view.getContext().startActivity(intent);





                                }
                            });

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
        return myorder_id == null?0 : myorder_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wishlist_name,cart_price,cart_sellername,cart_quantity,extra,post,line_cart,method;
        Button wishlist_cart,myorder_remove;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            wishlist_name = itemView.findViewById(R.id.wishlist_name);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_sellername = itemView.findViewById(R.id.cart_sellername);
            cart_quantity = itemView.findViewById(R.id.cart_quantity);
            extra = itemView.findViewById(R.id.extra);
            wishlist_cart = itemView.findViewById(R.id.wishlist_cart);
            myorder_remove = itemView.findViewById(R.id.myorder_remove);
            post= itemView.findViewById(R.id.post);
            line_cart = itemView.findViewById(R.id.line_cart);
            method = itemView.findViewById(R.id.method);






        }
    }

}
