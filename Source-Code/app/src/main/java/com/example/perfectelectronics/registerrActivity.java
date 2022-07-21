package com.example.perfectelectronics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.perfectelectronics.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class registerrActivity extends AppCompatActivity {

    private Button CreateAccountButton,verify_number;
    private EditText InputName,InputPhoneNumber,InputPassword,InputConfirmPassword;
    private ProgressDialog loadingBar;
     DatabaseReference RootRef;
    int flag = 0;



    @Override
    public void onBackPressed() {
        Intent gotomain  = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(gotomain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerr);

        CreateAccountButton = (Button) findViewById(R.id.main_register_btn);
        InputName = (EditText) findViewById(R.id.register_name_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputConfirmPassword = (EditText) findViewById(R.id.register_confirm_password_input);
        verify_number = (Button) findViewById(R.id.verify_number);
        RootRef = FirebaseDatabase.getInstance().getReference();

        InputName.setVisibility(View.GONE);
        InputPassword.setVisibility(View.GONE);
        InputConfirmPassword.setVisibility(View.GONE);
        CreateAccountButton.setVisibility(View.GONE);

        if(Prevalent.numberchecker == 1)
        {
            Prevalent.numberchecker = 0;
            InputPhoneNumber.setEnabled(false);
            InputPhoneNumber.setText(getIntent().getStringExtra("phone").toString());
            verify_number.setVisibility(View.GONE);
            InputName.setVisibility(View.VISIBLE);
            InputPassword.setVisibility(View.VISIBLE);
            InputConfirmPassword.setVisibility(View.VISIBLE);
            CreateAccountButton.setVisibility(View.VISIBLE);

        }


        verify_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number_exist_or_not();
                if(TextUtils.isEmpty(InputPhoneNumber.getText().toString()))
                {
                    Toast.makeText(registerrActivity.this, "Please Enter Your Number", Toast.LENGTH_SHORT).show();
                }
                else if (InputPhoneNumber.getText().toString().length() != 10)
                {
                    Toast.makeText(registerrActivity.this, "Please Enter Appropriate Number", Toast.LENGTH_SHORT).show();
                }
                else if(flag == 1)
                {
                    flag = 0;
                    InputPhoneNumber.setText("");
                    Toast.makeText(registerrActivity.this, "This Phone Number is Already Exists. ", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent in = new Intent(getApplicationContext(), AuthVerificationActivity.class);
                    in.putExtra("phone_num", InputPhoneNumber.getText().toString());
                    in.putExtra("use", "number_verification");
                    startActivity(in);
                }

            }
        });

        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateAccount();
            }
        });
    }
    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String confirmpassword = InputConfirmPassword.getText().toString();
        String type = "Users";



        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please enter your full name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please enter your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(confirmpassword))
        {
            Toast.makeText(this, "Please enter confirm password...", Toast.LENGTH_SHORT).show();
        }
        else if(!InputPassword.getText().toString().equals(InputConfirmPassword.getText().toString()))
        {
            Toast.makeText(this, "Password Mismatched", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatePhoneNumber(name,phone,password,type);
        }
    }

    private void ValidatePhoneNumber(final String name, final String phone, final String password,final  String type)
    {
          RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot)
            {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    int userid = 1;
                    while(dataSnapshot.child("Users").child(String.valueOf(userid)).exists())
                    {
                        userid++;
                    }

                    userdataMap.put("userid", userid);
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    userdataMap.put("type", type);

                    RootRef.child("Users").child(String.valueOf(userid)).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(registerrActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(registerrActivity.this,LoginActivity.class);




                                startActivity(intent);
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(registerrActivity.this, "Netwrok ERROR: Please try again after some time...", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(registerrActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
             @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void number_exist_or_not() {
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i =1,j=1,k=1; (i<=snapshot.child("Users").getChildrenCount() | j<=snapshot.child("Sellers").getChildrenCount() |
                        k<=snapshot.child("Admins").getChildrenCount());i++,j++,k++)
                {
                    if(snapshot.child("Users").child(String.valueOf(i)).child("phone").getValue()  != null) {
                        if ((snapshot.child("Users").child(String.valueOf(i)).child("phone").getValue().equals(InputPhoneNumber.getText().toString()))) {
                            flag = 1;

                            break;
                        }
                    }
                    if(snapshot.child("Sellers").child(String.valueOf(j)).child("phone").getValue()  != null) {
                        if ((snapshot.child("Sellers").child(String.valueOf(j)).child("phone").getValue().equals(InputPhoneNumber.getText().toString()))) {
                            flag = 1;

                            break;
                        }
                    }
                    if(snapshot.child("Admins").child(String.valueOf(k)).child("phone").getValue()  != null) {
                        if ((snapshot.child("Admins").child(String.valueOf(k)).child("phone").getValue().equals(InputPhoneNumber.getText().toString()))) {
                            flag = 1;

                            break;
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
