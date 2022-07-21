package com.example.perfectelectronics.ui.addproduct;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.perfectelectronics.Prevalent.Prevalent;
import com.example.perfectelectronics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class AddproductFragment extends Fragment{

    private AddproductViewModel addproductViewModel;

    private ProgressDialog loadingBar;
    String quantity_product,price_product,details_product,image_url,request_brand_text,request_model_text,request_details_text;
    int flag = 0;
    LinearLayout brand_layout,model_layout,image_layout,quantity_layout,price_layout,details_layout,button_layout,or1_layout,request_layout,request_brand_layout,
    request_model_layout,request_details_layout,request_button_layout,or2_layout,predefine_layout,category_layout;
    String categoryName,brandName,modelName;
    EditText quantity,price,details,request_brand,request_model,request_details;
    Button submit,request_button;
    TextView request_product,predefine_product;
    ImageView image;


    Spinner categories_spinner,brand_spinner,model_spinner;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addproductViewModel =
                new ViewModelProvider(this).get(AddproductViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addproduct, container, false);

        request_product = root.findViewById(R.id.request_product);
        predefine_product =   root.findViewById(R.id.predefine_product);
        categories_spinner = root.findViewById(R.id.categories_spinner);
        brand_spinner = root.findViewById(R.id.brand_spinner);
        model_spinner = root.findViewById(R.id.model_spinner);

        image = root.findViewById(R.id.product_image);
        quantity = root.findViewById(R.id.product_quantity);
        price = root.findViewById(R.id.product_price);
        details = root.findViewById(R.id.product_details);
        submit = root.findViewById(R.id.main_addproduct_btn);
        loadingBar = new ProgressDialog(getContext());

        request_brand = root.findViewById(R.id.request_brand);
        request_model = root.findViewById(R.id.request_model);
        request_details = root.findViewById(R.id.request_details);
        request_button = root.findViewById(R.id.request_button);

        category_layout= (LinearLayout)root.findViewById(R.id.category_layout);
        brand_layout = (LinearLayout) root.findViewById(R.id.brand_layout);
        model_layout = (LinearLayout)root.findViewById(R.id.model_layout);
        image_layout = (LinearLayout)root.findViewById(R.id.image_layout);
        quantity_layout =(LinearLayout) root.findViewById(R.id.quantity_layout);
       price_layout = (LinearLayout)root.findViewById(R.id.price_layout);
       details_layout = (LinearLayout)root.findViewById(R.id.details_layout);
        button_layout= (LinearLayout)root.findViewById(R.id.button_layout);
        or1_layout= (LinearLayout)root.findViewById(R.id.or1_layout);
        request_layout= (LinearLayout)root.findViewById(R.id.request_layout);
        request_brand_layout= (LinearLayout)root.findViewById(R.id.request_brand_layout);
        request_model_layout= (LinearLayout)root.findViewById(R.id.request_model_layout);
        request_details_layout= (LinearLayout)root.findViewById(R.id.request_details_layout);
        request_button_layout= (LinearLayout)root.findViewById(R.id.request_button_layout);
        or2_layout= (LinearLayout)root.findViewById(R.id.or2_layout);
        predefine_layout= (LinearLayout)root.findViewById(R.id.predefine_layout);

        brand_layout.setVisibility(View.GONE);
        model_layout.setVisibility(View.GONE);
        image_layout.setVisibility(View.GONE);
        quantity_layout.setVisibility(View.GONE);
        price_layout.setVisibility(View.GONE);
        details_layout.setVisibility(View.GONE);
        request_brand_layout.setVisibility(View.GONE);
        request_model_layout.setVisibility(View.GONE);
        request_details_layout.setVisibility(View.GONE);
        request_button_layout.setVisibility(View.GONE);
        or2_layout.setVisibility(View.GONE);
        predefine_layout.setVisibility(View.GONE);












        final DatabaseReference  rootref;
        rootref = FirebaseDatabase.getInstance().getReference();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {


                final ArrayList categoriesList = new ArrayList();


                for(int i = 0;i<=snapshot.child("Categories").getChildrenCount();i++)
                {
                    if (snapshot.child("Categories").child(String.valueOf(i)).getValue() != null) {
                        categoriesList.add(i, snapshot.child("Categories").child(String.valueOf(i)).getValue());
                    }
                    else
                    {
                         i = (int) (snapshot.child("Categories").getChildrenCount() )+2;
                    }

                }

                if(getActivity() != null)
                {
                    ArrayAdapter categories = new ArrayAdapter(AddproductFragment.super.getActivity(), android.R.layout.simple_spinner_item, categoriesList);
                    categories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    categories_spinner.setAdapter(categories);


                    categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                            categoryName = categoriesList.get(i).toString();
                            if (categoryName.equals("Select The Category")) {
                                brand_layout.setVisibility(View.GONE);
                                model_layout.setVisibility(View.GONE);
                                image_layout.setVisibility(View.GONE);
                                quantity_layout.setVisibility(View.GONE);
                                price_layout.setVisibility(View.GONE);
                                details_layout.setVisibility(View.GONE);
                            }

                            if (!(categoryName.equals("Select The Category"))) {
                                final ArrayList brandlist = new ArrayList();

                                for (int j = 0; j <= snapshot.child("Categories").child(categoryName).getChildrenCount(); j++) {
                                    if (snapshot.child("Categories").child(categoryName).child(String.valueOf(j)).getValue() != null) {
                                        brandlist.add(j, snapshot.child("Categories").child(categoryName).child(String.valueOf(j)).getValue());
                                    } else {
                                        j = (int) (snapshot.child("Categories").child(categoryName).getChildrenCount()) + 1;
                                    }
                                }


                                ArrayAdapter brand = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, brandlist);
                                brand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                brand_spinner.setAdapter(brand);
                                brand_layout.setVisibility(View.VISIBLE);
                                brand_spinner.setVisibility(View.VISIBLE);
                                brand_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        brandName = brandlist.get(i).toString();
                                        if (brandName.equals("Select The Brand")) {
                                            model_layout.setVisibility(View.GONE);
                                            image_layout.setVisibility(View.GONE);
                                            quantity_layout.setVisibility(View.GONE);
                                            price_layout.setVisibility(View.GONE);
                                            details_layout.setVisibility(View.GONE);
                                        }

                                        if (!(brandName.equals("Select The Brand"))) {
                                            final ArrayList modellist = new ArrayList();

                                            for (int j = 0; j <= snapshot.child("Categories").child(categoryName).child(brandName).getChildrenCount(); j++) {
                                                if (snapshot.child("Categories").child(categoryName).child(brandName).child(String.valueOf(j)).getValue() != null) {
                                                    modellist.add(j, snapshot.child("Categories").child(categoryName).child(brandName).child(String.valueOf(j)).getValue());

                                                }


                                            }


                                            ArrayAdapter model = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, modellist);
                                            model.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                                            model_spinner.setAdapter(model);

                                            model_layout.setVisibility(View.VISIBLE);
                                            model_spinner.setVisibility(View.VISIBLE);
                                            model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                    modelName = modellist.get(i).toString();
                                                    for(int j =1;j<=snapshot.child("Products").child(Prevalent.currentsellerlineUser.getPhone()).getChildrenCount();j++)
                                                    {
                                                        if(snapshot.child("Products").child(Prevalent.currentsellerlineUser.getPhone()).child(String.valueOf(j)).child("wholemodelname").getValue() != null)
                                                        {
                                                            if(snapshot.child("Products").child(Prevalent.currentsellerlineUser.getPhone()).child(String.valueOf(j)).child("wholemodelname").getValue().toString().equals(modelName))
                                                            {
                                                                flag = 1;
                                                            }
                                                        }

                                                    }
                                                    if (modelName.equals("Select The Model")) {
                                                        image_layout.setVisibility(View.GONE);
                                                        quantity_layout.setVisibility(View.GONE);
                                                        price_layout.setVisibility(View.GONE);
                                                        details_layout.setVisibility(View.GONE);
                                                    }

                                            /*       else if(flag == 1)
                                                    {
                                                        flag= 0;
                                                        model_spinner.setSelection(0);
                                                        Toast.makeText(getContext(), "You Already Added This Product\nCheck My Product", Toast.LENGTH_SHORT).show();
                                                    }*/
                                                    else {

                                                        image_layout.setVisibility(View.VISIBLE);
                                                        image.setVisibility(View.VISIBLE);



                                                 //     image_url = "https://firebasestorage.googleapis.com/v0/b/perfect-electronics-53b36.appspot.com/o/air_conditioner.png?alt=media&token=8e559758-61c5-4805-bf17-5520d157d1a6";

                                                      //  Picasso.get().load(image_url).into(image);
                                                        image.setImageResource(R.drawable.twelvepromax);
                                                        quantity_layout.setVisibility(View.VISIBLE);
                                                        quantity.setVisibility(View.VISIBLE);
                                                        price_layout.setVisibility(View.VISIBLE);
                                                        price.setVisibility(View.VISIBLE);
                                                        details_layout.setVisibility(View.VISIBLE);
                                                        details.setVisibility(View.VISIBLE);


                                                    }
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {

                                                }
                                            });


                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });


                            }

                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request_brand_text = request_brand.getText().toString();
                request_model_text = request_model.getText().toString();
                request_details_text = request_details.getText().toString();
               if(TextUtils.isEmpty(request_brand_text))
                {
                    Toast.makeText(getContext(),"Please Type The Brand Name",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(request_model_text))
                {
                    Toast.makeText(getContext(),"Please Type The Model Name",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(request_details_text))
                {
                    Toast.makeText(getContext(),"Please Type The Details(Color,RAM,Storage)",Toast.LENGTH_LONG).show();
                }
                else
                {
                    loadingBar.setTitle("Request Status");
                    loadingBar.setMessage("Please wait,Request is Sending to us");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                  HashMap<String,Object> notificationMap = new HashMap<>();
                  int notifaication_id = 1;
                    int seller_id = Prevalent.currentsellerlineUser.getSellerid();
                    String seller_phone = String.valueOf(Prevalent.currentsellerlineUser.getPhone());

                    while (snapshot.child("Notifications").child(seller_phone).child(String.valueOf(notifaication_id)).exists())
                    {
                        notifaication_id++;
                    }


                    notificationMap.put("Notification_ID",notifaication_id);
                    notificationMap.put("Seller_ID",seller_id);
                    notificationMap.put("Seller_Phone",seller_phone);

                    notificationMap.put("Brand",request_brand_text);
                    notificationMap.put("Model",request_model_text);
                    notificationMap.put("Details",request_details_text);
                    rootref.child("Notifications").child(seller_phone).child(String.valueOf(notifaication_id)).updateChildren(notificationMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(),"Request sent Successfully ",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();

                        }
                    });



                }

            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity_product = quantity.getText().toString();
                price_product = price.getText().toString();
                details_product = details.getText().toString();

                if(categoryName.equals("Select The Category"))
                {
                    Toast.makeText(getContext(),"Please Select the Category",Toast.LENGTH_LONG).show();

                }
                else if (brandName.equals("Select The Brand"))
                {
                    Toast.makeText(getContext(),"Please Select the Brand",Toast.LENGTH_LONG).show();
                }
                else if (modelName.equals("Select The Model"))
                {
                    Toast.makeText(getContext(),"Please Select the Model",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(quantity_product))
                {
                    Toast.makeText(getContext(),"Please Set the Quantity",Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(quantity_product) > 50)
                {
                    Toast.makeText(getContext(),"You can't add more than 50 Quantity  ",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(price_product))
                {
                    Toast.makeText(getContext(),"Please Set the Price",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(details_product))
                {
                    Toast.makeText(getContext(),"Please Type the Details",Toast.LENGTH_LONG).show();
                }
                else
                {
                   loadingBar.setTitle("Product Approved");
                    loadingBar.setMessage("Please wait,Product is cooking");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    HashMap<String,Object> productdataMap = new HashMap<>();
                      int seller_id = Prevalent.currentsellerlineUser.getSellerid();
                    String seller_phone = String.valueOf(Prevalent.currentsellerlineUser.getPhone());
                    int product_quantity = Integer.parseInt(quantity_product);
                    float product_price = Float.parseFloat(price_product);
                    int product_id = 1;
                    while (snapshot.child("Products").child(String.valueOf(seller_id)).child(String.valueOf(product_id)).exists())
                    {
                        product_id++;
                    }
                    String model = "";
                    String colour = "";
                    String variant = "";
                    int temp = 0;
                    for(int a = 0; a <modelName.length();a++)
                    {
                        if(String.valueOf(modelName.charAt(a)).equals(")"))
                        {
                            temp ++;
                        }
                        if(temp == 1)
                        {
                            colour = colour +String.valueOf(modelName.charAt(a));
                        }
                       if(temp == 3)
                       {
                           variant = variant+String.valueOf(modelName.charAt(a));
                       }

                        if(String.valueOf(modelName.charAt(a)).equals("("))
                        {
                            temp ++;
                        }
                        if(temp == 0)
                        {
                            model = model+String.valueOf(modelName.charAt(a));
                        }
                     }

                  productdataMap.put("productid",product_id);
                    productdataMap.put("sellerid",seller_id);
                    productdataMap.put("sellerphone",seller_phone);
                    productdataMap.put("category",categoryName);
                    productdataMap.put("brand",brandName);
                    productdataMap.put("wholemodelname",modelName);
                    productdataMap.put("model",model.trim());
                    productdataMap.put("colour",colour.trim());
                    productdataMap.put("variant",variant.trim());
                    productdataMap.put("imageurl",image_url);
                    productdataMap.put("quantity",product_quantity);
                    productdataMap.put("price",product_price);
                    productdataMap.put("details",details_product);
                    rootref.child("Products").child(String.valueOf(seller_id)).child(String.valueOf(product_id)).updateChildren(productdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(),"Product Successfully Added",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();

                        }
                    });


                }


            }
        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        request_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_layout.setVisibility(View.GONE);
                brand_layout.setVisibility(View.GONE);
                model_layout.setVisibility(View.GONE);
                image_layout.setVisibility(View.GONE);
                quantity_layout.setVisibility(View.GONE);
                price_layout.setVisibility(View.GONE);
                details_layout.setVisibility(View.GONE);
                button_layout.setVisibility(View.GONE);
                or1_layout.setVisibility(View.GONE);
                request_layout.setVisibility(View.GONE);

                request_brand_layout.setVisibility(View.VISIBLE);
                request_model_layout.setVisibility(View.VISIBLE);
                request_details_layout.setVisibility(View.VISIBLE);
                request_button_layout.setVisibility(View.VISIBLE);
                or2_layout.setVisibility(View.VISIBLE);
                predefine_layout.setVisibility(View.VISIBLE);


            }
        });

        predefine_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                category_layout.setVisibility(View.VISIBLE);
                brand_layout.setVisibility(View.VISIBLE);
                model_layout.setVisibility(View.VISIBLE);
                image_layout.setVisibility(View.VISIBLE);
                quantity_layout.setVisibility(View.VISIBLE);
                price_layout.setVisibility(View.VISIBLE);
                details_layout.setVisibility(View.VISIBLE);
                if (categoryName.equals("Select The Category"))
                {
                    brand_layout.setVisibility(View.GONE);
                    model_layout.setVisibility(View.GONE);
                    image_layout.setVisibility(View.GONE);
                    quantity_layout.setVisibility(View.GONE);
                    price_layout.setVisibility(View.GONE);
                    details_layout.setVisibility(View.GONE);
                }
                else if(brandName.equals("Select The Brand"))
                {
                    model_layout.setVisibility(View.GONE);
                    image_layout.setVisibility(View.GONE);
                    quantity_layout.setVisibility(View.GONE);
                    price_layout.setVisibility(View.GONE);
                    details_layout.setVisibility(View.GONE);
                }
                else if(modelName.equals("Select The Model"))
                {
                    image_layout.setVisibility(View.GONE);
                    quantity_layout.setVisibility(View.GONE);
                    price_layout.setVisibility(View.GONE);
                    details_layout.setVisibility(View.GONE);
                }
            
                     button_layout.setVisibility(View.VISIBLE);
                    or1_layout.setVisibility(View.VISIBLE);
                    request_layout.setVisibility(View.VISIBLE);

                    request_brand_layout.setVisibility(View.GONE);
                    request_model_layout.setVisibility(View.GONE);
                    request_details_layout.setVisibility(View.GONE);
                    request_button_layout.setVisibility(View.GONE);
                    or2_layout.setVisibility(View.GONE);
                    predefine_layout.setVisibility(View.GONE);





            }
        });





        return root;
    }




}