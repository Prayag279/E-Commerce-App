package com.example.perfectelectronics.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class CategoriesFragment extends Fragment {
    @Override
    public void onPause() {
        super.onPause();
        onDestroyView();
    }

    LinearLayout mobile,tv,game,aircooler,computer,ac;
    DatabaseReference rootref;

    String category;

    LinearLayout [] allproducts_box = new  LinearLayout[5];
    TextView[] allproducts_line = new   TextView[4];
    TextView [] allproducts_name = new  TextView[5];
    TextView [] allproducts_color = new  TextView[5];
    LinearLayout [] allproducts_variant_layout = new  LinearLayout[5];
    TextView [] allproducts_variant = new  TextView[5];
    TextView [] allproducts_price = new  TextView[5];
    TextView [] allproducts_price_dash = new  TextView[5];
    TextView [] allproducts_sellername = new  TextView[5];
    TextView [] cat_line = new  TextView[6];


    ScrollView allproducts_scroll_view;
    Button all_products_prev_page,all_products_next_page;

    Button [] allproducts_wishlist= new Button[5];
    Button [] allproducts_cart= new Button[5];

    int cart_id = 1,wishlist_id = 1,wishlist_id_pluser,cart_id_pluser;
    int seller_id_mover = 1,item_id_mover = 1,temp = 0,main_flag = 0;
    int total_seller,each_seller_allitem,start_seller_id,start_item_id;
    ArrayList prev_seller_id = new ArrayList();
    ArrayList prev_item_id = new ArrayList();

    private CategoriesViewModel ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModel =
                new ViewModelProvider(this).get(CategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        mobile = root.findViewById(R.id.mobile);
        tv = root.findViewById(R.id.tv);
        game = root.findViewById(R.id.game);
        aircooler = root.findViewById(R.id.aircooler);
        computer = root.findViewById(R.id.computer);
        ac = root.findViewById(R.id.ac);



        rootref= FirebaseDatabase.getInstance().getReference();
        // cat line betwwen category
        cat_line[0] = root.findViewById(R.id.cat_line_1);
        cat_line[1] = root.findViewById(R.id.cat_line_2);
        cat_line[2] = root.findViewById(R.id.cat_line_3);
        cat_line[3] = root.findViewById(R.id.cat_line_4);
        cat_line[4] = root.findViewById(R.id.cat_line_5);
        cat_line[5] = root.findViewById(R.id.cat_line_6);

        //all box layout
        allproducts_box[0] = root.findViewById(R.id.allproducts_1_box);
        allproducts_box[1] = root.findViewById(R.id.allproducts_2_box);
        allproducts_box[2] = root.findViewById(R.id.allproducts_3_box);
        allproducts_box[3] = root.findViewById(R.id.allproducts_4_box);
        allproducts_box[4] = root.findViewById(R.id.allproducts_5_box);

        //all lines below boxes
        allproducts_line [0] = root.findViewById(R.id.allproducts_1_line);
        allproducts_line [1] = root.findViewById(R.id.allproducts_2_line);
        allproducts_line [2] = root.findViewById(R.id.allproducts_3_line);
        allproducts_line [3] = root.findViewById(R.id.allproducts_4_line);

        //scroll view of page and next previous button
        allproducts_scroll_view = root.findViewById(R.id.allproducts_scroll_view);
        all_products_prev_page= root.findViewById(R.id.all_products_prev_page);
        all_products_next_page = root.findViewById(R.id.all_products_next_page);

        //all add whistlist button
        allproducts_wishlist[0] = root.findViewById(R.id.allproducts_1_wishlist);
        allproducts_wishlist[1] = root.findViewById(R.id.allproducts_2_wishlist);
        allproducts_wishlist[2] = root.findViewById(R.id.allproducts_3_wishlist);
        allproducts_wishlist[3] = root.findViewById(R.id.allproducts_4_wishlist);
        allproducts_wishlist[4] = root.findViewById(R.id.allproducts_5_wishlist);

        //all add cart button
        allproducts_cart[0] = root.findViewById(R.id.allproducts_1_cart);
        allproducts_cart[1] = root.findViewById(R.id.allproducts_2_cart);
        allproducts_cart[2] = root.findViewById(R.id.allproducts_3_cart);
        allproducts_cart[3] = root.findViewById(R.id.allproducts_4_cart);
        allproducts_cart[4] = root.findViewById(R.id.allproducts_5_cart);

        //name of products
        allproducts_name[0] = root.findViewById(R.id.allproducts_1_name);
        allproducts_name[1] = root.findViewById(R.id.allproducts_2_name);
        allproducts_name[2] = root.findViewById(R.id.allproducts_3_name);
        allproducts_name[3] = root.findViewById(R.id.allproducts_4_name);
        allproducts_name[4] = root.findViewById(R.id.allproducts_5_name);

        //color of products
        allproducts_color[0] = root.findViewById(R.id.allproducts_1_color);
        allproducts_color[1] = root.findViewById(R.id.allproducts_2_color);
        allproducts_color[2] = root.findViewById(R.id.allproducts_3_color);
        allproducts_color[3] = root.findViewById(R.id.allproducts_4_color);
        allproducts_color[4] = root.findViewById(R.id.allproducts_5_color);

        //variant of products
        allproducts_variant[0] = root.findViewById(R.id.allproducts_1_variant);
        allproducts_variant[1] = root.findViewById(R.id.allproducts_2_variant);
        allproducts_variant[2] = root.findViewById(R.id.allproducts_3_variant);
        allproducts_variant[3] = root.findViewById(R.id.allproducts_4_variant);
        allproducts_variant[4] = root.findViewById(R.id.allproducts_5_variant);

        //varint s layout
        allproducts_variant_layout[0] = root.findViewById(R.id.allproducts_1_variant_layout);
        allproducts_variant_layout[1] = root.findViewById(R.id.allproducts_2_variant_layout);
        allproducts_variant_layout[2] = root.findViewById(R.id.allproducts_3_variant_layout);
        allproducts_variant_layout[3] = root.findViewById(R.id.allproducts_4_variant_layout);
        allproducts_variant_layout[4] = root.findViewById(R.id.allproducts_5_variant_layout);

        //price of products
        allproducts_price[0] = root.findViewById(R.id.allproducts_1_price);
        allproducts_price[1] = root.findViewById(R.id.allproducts_2_price);
        allproducts_price[2] = root.findViewById(R.id.allproducts_3_price);
        allproducts_price[3] = root.findViewById(R.id.allproducts_4_price);
        allproducts_price[4] = root.findViewById(R.id.allproducts_5_price);

        //price dash of products
        allproducts_price_dash[0] = root.findViewById(R.id.allproducts_1_price_dash);
        allproducts_price_dash[1] = root.findViewById(R.id.allproducts_2_price_dash);
        allproducts_price_dash[2] = root.findViewById(R.id.allproducts_3_price_dash);
        allproducts_price_dash[3] = root.findViewById(R.id.allproducts_4_price_dash);
        allproducts_price_dash[4] = root.findViewById(R.id.allproducts_5_price_dash);

        //quantity of product
        allproducts_sellername[0] = root.findViewById(R.id.allproducts_1_sellername);
        allproducts_sellername[1] = root.findViewById(R.id.allproducts_2_sellername);
        allproducts_sellername[2] = root.findViewById(R.id.allproducts_3_sellername);
        allproducts_sellername[3] = root.findViewById(R.id.allproducts_4_sellername);
        allproducts_sellername[4] = root.findViewById(R.id.allproducts_5_sellername);

        all_products_prev_page.setVisibility(View.GONE);
        all_products_next_page.setVisibility(View.GONE);
        allproducts_box[0].setVisibility(View.GONE);
        allproducts_box[1].setVisibility(View.GONE);
        allproducts_box[2].setVisibility(View.GONE);
        allproducts_box[3].setVisibility(View.GONE);
        allproducts_box[4].setVisibility(View.GONE);
        allproducts_line [0].setVisibility(View.GONE);
        allproducts_line [1].setVisibility(View.GONE);
        allproducts_line [2].setVisibility(View.GONE);
        allproducts_line [3].setVisibility(View.GONE);





        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "MOBILES";
                mobile.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                ac.setVisibility(View.GONE);
                aircooler.setVisibility(View.GONE);
                game.setVisibility(View.GONE);
                computer.setVisibility(View.GONE);

                showallproduct();


            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "TELEVISIONS";
                mobile.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                ac.setVisibility(View.GONE);
                aircooler.setVisibility(View.GONE);
                game.setVisibility(View.GONE);
                computer.setVisibility(View.GONE);
                showallproduct();

            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category ="GAME CONSOLES";
                mobile.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                ac.setVisibility(View.GONE);
                aircooler.setVisibility(View.GONE);
                game.setVisibility(View.GONE);
                computer.setVisibility(View.GONE);
                showallproduct();


            }
        });
        aircooler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "AIR COOLERS";
                mobile.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                ac.setVisibility(View.GONE);
                aircooler.setVisibility(View.GONE);
                game.setVisibility(View.GONE);
                computer.setVisibility(View.GONE);
                showallproduct();


            }
        });
        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "LAPTOPS";
                mobile.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                ac.setVisibility(View.GONE);
                aircooler.setVisibility(View.GONE);
                game.setVisibility(View.GONE);
                computer.setVisibility(View.GONE);
                showallproduct();


            }
        });
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "AIR CONDITIONERS";
                mobile.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                ac.setVisibility(View.GONE);
                aircooler.setVisibility(View.GONE);
                game.setVisibility(View.GONE);
                computer.setVisibility(View.GONE);
                showallproduct();


            }
        });



        all_products_next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prev_seller_id.add(temp,start_seller_id);
                prev_item_id.add(temp,start_item_id);
                temp++;
                all_products_prev_page.setVisibility(View.VISIBLE);
                main_flag = 0;


                showallproduct();


            }
        });

        all_products_prev_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temp !=0)
                { temp--;}
                seller_id_mover = (int) prev_seller_id.get(temp);
                item_id_mover = (int) prev_item_id.get(temp);

                main_flag = 0;

                showallproduct();
                if(temp == 0)
                {
                    all_products_prev_page.setVisibility(View.GONE);
                }


            }
        });






        return root;
    }


    private void showallproduct() {
        System.gc();
        wishlist_id_pluser = 0;
        cart_id_pluser = 0;
        allproducts_scroll_view.scrollTo(0,0);


        for(int j = 0;j<5;j++)
        {

            allproducts_box[j].setVisibility(View.GONE);
            allproducts_wishlist[j].setEnabled(true);
            allproducts_wishlist[j].setText("Add To Wishlist");
            allproducts_cart[j].setEnabled(true);
            allproducts_cart[j].setText("Add To Cart");
            cat_line[j].setVisibility(View.GONE);
            if(j == 4)
            {
                cat_line[j+1].setVisibility(View.GONE);
            }

            if(j!=4)
            {
                allproducts_line[j].setVisibility(View.GONE);
            }
            all_products_next_page.setVisibility(View.GONE);
        }





        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                total_seller = Integer.parseInt(String.valueOf(snapshot.child("Sellers").getChildrenCount()));
                while (!(snapshot.child("Products").child(String.valueOf(seller_id_mover)).exists())) {
                    seller_id_mover++;

                }
                each_seller_allitem = Integer.parseInt(String.valueOf(snapshot.child("Products").child(String.valueOf(seller_id_mover)).getChildrenCount()));


                for (int i = 0; i < 5; i++) {
                    if (main_flag == 0)
                    {



                        if (snapshot.child("Sellers").child(String.valueOf(seller_id_mover)).exists())
                        {
                            if(snapshot.child("Products").child(String.valueOf(seller_id_mover)).child(String.valueOf(item_id_mover)).child("productid").exists())
                            {
                                if (snapshot.child("Products").child(String.valueOf(seller_id_mover)).child(String.valueOf(item_id_mover)).child("category").getValue().toString().equals(category))
                                {
                                    if (i == 0) {
                                        start_seller_id = seller_id_mover;
                                        start_item_id = item_id_mover;
                                    }


                                    final int index = i;
                                    final Products itemdata;
                                    itemdata = snapshot.child("Products").child(String.valueOf(seller_id_mover)).child(String.valueOf(item_id_mover)).getValue(Products.class);
                                    allproducts_box[i].setVisibility(View.VISIBLE);
                                    allproducts_name[i].setText(itemdata.getModel().toString());
                                    allproducts_color[i].setText(itemdata.getColour().toString());
                                    if (itemdata.getVariant().equals("")) {
                                        allproducts_variant_layout[i].setVisibility(View.GONE);
                                    } else {
                                        allproducts_variant[i].setText(itemdata.getVariant().toString());
                                    }
                                    allproducts_price[i].setText("₹ " + itemdata.getPrice());
                                    allproducts_price_dash[i].setText(String.valueOf((itemdata.getPrice() + 500.00)));
                                    allproducts_sellername[i].setText("Seller Name :- " + snapshot.child("Sellers").child(String.valueOf(itemdata.getSellerid())).child("name").getValue().toString());
                                    if (String.valueOf(itemdata.getQuantity()).equals("0")) {
                                        allproducts_cart[i].setText("Out Of Stock");
                                        allproducts_cart[i].setEnabled(false);
                                    }

                                    for (int x = 1; x <= snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount(); x++) {
                                        if (snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(x)).child("productid").exists()) {
                                            if (Integer.parseInt(snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(x)).child("productid").getValue().toString()) == itemdata.getProductid()
                                                    && Integer.parseInt(snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(x)).child("sellerid").getValue().toString()) == itemdata.getSellerid()
                                            ) {
                                                allproducts_wishlist[index].setEnabled(false);
                                            }
                                        }
                                    }

                                    for (int x = 1; x <= snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).getChildrenCount(); x++) {
                                        if (snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(x)).child("productid").exists()) {
                                            if (Integer.parseInt(snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(x)).child("productid").getValue().toString()) == itemdata.getProductid()
                                                    && Integer.parseInt(snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(x)).child("sellerid").getValue().toString()) == itemdata.getSellerid()
                                            ) {
                                                allproducts_cart[index].setEnabled(false);
                                            }
                                        }
                                    }
                                    allproducts_wishlist[i].setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view) {


                                            while (snapshot.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(wishlist_id)).exists()) {
                                                wishlist_id++;

                                            }
                                            if (wishlist_id_pluser == 0) {
                                                wishlist_id_pluser = 1;
                                            } else {
                                                wishlist_id++;
                                            }
                                            HashMap<String, Object> wishlistmap = new HashMap<>();

                                            wishlistmap.put("wishlistid", wishlist_id);
                                            wishlistmap.put("model", itemdata.getModel());
                                            wishlistmap.put("colour", itemdata.getColour());
                                            wishlistmap.put("variant", itemdata.getVariant());
                                            wishlistmap.put("quantity", itemdata.getQuantity());
                                            wishlistmap.put("price", itemdata.getPrice());
                                            wishlistmap.put("sellerid", itemdata.getSellerid());
                                            wishlistmap.put("productid", itemdata.getProductid());
                                            wishlistmap.put("wholemodelname", itemdata.getWholemodelname());


                                            rootref.child("Wishlist").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(wishlist_id)).updateChildren(wishlistmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    allproducts_wishlist[index].setEnabled(false);
                                                    Toast.makeText(getContext(), "Product added in Wishlist", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                        }
                                    });

                                    allproducts_cart[i].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {


                                            HashMap<String, Object> quantitymap = new HashMap<>();
                                            quantitymap.put("quantity", (itemdata.getQuantity() - 1));
                                            rootref.child("Products").child(String.valueOf(itemdata.getSellerid())).child(String.valueOf(itemdata.getProductid())).updateChildren(quantitymap);

                                            while (snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cart_id)).exists()) {
                                                if (snapshot.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cart_id)).child("Deleted Item").exists()) {
                                                    cart_id_pluser = 0;
                                                    break;
                                                } else {
                                                    cart_id++;
                                                }

                                            }
                                            if (cart_id_pluser == 0) {
                                                cart_id_pluser = 1;
                                            } else {
                                                cart_id++;
                                            }
                                            HashMap<String, Object> cartmap = new HashMap<>();
                                            cartmap.put("cartid", cart_id);

                                            cartmap.put("model", itemdata.getModel());
                                            cartmap.put("colour", itemdata.getColour());
                                            cartmap.put("variant", itemdata.getVariant());
                                            cartmap.put("quantity", itemdata.getQuantity());
                                            cartmap.put("price", itemdata.getPrice());
                                            cartmap.put("sellerid", itemdata.getSellerid());
                                            cartmap.put("productid", itemdata.getProductid());
                                            cartmap.put("wholemodelname", itemdata.getWholemodelname());

                                            rootref.child("Cart").child(Prevalent.current_type).child(String.valueOf(Prevalent.current_id)).child(String.valueOf(cart_id)).updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    allproducts_cart[index].setEnabled(false);

                                                    Toast.makeText(getContext(), "Product added in cart", Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                        }
                                    });

                                    item_id_mover++;

                                    if (i != 0) {
                                        allproducts_line[i - 1].setVisibility(View.VISIBLE);
                                    }

                                    if (i == 4) {
                                        main_flag = 1;
                                        if (item_id_mover <= each_seller_allitem || seller_id_mover < total_seller) {
                                            all_products_next_page.setVisibility(View.VISIBLE);

                                        }
                                    }


                                }
                                else
                                    {
                                    if (item_id_mover > each_seller_allitem) {
                                        seller_id_mover++;
                                        item_id_mover = 1;
                                        i--;
                                    } else {

                                        item_id_mover++;

                                        i--;
                                    }
                                }
                            }
                            else
                            {
                                if (item_id_mover > each_seller_allitem) {
                                    seller_id_mover++;
                                    item_id_mover = 1;
                                    i--;
                                } else {

                                    item_id_mover++;

                                    i--;
                                }
                            }

                        }
                        else
                        {
                            if (seller_id_mover > total_seller)
                            {
                                if(i == 4) {

                                    seller_id_mover = 1;
                                    item_id_mover = 1;
                                    System.gc();
                                    main_flag = 1;
                                }




                            }
                            else
                            {
                                seller_id_mover++;
                                item_id_mover = 1;
                                i--;
                            }

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