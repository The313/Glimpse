package com.example.jin.java1d;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Integer.parseInt;

public class ish2 extends AppCompatActivity{

    ImageButton Home;
    ImageButton Favourite;
    TextView Name;
    TextView CurrentCapacity;
    TextView OpeningHours;
    TextView LastUpdated;
    TextView Location;
    ImageView picture;
    Boolean favon;
    String capacity;
    Integer current_capacity_num;
    Float percentage;
    public final Integer MAXIMUM_CAPACITY = 10;
    private SwipeRefreshLayout swipeCont;

    private final String sharedPrefFile = "com.example.jin.java1d.res.xml.sharedpreferences";
    public static final String ISHTWO = "ish2";
    SharedPreferences mPreferences;



    String openinghours = "Opening Hours: 10AM - 9PM";
    String name = "Indoor Sports Hall 2";
    String lastupdated = "Last Updated on: ";
    String location = "Address: 61 Changi South Ave 1";
    String current_capacity = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ish2);

        Intent intent = getIntent();
        String currentcap = intent.getStringExtra("EMPTY");
        Log.i("firebase", "currentcap = " + currentcap);

        picture = findViewById(R.id.mainpicture);
        picture.setImageResource(R.drawable.unlabelled_ish2);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference firstfield = database.child("java1d-78754");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    //Log.i("firebase", ds.getKey() + ds.getValue());
                    if (ds.getKey().equals("ish2")) {
                        capacity = ds.getValue().toString();
                        capacity = capacity.replace("{", "").replace("}", "");
                        current_capacity_num = (parseInt(capacity.split("=")[2]));
                        percentage = (float) (current_capacity_num / MAXIMUM_CAPACITY);         //setting current capacity of space bar
                    }
                }
            }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.i("firebase", "Failed to read value.", error.toException());
                }
            });

        swipeCont = (SwipeRefreshLayout) findViewById(R.id.swiperlayout);
        swipeCont.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CurrentCapacity.setText(current_capacity_num.toString());
            }
        });

        swipeCont.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Boolean Rate_text = mPreferences.getBoolean(ISHTWO,false);
        favon = Rate_text;



        Home = findViewById(R.id.home);
        Home.setBackgroundDrawable(null);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ish2.this, newtabbed.class);
                startActivity(intent);
            }
        });
        Favourite = findViewById(R.id.favourite);
        if(!favon){
            Favourite.setImageResource(R.drawable.emptyheart);
        } else {
            Favourite.setImageResource(R.drawable.redheart);
        }
        Favourite.setBackgroundDrawable(null);
        Favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!favon) {
                    Favourite.setImageResource(R.drawable.redheart);
                    favon = true;
                    Toast.makeText(ish2.this, "Added to Favourites!", Toast.LENGTH_SHORT).show();
                } else {
                    Favourite.setImageResource(R.drawable.emptyheart);
                    favon = false;
                    Toast.makeText(ish2.this, "Removed from Favourites!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Name = findViewById(R.id.name);
        Name.setText(name);

        OpeningHours = findViewById(R.id.OpeningHours);
        OpeningHours.setText(openinghours);

        CurrentCapacity = findViewById(R.id.CurrentCapacity);
        CurrentCapacity.setText(currentcap);

        LastUpdated = findViewById(R.id.FirebaseLastUpdated);
        LastUpdated.setText(lastupdated);

        Location = findViewById(R.id.Location);
        Location.setText(location);



    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean(ISHTWO, favon);
        preferencesEditor.apply();
    }
}

