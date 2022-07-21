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

import com.example.perfectelectronics.Model.Cart;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private int cartitemlayout;
    private ArrayList cart_id;
    DatabaseReference rootref;
    String each_cart_id;


    public CartAdapter(int cartitemlayout, ArrayList cart_id) {
        this.cartitemlayout = cartitemlayout;
        this.cart_id = cart_id;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(cartitemlayout,parent,false);
        ViewHolder myviewHolder = new ViewHolder(view);

        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, final int position) {


                final TextView cart_name = holder.cart_name;
                 final TextView post = holder.post;

                TextView line_cart = holder.line_cart;

                final TextView cart_price = holder.cart_price;
                final TextView cart_sellername = holder.cart_sellername;
                TextView cart_quantity = holder.cart_quantity;
                final TextView extra = holder.extra;
                final Button cart_wishlist = holder.cart_wishlist;
                final Button cart_remove = holder.cart_remove;
                post.setText(String.valueOf(position));
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot)
            {
                each_cart_id = cart_id.get(Integer.parseInt(post.getText().toString())).toString();
                extra.setText(each_cart_id);

                if(snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(extra.getText().toString()).child("cartid").exists())
                {
                    final Cart cartdata = snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(extra.getText().toString()).getValue(Cart.class);
                    cart_name.setText(cartdata.getWholemodelname());
                    cart_price.setText("₹" + cartdata.getPrice());
                    cart_sellername.setText("Seller :- " + snapshot.child("Sellers").child(String.valueOf(cartdata.getSellerid())).child("name").getValue().toString());
              holder.cart_wishlist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            int flag = 0;
                            for (int i = 1; i <= snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount(); i++) {
                                if(snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("productid").exists())
                                {

                                    if (snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("productid").getValue().toString().equals(String.valueOf(cartdata.getProductid()))
                                            && snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("sellerid").getValue().toString().equals(String.valueOf(cartdata.getSellerid()))
                                    ) {
                                        HashMap<String, Object> cartdeletemap = new HashMap<>();
                                        cartdeletemap.put("Deleted product", "Deleted Product");

                                        rootref.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cartdata.getCartid())).removeValue();
                                        rootref.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cartdata.getCartid())).updateChildren(cartdeletemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(view.getContext(), "Product Added to Wishlist", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(view.getContext(), CartActivity.class);
                                                view.getContext().startActivity(intent);


                                            }
                                        });
                                        flag = 1;
                                    }
                                }
                            }
                            if (flag == 0) {
                                HashMap<String, Object> wishlistmapping = new HashMap<>();
                                int wishlist_id = (int) snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount() + 1;
                                wishlistmapping.put("wishlistid", wishlist_id);
                                wishlistmapping.put("model", cartdata.getModel());
                                wishlistmapping.put("colour", cartdata.getColour());
                                wishlistmapping.put("variant", cartdata.getVariant());
                                wishlistmapping.put("quantity", cartdata.getQuantity());
                                wishlistmapping.put("price", cartdata.getPrice());
                                wishlistmapping.put("sellerid", cartdata.getSellerid());
                                wishlistmapping.put("productid", cartdata.getProductid());
                                wishlistmapping.put("wholemodelname", cartdata.getWholemodelname());
                                rootref.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(wishlist_id)).updateChildren(wishlistmapping).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, Object> cartdeletemap = new HashMap<>();
                                        cartdeletemap.put("Deleted product", "Deleted Product");

                                        rootref.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cartdata.getCartid())).removeValue();
                                        rootref.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cartdata.getCartid())).updateChildren(cartdeletemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(view.getContext(), "Product Added to Wishlist", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(view.getContext(),CartActivity.class);
                                                view.getContext().startActivity(intent);


                                            }
                                        });

                                    }
                                });


                            }


                        }
                    });
                 holder.cart_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {

                            HashMap<String, Object> cartdeletemap = new HashMap<>();
                            cartdeletemap.put("Deleted product", "Deleted Product");

                            rootref.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cartdata.getCartid())).removeValue();
                            rootref.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cartdata.getCartid())).updateChildren(cartdeletemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    HashMap<String, Object> quantityplusmap = new HashMap<>();
                                    int q =  Integer.parseInt(snapshot.child("Products").child(String.valueOf(cartdata.getSellerid())).child(String.valueOf(cartdata.getProductid())).child("quantity").getValue().toString()) + 1;

                                    quantityplusmap.put("quantity",q);
                                    rootref.child("Products").child(String.valueOf(cartdata.getSellerid())).child(String.valueOf(cartdata.getProductid())).updateChildren(quantityplusmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(view.getContext(),CartActivity.class);
                                            view.getContext().startActivity(intent);


                                        }
                                    });


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
        return cart_id == null?0 : cart_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cart_name,cart_price,cart_sellername,cart_quantity,extra,post,line_cart;
        Button cart_wishlist,cart_remove;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cart_name = itemView.findViewById(R.id.cart_name);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_sellername = itemView.findViewById(R.id.cart_sellername);
            cart_quantity = itemView.findViewById(R.id.cart_quantity);
            extra = itemView.findViewById(R.id.extra);
            cart_wishlist = itemView.findViewById(R.id.cart_wishlist);
            cart_remove = itemView.findViewById(R.id.cart_remove);
            post= itemView.findViewById(R.id.post);
            line_cart = itemView.findViewById(R.id.line_cart);





        }
    }
}


