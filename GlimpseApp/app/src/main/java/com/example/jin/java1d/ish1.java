package com.example.jin.java1d;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Integer.parseInt;
import static uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap;

public class ish1 extends AppCompatActivity{

    ImageView BlueLogo;
    ImageView BlueBanner;
    ImageButton Home;
    ImageButton Favourite;
    TextView Name;
    TextView CurrentCapacity;
    TextView OpeningHours;
    TextView LastUpdated;
    TextView Location;
    TextView Rates;
    TextView Number;
    TextView Subname;
    ImageView picture;
    Boolean favon;
    String capacity;
    Integer current_capacity_num;
    Double percentage;
    ImageButton map;
    public final Double MAXIMUM_CAPACITY = 30.0;
    private SwipeRefreshLayout swipeCont;

    private final String sharedPrefFile = "com.example.jin.java1d.res.xml.sharedpreferences";
    public static final String ISHONE = "ish1";
    SharedPreferences mPreferences;

    ProgressBar mprogressbar;
    int mprogressstatus = 0;
    private Handler mHandler = new Handler();


    String openinghours = "10AM - 9PM";
    String name = "Indoor Sports Hall";
    String lastupdated = "Last Updated on: ";
    String location = "61 Changi South Ave 1";
    String rates = "Free admission";
    String phonenumber = "+65 64998927";
    String subname = "Hall 1";
    String lastupdateddate;
    String lastupdatetime;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ish1);

        Intent intent = getIntent();
        final String date = intent.getStringExtra("DATE");
        final String time = intent.getStringExtra("TIME");
        final String currentcap = intent.getStringExtra("EMPTY");
        Log.i("firebase", "currentcap = " + currentcap);

        picture = findViewById(R.id.mainpicture);
        picture.setImageResource(R.drawable.unlabelled_ish1);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    //Log.i("firebase", ds.getKey() + ds.getValue());
                    if (ds.getKey().equals("ish1")) {
                        capacity = ds.getValue().toString();
                        capacity = capacity.replace("{", "").replace("}", "");
                        current_capacity_num = (parseInt(capacity.split("=")[2]));
                        percentage = (current_capacity_num / MAXIMUM_CAPACITY);
                        lastupdateddate = capacity.split("=")[0];
                        lastupdatetime = capacity.split("=")[1];
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("firebase", "Failed to read value.", error.toException());
            }
        });


        mprogressbar = (ProgressBar) findViewById(R.id.progressbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mprogressstatus < (int)((parseInt(currentcap)/MAXIMUM_CAPACITY)*100)){
                    mprogressstatus++;
                    android.os.SystemClock.sleep(40);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mprogressbar.setProgress(mprogressstatus);
                        }
                    });
                }
            }
        }).start();

        swipeCont = (SwipeRefreshLayout) findViewById(R.id.swiperlayout);
        swipeCont.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                long max_num = Math.round(MAXIMUM_CAPACITY);
                CurrentCapacity.setText(current_capacity_num + "/" + max_num + " people");
                LastUpdated.setText("Last Updated At: " + lastupdateddate + ", " + lastupdatetime);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(mprogressstatus < (int)((current_capacity_num/MAXIMUM_CAPACITY)*100)){
                            mprogressstatus++;
                            android.os.SystemClock.sleep(40);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mprogressbar.setProgress(mprogressstatus);
                                }
                            });
                        }
                        while(mprogressstatus > (int)((current_capacity_num/MAXIMUM_CAPACITY)*100)){
                            mprogressstatus--;
                            android.os.SystemClock.sleep(40);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mprogressbar.setProgress(mprogressstatus);
                                }
                            });
                        }
                    }
                }).start();


                if (swipeCont.isRefreshing()) {
                    swipeCont.setRefreshing(false);
                }
            }
        });

        swipeCont.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Boolean Rate_text = mPreferences.getBoolean(ISHONE,false);
        favon = Rate_text;

        BlueBanner = findViewById(R.id.bluebanner);
        BlueBanner.setImageResource(R.drawable.greenbackground);
        BlueLogo = findViewById(R.id.bluelogo);
        BlueLogo.setImageResource(R.drawable.blanklogo);



        Home = findViewById(R.id.home);
        Home.setBackground(null);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ish1.this, newtabbed.class);
                startActivity(intent);
            }
        });
        Favourite = findViewById(R.id.favourite);
        if(!favon){
            Favourite.setImageResource(R.drawable.emptyheart);
        } else {
            Favourite.setImageResource(R.drawable.redheart);
        }
        Favourite.setBackground(null);
        Favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!favon) {
                    Favourite.setImageResource(R.drawable.redheart);
                    favon = true;
                    Toast.makeText(ish1.this, "Added to Favourites!", Toast.LENGTH_SHORT).show();
                } else {
                    Favourite.setImageResource(R.drawable.emptyheart);
                    favon = false;
                    Toast.makeText(ish1.this, "Removed from Favourites!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CurrentCapacity = findViewById(R.id.CurrentCapacity);
        long max_num = Math.round(MAXIMUM_CAPACITY);
        CurrentCapacity.setText(currentcap + "/" + max_num + " people");

        Name = findViewById(R.id.name);
        Name.setText(name);

        OpeningHours = findViewById(R.id.textView17);
        OpeningHours.setText(openinghours);
        OpeningHours.setPadding(80,10,0,0);


        LastUpdated = findViewById(R.id.FirebaseLastUpdated);
        LastUpdated.setText("Last Updated At: " + date + ", " + time);

        Location = findViewById(R.id.textView21);
        Location.setText(location);
        Location.setPadding(80,0,0,0);

        Rates = findViewById(R.id.textView19);
        Rates.setText(rates);
        Rates.setPadding(80,0,0,0);

        Number = findViewById(R.id.textView24);
        Number.setText(phonenumber);
        Number.setPadding(80,0,0,0);

        Subname = findViewById(R.id.subname);
        Subname.setText(subname);


        map = findViewById(R.id.map);
        map.setBackground(null);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("LOGCAT", "onClick()");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(buildURL(getText(R.string.ish1_id).toString())));
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean(ISHONE, favon);
        preferencesEditor.apply();
    }

    private String buildURL(String placeID) {
        return "https://sutdmap.appspot.com/?id=" + placeID;
    }
}

