package com.example.jin.java1d;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity{

    ImageButton Home;
    ImageButton Favourite;
    int firebasecapacity = 0;
    int maxcapacity = 100;   //TODO: SET MAX CAPACITY BASED ON LOCATION
    int maxcapacitywidth;
    public final String LAST_UPDATE = "Last updated on: " + "" + " (" + firebasecapacity + "/" + maxcapacity + ")";//TODO: + Firebase updated time
    private SwipeRefreshLayout swipeCont;
    TextView LastUpdate;
    ImageView progressback;
    ImageView progressfront;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swimming_pool);

        LastUpdate = findViewById(R.id.textView14);

        swipeCont = (SwipeRefreshLayout) findViewById(R.id.swiperlayout);
        swipeCont.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //TODO: refresh firebase capacity
                progressback.setMaxWidth(firebasecapacity/maxcapacity * maxcapacitywidth);
                LastUpdate.setText(LAST_UPDATE);
            }
        });
        swipeCont.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        progressfront = findViewById(R.id.progressfront);
        maxcapacitywidth = progressfront.getMaxWidth();
        //read data from firebase
        progressback = findViewById(R.id.progressback);
        progressback.setMaxWidth(firebasecapacity/maxcapacity * maxcapacitywidth);



        Home = findViewById(R.id.home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Favourite = findViewById(R.id.favourite);
        Favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if not in favourite list
                    //add to favourites
                    Toast.makeText(SubActivity.this, "Added to Favourites!", Toast.LENGTH_SHORT).show();
                //else
                    //remove from favourites
                    //Toast.makeText(subActivity.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
