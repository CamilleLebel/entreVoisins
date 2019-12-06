package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.openclassrooms.entrevoisins.Utils.SharedPreference;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.FavoriteNeighbourFragment;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

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

    SharedPreference sharedPreference;
    Activity activity;

    //FOR UI
    private ImageView mImageView;
    private TextView titleCardview1;
    private TextView neighbourPlace;
    private TextView neighbourPhoneNumber;
    private TextView neighbourUrlWebiste;
    private TextView neighbourDesc;

    @SuppressLint("PrivateResource")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_details);

        sharedPreference = new SharedPreference();

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

        titleCardview1 = (TextView) findViewById(R.id.title_cardview);
        titleCardview1.setText(currentNeighbour.getName());

        neighbourPlace = (TextView) findViewById(R.id.neighbour_place) ;
        neighbourPlace.setText(currentNeighbour.getName() + "'s home");

        neighbourPhoneNumber = (TextView) findViewById(R.id.neighbour_phone_number) ;
        neighbourPhoneNumber.setText(currentNeighbour.getName() + "'s phone number");

        neighbourUrlWebiste = (TextView) findViewById(R.id.neighbour_url_website) ;
        neighbourUrlWebiste.setText(currentNeighbour.getName() + "'s url website");

        neighbourDesc = (TextView) findViewById(R.id.neigbour_description);
        neighbourDesc.setText(currentNeighbour.getName() + "'s description");


        FloatingActionButton fabfav = (FloatingActionButton) findViewById(R.id.fabfav);
        fabfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tag = fabfav.getTag().toString();
                if(tag.equalsIgnoreCase("grey")){
                    sharedPreference.addFavorite(getApplicationContext(), currentNeighbour);
                    Snackbar.make(view, "Neighbour added to favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fabfav.setTag("yellow");
                    fabfav.setImageResource(R.drawable.ic_star_white_24dp);
                }else{
                    sharedPreference.removeFavorite(getApplicationContext(), currentNeighbour);
                    Snackbar.make(view, "Neighbour removed from favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fabfav.setTag("grey");
                    fabfav.setImageResource(R.drawable.ic_star_border_white_24dp);
                }




            }
        });

        try{
            if(sharedPreference.getFavorites(getApplicationContext()).contains(currentNeighbour)){
                fabfav.setTag("yellow");
                fabfav.setImageResource(R.drawable.ic_star_white_24dp);
            }else{
                fabfav.setTag("grey");
                fabfav.setImageResource(R.drawable.ic_star_border_white_24dp);
            }
        } catch (Exception e) {
            return;
        }


    }
}
