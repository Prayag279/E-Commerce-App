package com.example.perfectelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.perfectelectronics.Model.Cart;
import com.example.perfectelectronics.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ImageButton orderSummaryBackBtn;
    TextView addOrChangeAddress,total_cashh,total_amount;
    Button orderContinueBtn;
    EditText add_address;
    ListView details;

    float total_costt = 0;
    DatabaseReference rootref;
    ArrayList cart_item = new ArrayList();
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderSummaryBackBtn = findViewById(R.id.order_summary_back_btn);
        addOrChangeAddress = findViewById(R.id.add_or_change_address);
        total_cashh = findViewById(R.id.total_cashh);
        orderContinueBtn = findViewById(R.id.order_continue_btn);
        total_amount = findViewById(R.id.total_amount);
        add_address = findViewById(R.id.add_address);
        details = findViewById(R.id.details);

        rootref = FirebaseDatabase.getInstance().getReference();

        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i = 1;i<= snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount();i++)
                {
                    if(snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("cartid").exists())
                    {
                        Cart data = snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).getValue(Cart.class);

                        cart_item.add(data.getWholemodelname()+"\n"+"₹"+data.getPrice());
                        total_costt = total_costt + Float.parseFloat(String.valueOf(data.getPrice()));


                    }
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,cart_item);


                details.setAdapter(adapter);
                total_cashh.setText("₹"+String.valueOf(total_costt));
                total_amount.setText("₹"+String.valueOf(total_costt));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        orderSummaryBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });
        addOrChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddressActivity.class);
                startActivity(intent);
            }
        });
        orderContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(add_address.getText().toString()))
                {
                    Toast.makeText(OrderActivity.this, "Type Address", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra("address", add_address.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}