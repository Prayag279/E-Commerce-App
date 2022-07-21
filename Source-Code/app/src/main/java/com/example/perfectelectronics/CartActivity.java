package com.example.perfectelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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


public class CartActivity extends AppCompatActivity {
    ImageButton cart_back_btn;
    DatabaseReference rootref;
    public ArrayList cartid = new ArrayList();

    float total_cost = 0;
    RecyclerView cart_recyclerview;
    Button placeOrderButton;
    TextView total_price;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        i.putExtra("typ", Prevalent.current_type);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        placeOrderButton = findViewById(R.id.place_order);
        total_price = findViewById(R.id.total_price);


        cart_back_btn = findViewById(R.id.cart_back_btn);
        cart_recyclerview = findViewById(R.id.cart_recyclerview);
        rootref = FirebaseDatabase.getInstance().getReference();



        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 1; i <= snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount() ; i++)
                {
                    if(snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("cartid").getValue() != null)
                    {
                        cartid.add(snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("cartid").getValue().toString());
                        total_cost = total_cost + Float.parseFloat(snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("price").getValue().toString());
                    }
                }
                total_price.setText("₹"+String.valueOf(total_cost));

                cart_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                cart_recyclerview.setItemAnimator(new DefaultItemAnimator());


                cart_recyclerview.setAdapter( new CartAdapter(R.layout.cart_item_layout,cartid));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        cart_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });


        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);
            }
        });
    }



}
