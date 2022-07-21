package com.example.perfectelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectelectronics.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {
    ImageButton wishlist_back_btn;
    DatabaseReference rootref;
    public ArrayList wishlistid = new ArrayList();


    RecyclerView wishlist_recyclerview;


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        i.putExtra("typ", Prevalent.current_type);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);




        wishlist_back_btn = findViewById(R.id.wishlist_back_btn);
        wishlist_recyclerview = findViewById(R.id.wishlist_recyclerview);
        rootref = FirebaseDatabase.getInstance().getReference();



        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 1; i <= snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount() ; i++)
                {
                    if(snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("wishlistid").getValue() != null)
                    {
                        wishlistid.add(snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("wishlistid").getValue().toString());
                    }
                }


                wishlist_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                wishlist_recyclerview.setItemAnimator(new DefaultItemAnimator());


                wishlist_recyclerview.setAdapter( new wishlistadapter(R.layout.wishlist_item_layout,wishlistid));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        wishlist_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }



}