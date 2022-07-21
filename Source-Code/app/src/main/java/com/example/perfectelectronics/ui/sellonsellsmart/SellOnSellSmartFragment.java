package com.example.perfectelectronics.ui.sellonsellsmart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.perfectelectronics.LoginActivity;
import com.example.perfectelectronics.Prevalent.Prevalent;
import com.example.perfectelectronics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SellOnSellSmartFragment extends Fragment {

    Button seller_submit;

    EditText seller_full_name,seller_company_shop_name,seller_gst_no,seller_pan_no,seller_current_password;
    private ProgressDialog loadingBar;
    private SellOnSellSMartViewModel sellOnSellSMartViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       sellOnSellSMartViewModel =
                new ViewModelProvider(this).get(SellOnSellSMartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sellonsellsmart, container, false);

        seller_full_name = root.findViewById(R.id.seller_full_name);
        seller_company_shop_name = root.findViewById(R.id.seller_company_shop_name);
        seller_gst_no = root.findViewById(R.id.seller_gst_no);
        seller_pan_no = root.findViewById(R.id.seller_pan_no);
        seller_current_password = root.findViewById(R.id.seller_current_password_input);
        seller_submit = root.findViewById(R.id.main_seller_button);

        loadingBar = new ProgressDialog(getContext());

        seller_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            createselleraccount();

            }
        });

        return root;
    }

    private void createselleraccount() {
                String phone = Prevalent.currentOnlineUser.getPhone();
                String name = seller_full_name.getText().toString();
                String company_shope = seller_company_shop_name.getText().toString();
                String gst_no = seller_gst_no.getText().toString();
                String pan_no = seller_pan_no.getText().toString();

                String password = seller_current_password.getText().toString();
                String current_password = Prevalent.currentOnlineUser.getPassword();

                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(getContext(), "Please enter your full name...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(company_shope)){
                    Toast.makeText(getContext(), "Please enter your company or shope name...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(gst_no)){
                    Toast.makeText(getContext(), "Please enter your GST No", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(pan_no)){
                    Toast.makeText(getContext(), "Please enter your PAN No", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(current_password)){
                    Toast.makeText(getContext(), "Please enter your current password ", Toast.LENGTH_SHORT).show();
                }
                else if (!(password.equals(current_password))){
                    Toast.makeText(getContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingBar.setTitle("Welcome at our Seller Side");
                    loadingBar.setMessage("Please wait,We are adding something you want");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    validatedetails(phone,name,company_shope,gst_no,pan_no,current_password,password);

                }

    }

    private void validatedetails(final String phone, final String name,final  String company_shope, final String gst_no, final String pan_no, final String password,final String current_password) {
        final DatabaseReference rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                HashMap<String,Object> sellerdataMap = new HashMap<>();
                int sellerid = 1;
                String type = "Sellers";
                while(snapshot.child("Sellers").child(String.valueOf(sellerid)).exists())
                {
                    sellerid++;
                }
                sellerdataMap.put("sellerid",sellerid);
                sellerdataMap.put("phone",phone);
                sellerdataMap.put("name",name);
                sellerdataMap.put("company_shope_name",company_shope);
                sellerdataMap.put("pan_no",pan_no);
                sellerdataMap.put("gst_no",gst_no);
                sellerdataMap.put("password",password);
                sellerdataMap.put("type",type);
                rootref.child("Sellers").child(String.valueOf(sellerid)).updateChildren(sellerdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Congratulations, your Seller account has been created.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(getContext(), LoginActivity.class);

                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
                HashMap<String,Object>userMap = new HashMap<>();
                String deleted = "Deleted_User";


                userMap.put("Deleted_User",deleted);

                rootref.child("Users").child(String.valueOf(Prevalent.currentOnlineUser.getUserid())).updateChildren(userMap);
                rootref.child("Users").child(String.valueOf(Prevalent.currentOnlineUser.getUserid())).child("name").removeValue();
                rootref.child("Users").child(String.valueOf(Prevalent.currentOnlineUser.getUserid())).child("password").removeValue();
                rootref.child("Users").child(String.valueOf(Prevalent.currentOnlineUser.getUserid())).child("phone").removeValue();
                rootref.child("Users").child(String.valueOf(Prevalent.currentOnlineUser.getUserid())).child("type").removeValue();
                rootref.child("Users").child(String.valueOf(Prevalent.currentOnlineUser.getUserid())).child("userid").removeValue();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        
    }
}