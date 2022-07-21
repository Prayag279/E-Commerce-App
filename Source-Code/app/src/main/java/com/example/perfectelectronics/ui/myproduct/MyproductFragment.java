package com.example.perfectelectronics.ui.myproduct;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.perfectelectronics.Model.Products;
import com.example.perfectelectronics.Prevalent.Prevalent;
import com.example.perfectelectronics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyproductFragment extends Fragment {

    TextView [] name = new TextView[5];
    TextView [] line = new TextView[4];
    TextView [] display_price = new TextView[5];

    TextView [] display_quantity = new TextView[5];


    Button  [] delete = new  Button[5];
    Button  [] modify = new  Button[5];
    Button [] min = new Button[5];
    Button [] plus = new  Button[5];
    Button [] save = new Button[5];

    EditText [] price = new EditText[5];
    EditText [] quantity = new EditText[5];

    LinearLayout [] edit_layout = new LinearLayout[5];
    LinearLayout [] modify_layout = new LinearLayout[5];
    LinearLayout [] product_layout = new LinearLayout[5];

    ScrollView scroll_layout;
    DatabaseReference rootref;
    Button next_page ,prev_page;
    int i = 0,flag = 0;
  int product_id_mover = 1;
        int start,temp = 0,end;

    ArrayList prev = new ArrayList();




    private MyproductViewModel myproductViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myproductViewModel =
                new ViewModelProvider(this).get(MyproductViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myproduct, container, false);

        name[0] = root.findViewById(R.id.name_1);
        name[1] = root.findViewById(R.id.name_2);
        name[2] = root.findViewById(R.id.name_3);
        name[3] = root.findViewById(R.id.name_4);
        name[4] = root.findViewById(R.id.name_5);

        display_price[0] = root.findViewById(R.id.display_price_1);
        display_price[1] = root.findViewById(R.id.display_price_2);
        display_price[2] = root.findViewById(R.id.display_price_3);
        display_price[3] = root.findViewById(R.id.display_price_4);
        display_price[4] = root.findViewById(R.id.display_price_5);


        display_quantity[0] = root.findViewById(R.id.display_quantity_1);
        display_quantity[1] = root.findViewById(R.id.display_quantity_2);
        display_quantity[2] = root.findViewById(R.id.display_quantity_3);
        display_quantity[3] = root.findViewById(R.id.display_quantity_4);
        display_quantity[4] = root.findViewById(R.id.display_quantity_5);


        line[0] = root.findViewById(R.id.line_1);
        line[1] = root.findViewById(R.id.line_2);
        line[2] = root.findViewById(R.id.line_3);
        line[3] = root.findViewById(R.id.line_4);

        //delete button
        delete[0] = root.findViewById(R.id.delete_1);
        delete[1] = root.findViewById(R.id.delete_2);
        delete[2] = root.findViewById(R.id.delete_3);
        delete[3] = root.findViewById(R.id.delete_4);
        delete[4] = root.findViewById(R.id.delete_5);

        //modify button
        modify[0] = root.findViewById(R.id.modify_1);
        modify[1] = root.findViewById(R.id.modify_2);
        modify[2] = root.findViewById(R.id.modify_3);
        modify[3] = root.findViewById(R.id.modify_4);
        modify[4] = root.findViewById(R.id.modify_5);

        //all 5 box layout
        product_layout[0] = root.findViewById(R.id.myproduct_1);
        product_layout[1] = root.findViewById(R.id.myproduct_2);
        product_layout[2] = root.findViewById(R.id.myproduct_3);
        product_layout[3] = root.findViewById(R.id.myproduct_4);
        product_layout[4] = root.findViewById(R.id.myproduct_5);

        //layout whcich contain modify and delete button
        edit_layout[0] = root.findViewById(R.id.edit_layout_1);
        edit_layout[1] = root.findViewById(R.id.edit_layout_2);
        edit_layout[2] = root.findViewById(R.id.edit_layout_3);
        edit_layout[3] = root.findViewById(R.id.edit_layout_4);
        edit_layout[4] = root.findViewById(R.id.edit_layout_5);

        //layout which contain price quantity and save button for modify product
        modify_layout[0] = root.findViewById(R.id.modify_layout_1);
        modify_layout[1] = root.findViewById(R.id.modify_layout_2);
        modify_layout[2] = root.findViewById(R.id.modify_layout_3);
        modify_layout[3] = root.findViewById(R.id.modify_layout_4);
        modify_layout[4] = root.findViewById(R.id.modify_layout_5);

        //edittext of price which is insied modify layout
        price[0] = root.findViewById(R.id.price_1);
        price[1] = root.findViewById(R.id.price_2);
        price[2] = root.findViewById(R.id.price_3);
        price[3] = root.findViewById(R.id.price_4);
        price[4] = root.findViewById(R.id.price_5);

        //button min for decrease quantity
        min[0] = root.findViewById(R.id.min_1);
        min[1] = root.findViewById(R.id.min_2);
        min[2] = root.findViewById(R.id.min_3);
        min[3] = root.findViewById(R.id.min_4);
        min[4] = root.findViewById(R.id.min_5);

        //edittext of quantity which is inside modify layout
        quantity[0] = root.findViewById(R.id.quantity_1);
        quantity[1] = root.findViewById(R.id.quantity_2);
        quantity[2] = root.findViewById(R.id.quantity_3);
        quantity[3] = root.findViewById(R.id.quantity_4);
        quantity[4] = root.findViewById(R.id.quantity_5);

        //button plus for increase quantity
        plus[0] = root.findViewById(R.id.plus_1);
        plus[1] = root.findViewById(R.id.plus_2);
        plus[2] = root.findViewById(R.id.plus_3);
        plus[3] = root.findViewById(R.id.plus_4);
        plus[4] = root.findViewById(R.id.plus_5);

        //save button for update price and quanitty
        save[0] = root.findViewById(R.id.save_1);
        save[1] = root.findViewById(R.id.save_2);
        save[2] = root.findViewById(R.id.save_3);
        save[3] = root.findViewById(R.id.save_4);
        save[4] = root.findViewById(R.id.save_5);


        //at the start scroll view define's here for automatic scroll at top by using scrollTo(x,y);
        scroll_layout = root.findViewById(R.id.scroll_layout);

        next_page= root.findViewById(R.id.next_page);
        prev_page = root.findViewById(R.id.prev_page);






        next_page.setVisibility(View.VISIBLE);
        prev_page.setVisibility(View.GONE);



        showproduct();

        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prev.add(temp,start);
                temp++;
                prev_page.setVisibility(View.VISIBLE);

                scroll_layout.scrollTo(0,0);
                flag = 0;
                showproduct( );
            }
        });
        prev_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      if(temp != 0)
                     {temp--;}
                    product_id_mover = (int) prev.get(temp);
                   scroll_layout.scrollTo(0,0);
                      flag = 0;
                    showproduct();
                    if(temp == 0)
                    {prev_page.setVisibility(View.GONE);}

                    else
                    { prev_page.setVisibility(View.VISIBLE);}
                next_page.setVisibility(View.VISIBLE);
 }
        });


        return root;
    }




    private void showproduct() {





        for(int k= 0;k<5;k++)
        {
            product_layout[k].setVisibility(View.VISIBLE);
            if(k!=4)
            {
                line[k].setVisibility(View.VISIBLE);
            }
        }



        rootref = FirebaseDatabase.getInstance().getReference();
        final String currentid = String.valueOf(Prevalent.currentsellerlineUser.getSellerid());

            rootref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot snapshot) {
                    end = (int) snapshot.child("Products").child(currentid).getChildrenCount();

                        int j = 0;
                        while (snapshot.child("Products").child(currentid).child(String.valueOf(end - j)).child("Deleted Product").exists())
                        {
                            rootref.child("Products").child(currentid).child(String.valueOf(end - j)).removeValue();
                            j++;
                        }

                    end = (int) snapshot.child("Products").child(currentid).getChildrenCount();



                    for(i=0;i<5;i++) {



                        if (flag == 0) {
                            if (snapshot.child("Products").child(currentid).child(String.valueOf(product_id_mover)).exists()) {


                                if (snapshot.child("Products").child(currentid).child(String.valueOf(product_id_mover)).child("productid").getValue() != null) {
                                    if (i == 0) {
                                        start = product_id_mover;


                                    }
                                    if (i == 4) {
                                        flag = 1;

                                        if (product_id_mover == end) {
                                            next_page.setVisibility(View.GONE);
                                        }

                                    }


                                    final Products productdata = snapshot.child("Products").child(currentid).child(String.valueOf(product_id_mover)).getValue(Products.class);
                                    final String product_id = String.valueOf(productdata.getProductid());
                                    final String p = String.valueOf(productdata.getPrice());//price
                                    final String q = String.valueOf(productdata.getQuantity());//quantity
                                    final int index = i;

                                    name[i].setText(productdata.getWholemodelname()+"");
                                    display_price[i].setText("₹"+productdata.getPrice());
                                    display_quantity[i].setText("Qty : "+productdata.getQuantity());



                                    modify[i].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            edit_layout[index].setVisibility(View.GONE);
                                            modify_layout[index].setVisibility(View.VISIBLE);
                                            price[index].setText(p + "");
                                            quantity[index].setText(q);


                                        }
                                    });
                                    min[i].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (!(TextUtils.isEmpty(quantity[index].getText().toString()))) {
                                                if (!(quantity[index].getText().toString().equals("0"))) {
                                                    int temp_quantity = Integer.parseInt(quantity[index].getText().toString()) - 1;
                                                    quantity[index].setText(String.valueOf(temp_quantity));
                                                }
                                            }
                                        }
                                    });

                                    plus[i].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (!(TextUtils.isEmpty(quantity[index].getText().toString()))) {
                                                if (Integer.parseInt(quantity[index].getText().toString()) < 50) {
                                                    int temp_quantity = Integer.parseInt(quantity[index].getText().toString()) + 1;
                                                    quantity[index].setText(String.valueOf(temp_quantity));
                                                }
                                            } else {
                                                quantity[index].setText("1");
                                            }
                                        }
                                    });
                                    save[i].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (TextUtils.isEmpty(price[index].getText().toString())) {
                                                Toast.makeText(getContext(), "Please Enter The Price", Toast.LENGTH_SHORT).show();

                                            } else if (TextUtils.isEmpty(quantity[index].getText().toString())) {
                                                Toast.makeText(getContext(), "Please Enter The Quantity", Toast.LENGTH_SHORT).show();

                                            } else if (!(Integer.parseInt(quantity[index].getText().toString()) > 0 && Integer.parseInt(quantity[index].getText().toString()) <= 50)) {
                                                Toast.makeText(getContext(), "Please Enter Quantity between 1 to 50", Toast.LENGTH_SHORT).show();
                                            } else {

                                                rootref.child("Products").child(currentid).child(product_id).child("price").removeValue();
                                                rootref.child("Products").child(currentid).child(product_id).child("quantity").removeValue();
                                                HashMap<String,Object> updatedatamap = new HashMap<>();
                                                updatedatamap.put("price",Float.parseFloat(price[index].getText().toString()));
                                                updatedatamap.put("quantity",Integer.parseInt(quantity[index].getText().toString()));
                                                rootref.child("Products").child(currentid).child(product_id).updateChildren(updatedatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(getContext(),"Data Updated Successfully",Toast.LENGTH_LONG).show();
                                                        flag =0;
                                                        product_id_mover = start;
                                                         modify_layout[index].setVisibility(View.GONE);
                                                        edit_layout[index].setVisibility(View.VISIBLE);
                                                        showproduct();

                                                    }
                                                });

