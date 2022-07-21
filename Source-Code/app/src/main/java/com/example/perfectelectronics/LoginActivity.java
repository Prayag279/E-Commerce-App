package com.example.perfectelectronics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.perfectelectronics.Model.Admins;
import com.example.perfectelectronics.Model.Sellers;
import com.example.perfectelectronics.Model.Users;
import com.example.perfectelectronics.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;




public class LoginActivity extends AppCompatActivity
{

    private EditText InputPhoneNumber, InputPassword;

    TextView forget_password_link;


    private ProgressDialog loadingBar;


    private CheckBox checkBoxRememberMe;

    private String parentDbName = "";
    DatabaseReference RootRef;



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = (Button) findViewById(R.id.main_login_btn);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        forget_password_link = findViewById(R.id.forget_password_link);
        RootRef = FirebaseDatabase.getInstance().getReference();


        loadingBar = new ProgressDialog(this);

        checkBoxRememberMe = (com.rey.material.widget.CheckBox) findViewById(R.id.remember_me);
        Paper.init(this);

        forget_password_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(InputPhoneNumber.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this, "Please Enter Your PhoneNumber First", Toast.LENGTH_SHORT).show();
                }
                else
                    {

                        RootRef.addValueEventListener(new ValueEventListener() {

                            int temp = 0,id_temp;
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(int i =1,j=1; (i<=snapshot.child("Users").getChildrenCount() | j<=snapshot.child("Sellers").getChildrenCount());i++,j++)
                                {
                                    if (snapshot.child("Users").child(String.valueOf(i)).child("phone").getValue() != null) {
                                        if ((snapshot.child("Users").child(String.valueOf(i)).child("phone").getValue().equals(InputPhoneNumber.getText().toString()))) {
                                            temp = 1;
                                            id_temp = i;
                                            Intent intent = new Intent(getApplicationContext(), AuthVerificationActivity.class);
                                            intent.putExtra("type","Users");
                                            intent.putExtra("id", String.valueOf(id_temp));
                                            intent.putExtra("phone_num", InputPhoneNumber.getText().toString());
                                            intent.putExtra("use","password_change");
                                            startActivity(intent);
                                            break;
                                        }
                                    }
                                    if (snapshot.child("Sellers").child(String.valueOf(j)).child("phone").getValue() != null) {
                                        if ((snapshot.child("Sellers").child(String.valueOf(j)).child("phone").getValue().equals(InputPhoneNumber.getText().toString()))) {
                                            temp= 2;
                                            id_temp = j;
                                            Intent intent = new Intent(getApplicationContext(), AuthVerificationActivity.class);
                                            intent.putExtra("type","Sellers");
                                            intent.putExtra("id", String.valueOf(id_temp));
                                            intent.putExtra("phone_num", InputPhoneNumber.getText().toString());
                                            intent.putExtra("use","password_change");
                                            startActivity(intent);
                                            break;
                                        }
                                    }

                                }
                                if(temp == 0 )
                                {
                                    Toast.makeText(LoginActivity.this, "Sorry,This Number is Invalid", Toast.LENGTH_SHORT).show();
                                }




                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });




                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginUser();
            }
        });




    }
    private void LoginUser()
    {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please enter your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {

        if (checkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);

        }


        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {

            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int userid = 1;
                int adminid = 1;
                int sellerid = 1;
                int flag = 0;

                for(int i =1,j=1,k=1; (i<=dataSnapshot.child("Users").getChildrenCount() | j<=dataSnapshot.child("Sellers").getChildrenCount() |
                        k<=dataSnapshot.child("Admins").getChildrenCount());i++,j++,k++) {
                    if (dataSnapshot.child("Users").child(String.valueOf(i)).child("phone").getValue() != null) {
                        if ((dataSnapshot.child("Users").child(String.valueOf(i)).child("phone").getValue().equals(phone))) {
                            flag = 1;
                            userid = i;
                            break;
                        }
                    }
                    if (dataSnapshot.child("Sellers").child(String.valueOf(j)).child("phone").getValue() != null) {
                        if ((dataSnapshot.child("Sellers").child(String.valueOf(j)).child("phone").getValue().equals(phone))) {
                            flag = 2;
                            sellerid = j;
                            break;
                        }
                    }
                    if (dataSnapshot.child("Admins").child(String.valueOf(k)).child("phone").getValue() != null) {
                        if ((dataSnapshot.child("Admins").child(String.valueOf(k)).child("phone").getValue().equals(phone))) {
                            flag = 3;
                            adminid = k;
                            break;
                        }
                    }
                }


                if (flag == 1)
                {
                    if (dataSnapshot.child("Users").child(String.valueOf(userid)).child("phone").getValue().equals(phone))
                    {
                        parentDbName = "Users";
                        Users usersData = null;
                        usersData = dataSnapshot.child(parentDbName).child(String.valueOf(userid)).getValue(Users.class);

                            if (usersData.getPhone().equals(phone) && usersData.getPassword().equals(password))
                            {
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("typ", "Users");
                                Prevalent.currentOnlineUser = usersData;

                                startActivity(intent);
                            }
                            else
                            {
                                loadingBar.dismiss();

                                Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                    }
                }

                if (flag == 2)
                {
                     if(dataSnapshot.child("Sellers").child(String.valueOf(sellerid)).child("phone").getValue().equals(phone))
                    {
                        parentDbName = "Sellers";
                        Sellers sellersData = null;
                        sellersData = dataSnapshot.child(parentDbName).child(String.valueOf(sellerid)).getValue(Sellers.class);
                            if(sellersData.getPhone().equals(phone) && (sellersData.getPassword().equals(password)))
                            {
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                intent.putExtra("typ", "Sellers");
                                Prevalent.currentsellerlineUser = sellersData;

                                startActivity(intent);

                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                    }
                }

                if (flag == 3)
                {
                    if (dataSnapshot.child("Admins").child(String.valueOf(adminid)).child("phone").getValue().equals(phone))
                    {

                        parentDbName = "Admins";
                        Admins adminsData = null;

                        adminsData = dataSnapshot.child(parentDbName).child(String.valueOf(adminid)).getValue(Admins.class);

                        if (adminsData.getPhone().equals(phone) && adminsData.getPassword().equals(password))
                        {
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, AdminPanelActivity.class);
                            intent.putExtra("typ", "Admin");
                            startActivity(intent);
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "admin Password is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
              if(flag != 1 && flag !=2 && flag !=3  )
                {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                }


            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}