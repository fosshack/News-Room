package com.fosshack.eldho.fosshack;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class FriendsFragment extends Fragment {
    public static final int AUTH_REQUEST = 1001;

    private static String TAG = MainActivity.class.getSimpleName();
    // private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    //private static final int SECOND_ACTIVITY_RESULT_CODE = 153;

    public static ArrayList<ChatRoom> chatRoomArrayList;
    private static ChatRoomsAdapter mAdapter;
    private static RecyclerView recyclerView;
    static TextView emptyElement;
    // private static final int RESULT_PICK_CONTACT = 95045;
    static String phoneNo = null ;
    static String name = null;
    static String country_code=null;
    // static FloatingActionButton new_crush;
    static Bitmap bit,bmp;
    static Canvas canvas;
    private static Context context = null;
    //GlideDrawableImageViewTarget imageViewTarget;

    static RelativeLayout content_main;

    //View toastLayout_crush;
    static TextView tv;
    static View toastLayout;
    ChatRoom chatRoom;
    String chat_id;

    private Paint p = new Paint();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rv = inflater.inflate(
                R.layout.content_main, container, false);

        Log.e(TAG, "Called onCreateView ");

        recyclerView = (RecyclerView)rv.findViewById(R.id.recycler_view);



/*
        toastLayout_crush = inflater.inflate(R.layout.app_bar_main, (ViewGroup) rv.findViewById(R.id.abl));
        new_crush=(FloatingActionButton)toastLayout_crush.findViewById(R.id.crush);
        new_crush.setVisibility(View.GONE);
*/
        //new_crush=(FloatingActionButton)rv.findViewById(R.id.crush);



        content_main=(RelativeLayout)rv.findViewById(R.id.content_main);
        context=getActivity();
        //  in=1;
        /**
         * Broadcast receiver calls in two scenarios
         * 1. nest registration is completed
         * 2. when new push notification is received
         * */


        toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) rv.findViewById(R.id.custom_toast_layout));
        tv=(TextView)toastLayout.findViewById(R.id.custom_toast_message);

        chatRoomArrayList = new ArrayList<>();
        mAdapter = new ChatRoomsAdapter(context, chatRoomArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                context.getApplicationContext()
        ));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new ChatRoomsAdapter.RecyclerTouchListener(context.getApplicationContext(), recyclerView, new ChatRoomsAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // when chat is clicked, launch full chat thread activity
                ChatRoom chatRoom = chatRoomArrayList.get(position);






                Intent intent = new Intent(context, ChatRoomActivity.class);
                intent.putExtra("rid", chatRoom.getcontent());


                if (chatRoom.getS_id() == MyApplication.getInstance().getPrefManager().getUser().getId())
                {
                    intent.putExtra("rid", chatRoom.getR_id());
                }
                else {

                }
                intent.putExtra("chat_room_id", chatRoom.getChat_id());

                if(chatRoom.getS_id().equals( MyApplication.getInstance().getPrefManager().getUser().getId())) {
                    intent.putExtra("name", name.getText());
                    intent.putExtra("r_id", chatRoom.getR_id());
                }
                else
                {
                    intent.putExtra("name", name.getText());
                    intent.putExtra("r_id", chatRoom.getS_id());
                }

                if(chatRoom.getBit()!=null) {
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    //Compress it before sending it to minimize the size and quality of bitmap.
                    // chatRoom.getBit().getBitmap().compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    chatRoom.getBit().compress(Bitmap.CompressFormat.PNG, 100, bStream);

                    byte[] byteArray = bStream.toByteArray();

                    intent.putExtra("image", byteArray);
                }

                intent.putExtra("number", chatRoom.getNumber());

                intent.putExtra("flag", chatRoom.getFlag());
                intent.putExtra("inst", Integer.toString(chatRoom.getInst()));
                intent.putExtra("msg_left", MyApplication.getInstance().getPrefManager().getMsgLeft());
                intent.putExtra("rechrg", MyApplication.getInstance().getPrefManager().getRechrge());



               // Log.e(TAG, "rechrg 1 "+chatRoom.getRechrg());
              //  Log.e(TAG, "msg_left 1 "+chatRoom.getMsg_left());
                Log.e(TAG, "inst 1 "+chatRoom.getInst());

//else
                {
                    //send the random color obtained
                    //   intent.putExtra("color", chatRoom.getColor());
                    //  Log.e(TAG, "color 1"+Integer.toString(chatRoom.getColor()));



                }
                // ChatRoomActivity.date="";
                getActivity().startActivityForResult(intent, 153);

                //  startActivity(intent);

                */
            }

            @Override
            public void onLongClick(View view, int position) {
                //code for blocking the person




/*
                 chatRoom = chatRoomArrayList.get(position);
                chat_id=chatRoom.getChat_id();





                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //builder.setMessage("Are you sure you want to block the person ?")
                builder.setMessage(Html.fromHtml(" "+"<font color='#E91E63'>Are you sure you want to block "+chatRoom.getName()+" ?</font>"))
                        .setCancelable(false)
                        //.setPositiveButton(Html.fromHtml("YES"), new DialogInterface.OnClickListener() {
                            .setPositiveButton(Html.fromHtml(" "+"<font color='#E91E63'> YES </font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(isNetworkAvailable(context.getApplicationContext()))
                                {
                                    block();
                                    //crush_mesg(5);
                                    //Toast.makeText(MainActivity.this, "You has blocked this person", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    //Toast.makeText(MainActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                                    tv.setText("Check your Internet Connection");
                                    Toast toast = new Toast(context);
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(toastLayout);
                                    toast.show();
                                }

                            }
                        })
                        //.setNegativeButton(Html.fromHtml("NO"), new DialogInterface.OnClickListener() {
                            .setNegativeButton(Html.fromHtml(" "+"<font color='#E91E63'> NO </font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


*/



            }
        }));
        recyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }
            @Override
            public void onShow() {
                showViews();
            }
        });

        /**
         * Always check for google play services availability before
         * proceeding further with GCM
         * */
        // if (checkPlayServices()) {
        // registerGCM();
