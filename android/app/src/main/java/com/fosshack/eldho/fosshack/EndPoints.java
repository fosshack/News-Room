package com.fosshack.eldho.fosshack;

/**
 * Created by Lincoln on 06/01/16.
 */
public class EndPoints {

    // localhost url
    // public static final String BASE_URL = "http://192.168.0.101/gcm_chat/v1";
   // ifconfig

    //new  IP Address 31.170.165.167 hostinger

    public static String var="qtdvbuqxlm";
    public static String var_img_show="bshmyultig";

   public static String BASE_URL = "https://"+var+".localtunnel.me";

   public static String BASE_URL_IMG = "https://"+var_img_show+".localtunnel.me";


    public static String NEWS = BASE_URL + "/api/list";

    public static String IMG_SHOW = BASE_URL_IMG+"/";

    public static String NEWS_UP = BASE_URL+ "/api/upload";
    public static String IMAGE_DIRECTORY_NAME = "Fosshack";


}
