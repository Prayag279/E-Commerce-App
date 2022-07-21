package com.example.perfectelectronics;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectelectronics.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class useradapter extends RecyclerView.Adapter<useradapter.ViewHolder> {
    private int useritemlayout;
    private ArrayList user_id;
    DatabaseReference rootref;



    public useradapter(int useritemlayout, ArrayList user_id) {
        this.useritemlayout = useritemlayout;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public useradapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(useritemlayout,parent,false);
        useradapter.ViewHolder myviewHolder = new useradapter.ViewHolder(view);

        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final useradapter.ViewHolder holder, final int position) {


        final TextView username = holder.username;
        final TextView userid = holder.userid;
        final ImageView userdetails = holder.userdetails;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if( snapshot.child("Users").child(user_id.get(position).toString()).child("userid").exists())
                {
                    Users data = snapshot.child("Users").child(user_id.get(position).toString()).getValue(Users.class);
                    username.setText(data.getName() + "");
                    userid.setText(data.getUserid() + "");
                    userdetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(view.getContext(), UsersprofileActivity.class);
                            i.putExtra("userid", userid.getText().toString());
                            view.getContext().startActivity(i);

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }





    @Override
    public int getItemCount() {
        return user_id == null?0 : user_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username,userid;
        ImageView userdetails;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            userid = itemView.findViewById(R.id.userid);
            userdetails = itemView.findViewById(R.id.userdetails);





        }
    }
}
