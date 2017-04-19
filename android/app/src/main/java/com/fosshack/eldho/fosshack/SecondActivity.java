package com.fosshack.eldho.fosshack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import static com.fosshack.eldho.fosshack.R.id.desc;
import static com.fosshack.eldho.fosshack.R.id.myImageView;


public class SecondActivity extends AppCompatActivity {


TextView title,disc;
    NetworkImageView image;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        disc=(TextView)findViewById(R.id.disc);
        title=(TextView)findViewById(R.id.title);
        image=(NetworkImageView) findViewById(R.id.image);


        Intent i = getIntent();

        // image or video path that is captured in previous activity
       title.setText(i.getStringExtra("title"));
       disc.setText(i.getStringExtra("disc"));

        image.setImageUrl(i.getStringExtra("url"), imageLoader);

        // boolean flag to identify the media type, image or video
        boolean isImage = i.getBooleanExtra("isImage", true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Second Activity");
    }


}