//
                                            }
                                        }
                                    });


                                    delete[i].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            AlertDialog.Builder deletealert = new AlertDialog.Builder(getContext());
                                            deletealert.setTitle("ALERT");
                                            deletealert.setMessage("You sure about DELETE below product :- \n"+productdata.getModel());
                                            deletealert.setNegativeButton("NO",null);
                                            deletealert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int idd) {
                                                    if(index == 0 && Integer.parseInt(product_id) == end)
                                                    {
                                                        if(temp != 0)
                                                        {   temp--;
                                                            product_id_mover = (int) prev.get(temp);
                                                        }
                                                        if(temp == 0)
                                                        {prev_page.setVisibility(View.GONE);}




                                                    }
                                                    else {
                                                      
                                                        product_id_mover = start;
                                                    }



                                                        HashMap<String, Object> deleteproductMap = new HashMap<>();
                                                        deleteproductMap.put("Deleted Product", "Deleted_product");
                                                        rootref.child("Products").child(String.valueOf(Prevalent.currentsellerlineUser.getSellerid())).child(String.valueOf(product_id)).removeValue();
                                                        rootref.child("Products").child(String.valueOf(Prevalent.currentsellerlineUser.getSellerid())).child(String.valueOf(product_id)).updateChildren(deleteproductMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                flag = 0;

                                                                showproduct();
                                                            }
                                                        });
                                                    }

                                            });
                                            deletealert.show();




                                        }
                                    });


                                } else {
                                    i--;
                                }

                                product_id_mover++;


                            } else {




                                if (i == 4) {
                                    flag = 1;


                                }
                                next_page.setVisibility(View.GONE);
                                Runtime.getRuntime().gc();

                                Runtime.getRuntime().freeMemory();
                                onDestroyView();


                                product_layout[i].setVisibility(View.GONE);
                                if (i == 0) {
                                    line[i].setVisibility(View.GONE);
                                } else {
                                    line[i - 1].setVisibility(View.GONE);
                                }

                            }
                        }
                        else {
                            Runtime.getRuntime().gc();

                            Runtime.getRuntime().freeMemory();
                            onDestroyView();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });







    }





}