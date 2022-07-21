package com.example.perfectelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.perfectelectronics.Model.Sellers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class SellerprofileActivity extends AppCompatActivity {
    EditText profile_nm, profile_num, profile_type, profile_pass, seller_id;
    Button make_changes, save_changes, Delete_profile, Show_Products;
    DatabaseReference rootref;
    ImageButton seller_back_btn;
    String id;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerprofile);


        id = getIntent().getStringExtra("sellerid");

        seller_id = findViewById(R.id.seller_id);
        seller_id.setText(id + "");
        seller_back_btn = findViewById(R.id.seller_back_btn);
        profile_nm = findViewById(R.id.profile_nm);
        profile_num = findViewById(R.id.profile_num);
        profile_pass = findViewById(R.id.profile_pass);
        profile_type = findViewById(R.id.profile_type);
        make_changes = findViewById(R.id.make_changes);
        Delete_profile = findViewById(R.id.Delete_profile);
        Show_Products = findViewById(R.id.Show_Products);
        save_changes = findViewById(R.id.save_changes);
        save_changes.setVisibility(View.GONE);


        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Sellers").child(id).child("sellerid").exists())
                {
                    Sellers data = snapshot.child("Sellers").child(id).getValue(Sellers.class);
                    profile_nm.setText(data.getName() + "");
                    profile_num.setText(data.getPhone() + "");
                    profile_pass.setText(data.getPassword() + "");
                    profile_type.setText(data.getType() + "");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Show_Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ShowproductActivity.class);
                i.putExtra("sellerid",id);
                startActivity(i);

            }
        });

        seller_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Delete_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object>deletemap = new HashMap<>();
                deletemap.put("Deleted product", "Deleted Product");

                rootref.child("Sellers").child(id).removeValue();
                rootref.child("Sellers").child(id).updateChildren(deletemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent  i = new Intent(getApplicationContext(),AdminPanelActivity.class);
                        startActivity(i);
                    }
                });

            }
        });


        make_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_changes.setVisibility(View.GONE);
                Delete_profile.setVisibility(View.GONE);
                Show_Products.setVisibility(View.GONE);
                save_changes.setVisibility(View.VISIBLE);
                profile_nm.setEnabled(true);
                profile_num.setEnabled(true);
                profile_pass.setEnabled(true);

            }
        });
        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(profile_nm.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Type Name", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(profile_num.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Type Number", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(profile_pass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Type Number", Toast.LENGTH_LONG).show();
                } else {
                    make_changes.setVisibility(View.VISIBLE);
                    Delete_profile.setVisibility(View.VISIBLE);
                    Show_Products.setVisibility(View.VISIBLE);
                    save_changes.setVisibility(View.GONE);
                    profile_nm.setEnabled(false);
                    profile_num.setEnabled(false);
                    profile_pass.setEnabled(false);
                    HashMap<String, Object> editmapping = new HashMap<>();
                    editmapping.put("name", profile_nm.getText().toString());
                    editmapping.put("phone", profile_num.getText().toString());
                    editmapping.put("password", profile_pass.getText().toString());
                    rootref.child("Sellers").child(id).updateChildren(editmapping).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_LONG).show();

                        }
                    });

                }
            }
        });


    }
}

