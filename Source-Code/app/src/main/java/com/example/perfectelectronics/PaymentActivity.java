package com.example.perfectelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class PaymentActivity extends AppCompatActivity {

    ImageButton paymentsBackBtn;
    Button paymentBtn;
    String address;
    RadioButton method;
    DatabaseReference rootref;

    RadioGroup groupradio;

    ArrayList cart_item = new ArrayList();
    int  flag = 0;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
      address = getIntent().getStringExtra("address");

        paymentsBackBtn = findViewById(R.id.paymnets_back_btn);

        paymentBtn = findViewById(R.id.paymentBtn);
        groupradio = findViewById(R.id.groupradio);
        rootref = FirebaseDatabase.getInstance().getReference();




        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected_id = groupradio.getCheckedRadioButtonId();
                method = (RadioButton)findViewById(selected_id);
                if(selected_id==-1){
                    Toast.makeText(getApplicationContext(),"Select Payment Method", Toast.LENGTH_SHORT).show();
                }
                else {


                    rootref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot snapshot) {
                            for (int i = 1; i <= snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount(); i++) {
                                if (snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).child("cartid").getValue() != null) {
                                    Cart data = snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(i)).getValue(Cart.class);

                                    cart_item.add(data.getCartid());

                                }
                            }

                            for (int i = 0; i < cart_item.size(); i++) {
                                if (flag != 1) {

                                    String id = cart_item.get(i).toString();
                                    Cart data = snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(id).getValue(Cart.class);
                                    int orderid = 0;
                                    orderid = (int) snapshot.child("Orders").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount() + 1;

                                    HashMap<String, Object> ordermap = new HashMap<>();

                                    ordermap.put("orderid", orderid);
                                    ordermap.put("model", data.getModel());
                                    ordermap.put("colour", data.getColour());
                                    ordermap.put("variant", data.getVariant());
                                    ordermap.put("quantity", data.getQuantity());
                                    ordermap.put("price", data.getPrice());
                                    ordermap.put("sellerid", data.getSellerid());
                                    ordermap.put("productid", data.getProductid());
                                    ordermap.put("method", method.getText().toString());
                                    ordermap.put("wholemodelname", data.getWholemodelname());
                                    ordermap.put("address", address);

                                    rootref.child("Orders").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(orderid)).updateChildren(ordermap);
                                    if (i == (cart_item.size() - 1)) {
                                        flag = 1;
                                        rootref.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(PaymentActivity.this, "Ordered Successfully", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(getApplicationContext(),MyorderActivity.class);
                                                startActivity(i);

                                            }
                                        });

                                    }
                                }


                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        if(flag == 1)
        {
            Intent i = new Intent(getApplicationContext(),MyorderActivity.class);
            startActivity(i);
        }
        paymentsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}






