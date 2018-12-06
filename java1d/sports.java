package com.example.jin.java1d;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import static com.example.jin.java1d.newtabbed.current_capacity;
import static com.example.jin.java1d.newtabbed.current_date;
import static com.example.jin.java1d.newtabbed.current_time;
import static com.example.jin.java1d.newtabbed.places;
import static com.example.jin.java1d.newtabbed.sportslist;

public class sports extends AppCompatActivity{

    Integer index = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sports);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setBackgroundColor(Color.parseColor("#56c09c"));
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.drawable.banner2_sports);

        Integer index =0;

        LinearLayout layout = findViewById(R.id.sportslinear2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < sportslist.length; i++) {
            for (int j=0;j<places.length;j++) {
                if (places[j].equals(sportslist[i])) {
                    index = j;
                    break;
                }
            }

            ImageButton newbutton = new ImageButton(this);
            final String date = current_date.get(index);
            final String time = current_time.get(index);
            final Integer currentcapacity = current_capacity.get(index);
            int imageid = getResources().getIdentifier("labelled_" + places[index], "drawable", this.getPackageName());
            final String currentplace = places[index];
            newbutton.setImageResource(imageid);
            newbutton.setBackgroundColor(Color.WHITE);
            newbutton.setAdjustViewBounds(true);
            newbutton.setPadding(0, 0, 0, 0);

            newbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("button", "button");
                    try {
                        final Class<?> place = Class.forName("com.example.jin.java1d." + currentplace);
                        Intent intenttoactivity = new Intent(sports.this, place);
                        intenttoactivity.putExtra("EMPTY", currentcapacity.toString());
                        intenttoactivity.putExtra("DATE", date);
                        intenttoactivity.putExtra("TIME", time);
                        startActivity(intenttoactivity);
                    } catch (ClassNotFoundException e) {
                        Log.i("firebase", "failed");
                        e.printStackTrace();
                    }
                }

            });

            layout.addView(newbutton);
        }
    }

}
