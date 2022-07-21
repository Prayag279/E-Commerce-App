package com.example.perfectelectronics.ui.profile;

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

import com.example.perfectelectronics.Prevalent.Prevalent;
import com.example.perfectelectronics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfileFragment extends Fragment {


    EditText profile_nm,profile_num,profile_type;
    Button make_changes,save_changes;
    DatabaseReference rootref;
    private ProfileViewModel ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_nm = root.findViewById(R.id.profile_nm);
        profile_num = root.findViewById(R.id.profile_num);
        profile_type = root.findViewById(R.id.profile_type);
        make_changes = root.findViewById(R.id.make_changes);
        save_changes = root.findViewById(R.id.save_changes);
        save_changes.setVisibility(View.GONE);

        profile_type.setText(Prevalent.current_type+"");
        rootref = FirebaseDatabase.getInstance().getReference();
        if(Prevalent.current_type == "Users")
        {
            profile_nm.setText(Prevalent.currentOnlineUser.getName().toString());
            profile_num.setText(Prevalent.currentOnlineUser.getPhone().toString());
        }
        else
        {
            profile_nm.setText(Prevalent.currentsellerlineUser.getName().toString());
            profile_num.setText(Prevalent.currentsellerlineUser.getPhone().toString());
        }

        make_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_changes.setVisibility(View.GONE);
                save_changes.setVisibility(View.VISIBLE);
                profile_nm.setEnabled(true);
                profile_num.setEnabled(true);

            }
        });
        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(profile_nm.getText().toString()))
                {
                    Toast.makeText(getContext(),"Type Name",Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(profile_num.getText().toString()))
                {
                    Toast.makeText(getContext(),"Type Number",Toast.LENGTH_LONG).show();
                }
                else
                    {
                        make_changes.setVisibility(View.VISIBLE);
                        save_changes.setVisibility(View.GONE);
                        profile_nm.setEnabled(false);
                        profile_num.setEnabled(false);
                        HashMap<String,Object> editmapping = new HashMap<>();
                        editmapping.put("name",profile_nm.getText().toString());
                        editmapping.put("phone",profile_num.getText().toString());
                        rootref.child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).updateChildren(editmapping).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(),"Changes Saved",Toast.LENGTH_LONG).show();

                            }
                        });

                }
            }
        });



        return root;
    }
}