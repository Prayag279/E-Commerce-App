package com.example.perfectelectronics.Prevalent;


import com.example.perfectelectronics.Model.Notifications;
import com.example.perfectelectronics.Model.Products;
import com.example.perfectelectronics.Model.Sellers;
import com.example.perfectelectronics.Model.Users;
// static no use Prevelent no object bnvya vgr direct class na name to call krva use thai ex prevalent.Users etc..
public class Prevalent
{
    public static Users currentOnlineUser;
    public static Sellers currentsellerlineUser;
    public static Products currentproduct;
    public  static Notifications currentnotification;



    public static int numberchecker;// create account ma jo number sacho hse and otp perfect thyu hse to agd vdhsee details nu puchsee
    public static String current_type; // ano use currently kon online e jova as currentonlineuser or  currentsellerlineuser no use krvo emaa confuse hto
    public static int current_id;//current kyu id 6e ejova
   public static final String UserPhoneKey = "UserPhone";
   public static final String UserPasswordKey = "UserPassword";
}
