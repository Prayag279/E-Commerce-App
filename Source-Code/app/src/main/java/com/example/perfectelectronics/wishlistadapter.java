package com.example.perfectelectronics;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectelectronics.Model.Wishlist;
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

public class wishlistadapter extends RecyclerView.Adapter<wishlistadapter.ViewHolder> {

    private int wishlistitemlayout;
    private ArrayList wishlist_id;
    DatabaseReference rootref;
    String each_wishlist_id;
    Wishlist wishlistdata;

    public wishlistadapter(int wishlistitemlayout, ArrayList wishlist_id) {
        this.wishlistitemlayout = wishlistitemlayout;
        this.wishlist_id = wishlist_id;
    }

    @NonNull
    @Override
    public wishlistadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(wishlistitemlayout,parent,false);
        wishlistadapter.ViewHolder myviewHolder = new wishlistadapter.ViewHolder(view);

        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final wishlistadapter.ViewHolder holder, final int position) {


        final TextView wishlist_name = holder.wishlist_name;
        final TextView post = holder.post;

        TextView line_cart = holder.line_cart;

        final TextView cart_price = holder.cart_price;
        final TextView cart_sellername = holder.cart_sellername;
        TextView cart_quantity = holder.cart_quantity;
        final TextView extra = holder.extra;
        final Button wishlist_cart = holder.wishlist_cart;
        final Button wishlist_remove = holder.wishlist_remove;
        post.setText(String.valueOf(position));
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot)
            {
                each_wishlist_id = wishlist_id.get(Integer.parseInt(post.getText().toString())).toString();
                extra.setText(each_wishlist_id);

                if(snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(extra.getText().toString()).child("wishlistid").exists())
                {
                   wishlistdata = snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(extra.getText().toString()).getValue(Wishlist.class);
                    wishlist_name.setText(wishlistdata.getWholemodelname());
                    cart_price.setText("₹" + wishlistdata.getPrice());
                    cart_sellername.setText("Seller :- " + snapshot.child("Sellers").child(String.valueOf(wishlistdata.getSellerid())).child("name").getValue().toString());
                    holder.wishlist_cart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {

                            int flag = 0;
                            for (int i = 1; i <= snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount(); i++) {
                                if (snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("productid").exists()) {

                                    if (snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("productid").getValue().toString().equals(String.valueOf(wishlistdata.getProductid()))
                                            && snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("sellerid").getValue().toString().equals(String.valueOf(wishlistdata.getSellerid()))
                                    ) {
                                        HashMap<String, Object> wishlistdeletemap = new HashMap<>();
                                        wishlistdeletemap.put("Deleted product", "Deleted Product");

                                        rootref.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(wishlistdata.getWishlistid())).removeValue();
                                        rootref.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(wishlistdata.getWishlistid())).updateChildren(wishlistdeletemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(view.getContext(), "Product Added to Cart", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(view.getContext(), WishlistActivity.class);
                                                view.getContext().startActivity(intent);


                                            }
                                        });
                                        flag = 1;
                                    }
                                }
                            }
                            if (flag == 0) {
                                HashMap<String, Object> cartmapping = new HashMap<>();
                                int cart_id = (int) snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount() + 1;
                                cartmapping.put("cartid", cart_id);
                                cartmapping.put("model", wishlistdata.getModel());
                                cartmapping.put("colour", wishlistdata.getColour());
                                cartmapping.put("variant", wishlistdata.getVariant());
                                cartmapping.put("quantity", wishlistdata.getQuantity());
                                cartmapping.put("price", wishlistdata.getPrice());
                                cartmapping.put("sellerid", wishlistdata.getSellerid());
                                cartmapping.put("productid", wishlistdata.getProductid());
                                cartmapping.put("wholemodelname", wishlistdata.getWholemodelname());
                                rootref.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cart_id)).updateChildren(cartmapping).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, Object> wishlistdeletemap = new HashMap<>();
                                        wishlistdeletemap.put("Deleted product", "Deleted Product");

                                        rootref.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(wishlistdata.getWishlistid())).removeValue();
                                        rootref.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(wishlistdata.getWishlistid())).updateChildren(wishlistdeletemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(view.getContext(), "Product Added to Cart", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(view.getContext(),WishlistActivity.class);
                                                view.getContext().startActivity(intent);


                                            }
                                        });

                                    }
                                });


                            }


                        }
                    });
                    holder.wishlist_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            Toast.makeText(view.getContext(), "weeee", Toast.LENGTH_SHORT).show();
                            HashMap<String, Object> wishlistdeletemap = new HashMap<>();
                            wishlistdeletemap.put("Deleted product", "Deleted Product");

                            rootref.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(wishlistdata.getWishlistid())).removeValue();
                            rootref.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(wishlistdata.getWishlistid())).updateChildren(wishlistdeletemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                            Intent intent = new Intent(view.getContext(),WishlistActivity.class);
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
        return wishlist_id == null?0 : wishlist_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wishlist_name,cart_price,cart_sellername,cart_quantity,extra,post,line_cart;
        Button wishlist_cart,wishlist_remove;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            wishlist_name = itemView.findViewById(R.id.wishlist_name);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_sellername = itemView.findViewById(R.id.cart_sellername);
            cart_quantity = itemView.findViewById(R.id.cart_quantity);
            extra = itemView.findViewById(R.id.extra);
            wishlist_cart = itemView.findViewById(R.id.wishlist_cart);
            wishlist_remove = itemView.findViewById(R.id.wishlist_remove);
            post= itemView.findViewById(R.id.post);
            line_cart = itemView.findViewById(R.id.line_cart);





        }
    }

}
