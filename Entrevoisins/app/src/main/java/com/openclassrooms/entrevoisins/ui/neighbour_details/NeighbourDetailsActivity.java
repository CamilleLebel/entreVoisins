package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material;

public class NeighbourDetailsActivity extends AppCompatActivity {

    public static int idData;
    public static String nameData;
    public static String avatarData;

    private Neighbour currentNeighbour;

    //FOR UI
    private ImageView mImageView;
    private TextView mTextView;

    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        mImageView = (ImageView) findViewById(R.id.backgroundImageView);

        idData = this.getIntent().getExtras().getInt("current_id");
        nameData = this.getIntent().getExtras().getString("current_name");
        avatarData = this.getIntent().getExtras().getString("current_avatar");
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(avatarData)
                .into(mImageView);

        currentNeighbour = new Neighbour(idData, nameData, avatarData);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.ctoolbar);
        collapsingToolbar.setTitle(currentNeighbour.getName());

        mTextView = (TextView) findViewById(R.id.description_text);
        mTextView.setText(R.string.large_text);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }
}
