package com.example.perfectelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AdminPanelActivity extends AppCompatActivity {

    LinearLayout sellerInfo, customerInfo;
    Button sellerDB, customerDB;
    RecyclerView sellers_all,users_all;
    DatabaseReference rootref;
    ArrayList sellerid = new ArrayList();
    ArrayList userid = new ArrayList();
    ImageView Admin_logout;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        sellerInfo = findViewById(R.id.seller_department);
        customerInfo = findViewById(R.id.customer_department);
        sellers_all = findViewById(R.id.sellers_all);
        users_all = findViewById(R.id.users_all);
        Admin_logout = findViewById(R.id.Admin_logout);

        sellerDB = findViewById(R.id.sellers_db);
        customerDB = findViewById(R.id.customers_db);
        sellerDB.setEnabled(false);
        sellerview();
        Admin_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                Paper.book().destroy();
                startActivity(intent);
            }
        });

        sellerDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerDB.setEnabled(true);
                users_all.setVisibility(View.GONE);
                sellers_all.setVisibility(View.VISIBLE);
                customerInfo.setVisibility(View.GONE);
                sellerInfo.setVisibility(View.VISIBLE);
                sellerDB.setEnabled(false);
                sellerview();

            }
        });

        customerDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerDB.setEnabled(true);
                sellers_all.setVisibility(View.GONE);
                users_all.setVisibility(View.VISIBLE);

                sellerInfo.setVisibility(View.GONE);
                customerInfo.setVisibility(View.VISIBLE);
                customerDB.setEnabled(false);
                userview();
            }
        });

    }

    private void userview() {
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 1; i <= snapshot.child("Users").getChildrenCount() ; i++)
                {
                    if(snapshot.child("Users").child(String.valueOf(i)).child("userid").getValue() != null)
                    {
                        userid.add(snapshot.child("Users").child(String.valueOf(i)).child("userid").getValue().toString());
                    }
                }
                users_all.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                users_all.setItemAnimator(new DefaultItemAnimator());


                users_all.setAdapter( new useradapter(R.layout.user_item_layout,userid));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void sellerview() {
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 1; i <= snapshot.child("Sellers").getChildrenCount() ; i++)
                {
                    if(snapshot.child("Sellers").child(String.valueOf(i)).child("sellerid").getValue() != null)
                    {
                        sellerid.add(snapshot.child("Sellers").child(String.valueOf(i)).child("sellerid").getValue().toString());
                    }
                }
                sellers_all.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                sellers_all.setItemAnimator(new DefaultItemAnimator());


                sellers_all.setAdapter( new selleradapter(R.layout.seller_item_layout,sellerid));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}