/*
        View mView = inflater.inflate(R.layout.user_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        alertDialogBuilderUserInput.setView(mView);

        final EditText compliant_id = (EditText) mView.findViewById(R.id.comp_id);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        // ToDo get user input here

                        if (!compliant_id.getText().toString().equals("")) {

                            // EndPoints.var=compliant_id.getText().toString();

                            MyApplication.getInstance().getPrefManager().storeIP(compliant_id.getText().toString());

                            if (isNetworkAvailable(context))
                            {

                                fetchnews();
                            }
                            else
                            {
                                // Toast.makeText(context, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                                tv.setText("Check your Internet Connection");
                                Toast toast = new Toast(context);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(toastLayout);
                                toast.show();
                            }

                        }
                        else
                        {
                            tv.setText("Enter IP ");
                            Toast toast = new Toast(context);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(toastLayout);
                            toast.show();
                        }

                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();


*/




          /*
            emptyElement.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            Log.e(TAG, "got inside 1");
            fetchChatRooms();
            Log.e(TAG, "got inside 2");
            if (chatRoomArrayList.isEmpty())
            {
                emptyElement.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Log.e(TAG, "got inside 3");
            }
            */
        // }



        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.RIGHT){

                    chatRoom = chatRoomArrayList.get(position);


                    tv.setText("Marked as further..");
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(toastLayout);
                    toast.show();



                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;

                    //Paint p = new Paint();

                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    if (dX > 0) {
            /* Set your color for positive displacement */

                        p.setColor(getResources().getColor(R.color.colorAccent));

                        // Draw Rect with varying right side, equal to displacement dX
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);

                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_block_white);

                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
