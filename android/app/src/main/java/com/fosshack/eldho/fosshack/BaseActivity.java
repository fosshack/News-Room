package com.fosshack.eldho.fosshack;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;



public class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    ImageView tv_image;

    public final void changeTitle(int toolbarId, String titlePage){
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText(Html.fromHtml("<font color='#FFFFFF'><i><strong>"+titlePage+"</strong></i></font>"));
        getSupportActionBar().setTitle("");
    }
    public final void setupToolbar(int toolbarId, String titlePage){
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText(titlePage);
       // title.setText(Html.fromHtml("<font color='#FFFFFF'><i><strong>"+titlePage+"</strong></i></font>"));

        getSupportActionBar().setTitle("");
    }

    public final void setupImage(int toolbarId){
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("");
    }


    public void setupToolbarWithUpNav(int toolbarId, String titlePage, @DrawableRes int res){
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.tv_title);
        title.setText(titlePage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(res);
        getSupportActionBar().setTitle("");
    }

}
