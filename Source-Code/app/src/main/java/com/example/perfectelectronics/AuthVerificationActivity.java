
package com.example.perfectelectronics;

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
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AuthVerificationActivity extends AppCompatActivity {

    Button otp_handler, verify_otp,change_password;

    EditText  otp_placer,reset_password,retype_reset_password;

    String phoneNumber, otp,type,id;
    Intent i;


    FirebaseAuth auth;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;

    @Override
    public void onBackPressed() {

        System.exit(0);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_verification);
        findViews();
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        phoneNumber="+91"+getIntent().getStringExtra("phone_num");
        otp_placer.setVisibility(View.VISIBLE);
        otp_handler.setVisibility(View.VISIBLE);
        verify_otp.setVisibility(View.GONE);
        reset_password.setVisibility(View.GONE);
        retype_reset_password.setVisibility(View.GONE);
        change_password.setVisibility(View.GONE);

        otp_placer.setText(phoneNumber);
        otp_placer.setEnabled(false);


        StartFirebaseLogin();



        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(reset_password.getText().toString()))
                { Toast.makeText(AuthVerificationActivity.this, "Please type the new Password", Toast.LENGTH_SHORT).show();}
                else if(TextUtils.isEmpty(retype_reset_password.getText().toString()))
                { Toast.makeText(AuthVerificationActivity.this, "Please retype the Password", Toast.LENGTH_SHORT).show();}
                else if(!(reset_password.getText().toString().equals(retype_reset_password.getText().toString())))
                { Toast.makeText(AuthVerificationActivity.this, "Password Mismatched", Toast.LENGTH_SHORT).show();}
               else
                    {
                    DatabaseReference rootref;
                    rootref = FirebaseDatabase.getInstance().getReference();
                    rootref.child(type).child(id).child("password").removeValue();
                    HashMap<String, Object> resetpasswordmap = new HashMap<>();
                    resetpasswordmap.put("password", reset_password.getText().toString());
                    rootref.child(type).child(id).updateChildren(resetpasswordmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AuthVerificationActivity.this, "Password Successfully Changed", Toast.LENGTH_SHORT).show();

                             i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            System.exit(0);


                        }
                    });



                    }

            }
        });


        otp_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_placer.setEnabled(true);
                otp_placer.setText("");
                verify_otp.setVisibility(View.VISIBLE);
                otp_handler.setText("Resend OTP");

                Toast.makeText(getApplicationContext(), "Wait For The OTP", Toast.LENGTH_SHORT).show();


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,                     // Phone number to verify
                        60,                           // Timeout duration
                        TimeUnit.SECONDS,                // Unit of timeout
                        AuthVerificationActivity.this,        // Activity (for callback binding)
                        mCallback);                      // OnVerificationStateChangedCallbacks
            }
        });

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(otp_placer.getText().toString()))
                { Toast.makeText(AuthVerificationActivity.this, "Please type the OTP ", Toast.LENGTH_SHORT).show();}
                else {

                   otp = otp_placer.getText().toString();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);

                    SigninWithPhone(credential);


                }
            }
        });
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(getIntent().getStringExtra("use").equals("number_verification"))
                            {
                                Prevalent.numberchecker =1;
                                Intent in = new Intent(getApplicationContext(),registerrActivity.class);
                                in.putExtra("phone",getIntent().getStringExtra("phone_num"));
                                startActivity(in);
                            }
                            else
                            {
                                otp_placer.setVisibility(View.GONE);
                                otp_handler.setVisibility(View.GONE);
                                verify_otp.setVisibility(View.GONE);
                                reset_password.setVisibility(View.VISIBLE);
                                retype_reset_password.setVisibility(View.VISIBLE);
                                change_password.setVisibility(View.VISIBLE);

                            }





                        } else {
                       
                            Toast.makeText(AuthVerificationActivity.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void findViews() {
        //otp part
        otp_placer=findViewById(R.id.otp_placer);
        otp_handler=findViewById(R.id.otp_handler);
        verify_otp=findViewById(R.id.verify_otp);
       // reset Password part
        reset_password=findViewById(R.id.reset_password);
        retype_reset_password=findViewById(R.id.retype_reset_password);
        change_password =findViewById(R.id.change_password);
    }

    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NotNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NotNull FirebaseException e) {
                Toast.makeText(AuthVerificationActivity.this,"You Reached The Limit",Toast.LENGTH_SHORT).show();

                if (e instanceof FirebaseTooManyRequestsException) {

                    Toast.makeText(getApplicationContext(), "Too many Request", Toast.LENGTH_SHORT).show();
                }

               }

            @Override
            public void onCodeSent(@NotNull String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(AuthVerificationActivity.this,"OTP sent",Toast.LENGTH_SHORT).show();
            }
        };
    }


}