/*

                        //seperation

                        p.setColor(getResources().getColor(R.color.colorAccent));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.block_user);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
*/

                    }
                    /*
                    else {

                        p.setColor(getResources().getColor(R.color.colorAccent));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.block_user);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);

                        //seperation

                        p.setColor(getResources().getColor(R.color.colorAccent));

                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom(), p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_block_white);

                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
*/
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);






        return rv;

    }


    private void hideViews() {
        //MainActivity.appBarLayout.animate().translationY(-MainActivity.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        MainActivity.toolbar.animate().translationY(-MainActivity.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        //  MainActivity.tabLayout.animate().translationY(-MainActivity.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));


        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) MainActivity.photo.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;

        MainActivity.photo.animate().translationY(MainActivity.photo.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
        //new_crush.setVisibility(View.GONE);

    }

    private void showViews() {
        // MainActivity.appBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

        MainActivity.toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        MainActivity.tabLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

        MainActivity.photo.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        //    new_crush.setVisibility(View.VISIBLE);

    }


    public static boolean isNetworkAvailable(Context ct)
    {
        //Log.w("myApp", "iside network available");

        ConnectivityManager cm = (ConnectivityManager) ct.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }

    /**
     * Updates the chat list unread count and the last message
     */




    /**
     * fetching the chat rooms by making http call
     */
    public static void fetchnews() {

        Log.e(TAG, "Called chatrooms ");




        StringRequest strReq = new StringRequest(Request.Method.POST,
                EndPoints.NEWS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: crush " + response);

                chatRoomArrayList.clear();




                try {
                    //JSONObject obj = new JSONObject(response);





                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);

                        JSONObject nw = jsonobject.getJSONObject("fields");


                        ChatRoom cr = new ChatRoom();
                        cr.setmodels(jsonobject.getString("models"));
                        cr.settitle(nw.getString("title"));
                        cr.setpk(jsonobject.getString("pk"));
                        cr.setcontent(nw.getString("content"));
                        cr.setlon(nw.getString("lon"));
                        cr.setlan(nw.getString("lan"));
                        cr.setimg("https://"+EndPoints.IMG_SHOW+nw.getString("img"));
                        cr.setupvotes(nw.getString("upvotes"));
                        cr.setdownvotes(nw.getString("downvotes"));
                        cr.setoriginality(nw.getString("originality"));

                        chatRoomArrayList.add(cr);

                    }



                    // check for error flag

                    // ChatRoomsAdapter.i=1;
                    /*

                        JSONArray chatRoomsArray = obj.getJSONArray("model");
                        //chatRoomArrayList=null;

                        for (int i = 0; i < chatRoomsArray.length(); i++)
                        {
                            JSONObject chatRoomsObj = (JSONObject) chatRoomsArray.get(i);
                            ChatRoom cr = new ChatRoom();
                            cr.setS_name(chatRoomsObj.getString("s_name"));
                            cr.setR_name(chatRoomsObj.getString("r_name"));
                            cr.setS_id(chatRoomsObj.getString("s_id"));
                            cr.setR_id(chatRoomsObj.getString("r_id"));
                            cr.setStatus(chatRoomsObj.getString("status"));
                            cr.setChat_id(chatRoomsObj.getString("chat_id"));
                            cr.setLast_msg(chatRoomsObj.getString("last_msg"));
                            cr.setTimestamp(chatRoomsObj.getString("created_at"));
                            cr.setLast_msg_s_id(chatRoomsObj.getString("last_msg_sender_id"));



                            chatRoomArrayList.add(cr);


                        }

*/






                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    //  Toast.makeText(context.getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                //  mAdapter.notifyDataSetChanged();






            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                //  Toast.makeText(context.getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {



            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                // Log.e(TAG, "params: " + params.toString());
                return params;
            }
        };


        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);



    }



    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int color = Color.RED;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    public static Bitmap getContactsDetails(Context context,String address) {
        //Bitmap bp = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher);
        Bitmap bp=null;




        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));

        // querying contact data store
        Cursor phones = context.getContentResolver().query(contactUri, null, null, null, null);


        while (phones.moveToNext()) {
            String image_uri = phones.getString(phones.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            if (image_uri != null) {

                try {
                    bp = MediaStore.Images.Media
                            .getBitmap(context.getContentResolver(),
                                    Uri.parse(image_uri));

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        phones.close();
        return   bp;

    }











/*
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

*/

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_logout:
                MyApplication.getInstance().logout();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

*/



}

