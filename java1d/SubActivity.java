package com.example.chias.crowdcheck;

import android.content.Intent;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.Toast;

public class SubActivity extends AppCompatActivity{

    Button Home;
    Button Favourite;
    public final String MAXIMUM_CAPACITY = ""; //firebase data + "/" + max capacity
    public final String LAST_UPDATE = "Last updated on: ";// + Firebase updated time
    private SwipeRefreshLayout swipeCont;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details1);

        swipeCont = (SwipeRefreshLayout) findViewById(R.id.swiperlayout);
        swipeCont.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refresh firebase capacity
            }
        });
        swipeCont.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        Home = findViewById(R.id.imageButton3);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Favourite = findViewById(R.id.imageButton4);
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

