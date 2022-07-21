        package com.example.perfectelectronics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

import io.paperdb.Paper;

        public class MainActivity extends AppCompatActivity
        {


    private Button JoinNowButton, loginButton;

    private ProgressDialog loadingBar;

            @Override
    protected void onCreate(Bundle savedInstanceState)
            {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                JoinNowButton = (Button) findViewById(R.id.main_join_now_btn);
                loginButton = (Button) findViewById(R.id.main_already_login_btn);

                loadingBar = new ProgressDialog(this);

                Paper.init(this);

                loginButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                });

                JoinNowButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(MainActivity.this,registerrActivity.class);
                        startActivity(intent);
                    }
                });

                String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
                String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

                if(UserPhoneKey != "" && UserPasswordKey != "")
                {
                    if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
                    {
                        AllowAccess(UserPhoneKey, UserPasswordKey);

                        loadingBar.setTitle("Already Logged in");
                        loadingBar.setMessage("Please wait.....");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                    }
                }

    }

            private void AllowAccess(final String phone, final String password)
            {
                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int userid = 1;
                        int adminid = 1;
                        int sellerid = 1;
                        int flag = 0;
                        String parentDbName = "";
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
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("typ", "Users");
                                    Prevalent.currentOnlineUser = usersData;
                                    
                                    startActivity(intent);
                                }
                                else
                                {
                                    loadingBar.dismiss();

                                    Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
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
                                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                    intent.putExtra("typ", "Sellers");
                                    Prevalent.currentsellerlineUser = sellersData;
                                    startActivity(intent);

                                }
                                else
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
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
                                    Intent intent = new Intent(MainActivity.this, AdminPanelActivity.class);
                                    intent.putExtra("typ", "Admin");
                                    startActivity(intent);
                                }
                                else
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText(MainActivity.this, "admin Password is incorrect", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        if(flag != 1 && flag !=2 && flag !=3  )
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }