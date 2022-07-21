
package com.example.perfectelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.perfectelectronics.Prevalent.Prevalent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import io.paperdb.Paper;

import static com.example.perfectelectronics.R.id.nav_my_wish_list;

public class HomeActivity extends AppCompatActivity {




    private AppBarConfiguration mAppBarConfiguration;
    int i =0,j = 0;
    long backPressedTime;
 @Override

    public void onBackPressed() {
     if(j==0)
     {
      j =1;
      backPressedTime = System.currentTimeMillis();
      if(backPressedTime + 2000 > System.currentTimeMillis())
      {
          i = 1;
          check();
      }
     }
     else if(j == 1)
     {
         if(backPressedTime + 2000 > System.currentTimeMillis())
         {
             check();
         }
         else
         {
             j=0;i=0;
            onBackPressed();
         }

     }

    }

    public void check()
    {
     if(i == 1)
    {
        Toast.makeText(getApplicationContext(), "Press again for Exit", Toast.LENGTH_LONG).show();
        i++;
    }
    else
    {
        moveTaskToBack(true);
    }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        FloatingActionButton fab = findViewById(R.id.cart);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
         NavigationView navigationView = findViewById(R.id.nav_view);
        ImageView navigation_opener = findViewById(R.id.navigation_open_button);
     //  ImageView wishlist_opener =findViewById(R.id.wishlist_icon);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_categories, R.id.nav_products, R.id.nav_my_orders, R.id.nav_my_cart, R.id.nav_my_notificatios
                , nav_my_wish_list, R.id.nav_my_profile, R.id.nav_sell_on_sellsmart, R.id.nav_addproduct, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigation_opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);

            }
        });


        View headerView  = navigationView.getHeaderView(0);

        TextView username = headerView.findViewById(R.id.user_profile_name);
        String typ = getIntent().getExtras().getString("typ");
        final Menu menu_item = navigationView.getMenu();
        menu_item.findItem(R.id.blank2).setEnabled(false);
        menu_item.findItem(R.id.blank3).setEnabled(false);
        menu_item.findItem(R.id.blank4).setEnabled(false);
        menu_item.findItem(R.id.blank5).setEnabled(false);

        menu_item.findItem(R.id.nav_my_notificatios).setVisible(false);

        if(typ.equals("Users"))
        {
            Prevalent.current_type = "Users";
            Prevalent.current_id = Prevalent.currentOnlineUser.getUserid();
            menu_item.findItem(R.id.nav_addproduct).setVisible(false);
            menu_item.findItem(R.id.nav_myproduct).setVisible(false);
            username.setText(Prevalent.currentOnlineUser.getName());
        }
        if(typ.equals("Sellers"))
        {
            Prevalent.current_type = "Sellers";
            Prevalent.current_id = Prevalent.currentsellerlineUser.getSellerid();
            menu_item.findItem(R.id.nav_sell_on_sellsmart).setVisible(false);
            username.setText(Prevalent.currentsellerlineUser.getName());
        }
     /*   wishlist_opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    }





               Fragment fragment = null;
                fragment = new WishlistFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
                fragment = new HomeFragment();

                getSupportFragmentManager().beginTransaction().hide(fragment).commit();

                        //replace(R.id.nav_host_fragment,fragment).commit();

               FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
               fragment = new WishlistFragment();
                transaction.replace(R.id.nav_host_fragment,fragment);
                transaction.commit();



               



        });*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();



            }
        });
        menu_item.findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                Paper.book().destroy();
                Prevalent.currentsellerlineUser = null;
                Prevalent.currentOnlineUser = null;

                startActivity(intent);
                return true;
            }
        });

        menu_item.findItem(R.id.nav_my_orders).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                Intent intent = new Intent(getApplicationContext(),MyorderActivity.class);

                startActivity(intent);
                return true;
            }
        });

        menu_item.findItem(R.id.nav_my_cart).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
                return true;
            }
        });

        menu_item.findItem(nav_my_wish_list).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                Intent intent = new Intent(getApplicationContext(),WishlistActivity.class);
                startActivity(intent);
                return true;
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);


        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}