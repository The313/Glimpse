package com.example.jin.java1d;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class newtabbed extends AppCompatActivity {
    String soccer_court;
    Integer soccer_court_capacity;
    String pantry;
    Integer pantry_capacity;
    String ish1;
    Integer ish1_capacity;
    String ish2;
    Integer ish2_capacity;
    String meeting_room1;
    Integer meeting_room1_capacity;
    String rock_climbing_wall;
    Integer rock_climbing_wall_capacity;
    String swimming_pool;
    Integer swimming_pool_capacity;
    String fitness_centre;
    Integer fitness_centre_capacity;
    String one_occupancy;
    String one_time;
    SwipeRefreshLayout refreshLayout;
    String firebaseData;
    TextView dataText;

    Integer max_pantry_capacity = 4;
    Integer max_ish1_capacity = 10;
    Integer max_ish2_capacity = 10;
    Integer max_meeting_room1_capacity = 5;
    Integer max_rock_climbing_capacity = 6;
    Integer max_swimming_pool_capacity = 20;
    Integer max_soccer_court_capacity = 10;
    Integer max_fitness_centre = 6;

    public static ArrayList<Float> capacity = new ArrayList<Float>();
    public static ArrayList<Integer> current_capacity = new ArrayList<Integer>();
    public static ArrayList<String> favourites = new ArrayList<String>();
    public static String[] categories = {"indoor", "outdoor", "sports", "study"};
    public static String[] places = {"pantry","soccer_court", "ish1", "ish2", "meeting_room1", "swimming_pool", "rock_climbing_wall", "fitness_centre"};

    private final String sharedPrefFile = "com.example.jin.java1d.res.xml.sharedpreferences";
    public static final String FITNESSCENTRE = "fitness_centre";
    public static final String ISHONE = "ish1";
    public static final String ISHTWO = "ish2";
    public static final String MEETINGRM = "meeting_room1";
    public static final String PANTRY = "pantry";
    public static final String POOL = "swimming_pool";
    public static final String SOCCERCOURT = "soccer_court";
    public static final String ROCKWALL = "rock_climbing_wall";
    SharedPreferences mPreferences;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        capacity.addAll(Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f));
        current_capacity.addAll(Arrays.asList(0,0,0,0,0,0,0,0));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtabbed);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Boolean fitness_centre_fav = mPreferences.getBoolean(FITNESSCENTRE,false);
        Boolean ish1_fav = mPreferences.getBoolean(ISHONE,false);
        Boolean ish2_fav = mPreferences.getBoolean(ISHTWO,false);
        Boolean pantry_fav = mPreferences.getBoolean(PANTRY,false);
        Boolean swimming_pool_fav = mPreferences.getBoolean(POOL,false);
        Boolean rock_climbing_wall_fav = mPreferences.getBoolean(ROCKWALL,false);
        Boolean soccer_court_fav = mPreferences.getBoolean(SOCCERCOURT,false);
        Boolean meeting_room1_fav = mPreferences.getBoolean(MEETINGRM,false);
        if (fitness_centre_fav) {
            favourites.remove("fitness_centre");
            favourites.add("fitness_centre");
        } else {
            favourites.remove("fitness_centre");
        }
        if (ish1_fav) {
            favourites.remove("ish1");
            favourites.add("ish1");
        } else {
            favourites.remove("ish1");
        }
        if (ish2_fav) {
            favourites.remove("ish2");
            favourites.add("ish2");
        } else {
            favourites.remove("ish2");
        }
        if (pantry_fav) {
            favourites.remove("pantry");
            favourites.add("pantry");
        } else {
            favourites.remove("pantry");
        }
        if (meeting_room1_fav) {
            favourites.remove("meeting_room1");
            favourites.add("meeting_room1");
        } else {
            favourites.remove("meeting_room1");
        }
        if (rock_climbing_wall_fav) {
            favourites.remove("rock_climbing_wall");
            favourites.add("rock_climbing_wall");
        } else {
            favourites.remove("rock_climbing_wall");
        }
        if (swimming_pool_fav) {
            favourites.remove("swimming_pool");
            favourites.add("swimming_pool");
        } else {
            favourites.remove("swimming_pool");
        }
        if (soccer_court_fav) {
            favourites.remove("soccer_court");
            favourites.add("soccer_court");
        } else {
            favourites.remove("soccer_court");
        }






        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds: dataSnapshot.getChildren()) {

                    //Log.i("firebase", ds.getKey() + ds.getValue());
                    if(ds.getKey().equals("pantry")){
                        pantry = ds.getValue().toString();
                        pantry = pantry.replace("{", "").replace("}", "");
                        pantry_capacity = parseInt(pantry.split("=")[2]);
                        current_capacity.set(0, pantry_capacity);
                        capacity.set(0, (float)pantry_capacity/max_pantry_capacity);         //setting current capacity of space bar
                    }
                    if(ds.getKey().equals("soccer_court")){
                        soccer_court = ds.getValue().toString();
                        soccer_court = soccer_court.replace("{", "").replace("}","");
                        soccer_court_capacity = parseInt(soccer_court.split("=")[2]);
                        current_capacity.set(1, soccer_court_capacity);
                        capacity.set(1, (float)(soccer_court_capacity/max_soccer_court_capacity));
                    }
                    if(ds.getKey().equals("ish1")){
                        ish1 = ds.getValue().toString();
                        ish1 = ish1.replace("{", "").replace("}", "");
                        ish1_capacity = parseInt(ish1.split("=")[2]);
                        current_capacity.set(2, ish1_capacity);
                        capacity.set(2, (float)(ish1_capacity/max_ish1_capacity));
                    }
                    if(ds.getKey().equals("ish2")){
                        ish2 = ds.getValue().toString();
                        ish2 = ish2.replace("{", "").replace("}", "");
                        ish2_capacity = parseInt(ish2.split("=")[2]);
                        current_capacity.set(3, ish2_capacity);
                        capacity.set(3, (float)(ish2_capacity/max_ish2_capacity));
                    }
                    if(ds.getKey().equals("meeting_room1")){
                        meeting_room1 = ds.getValue().toString();
                        meeting_room1 = meeting_room1.replace("{", "").replace("}", "");
                        meeting_room1_capacity = parseInt(meeting_room1.split("=")[2]);
                        current_capacity.set(4, meeting_room1_capacity);
                        capacity.set(4, (float)(meeting_room1_capacity/max_meeting_room1_capacity));
                    }
                    if(ds.getKey().equals("swimming_pool")){
                        swimming_pool = ds.getValue().toString();
                        swimming_pool = swimming_pool.replace("{", "").replace("}", "");
                        swimming_pool_capacity = parseInt(swimming_pool.split("=")[2]);
                        current_capacity.set(5, swimming_pool_capacity);
                        capacity.set(5, (float)(swimming_pool_capacity/max_swimming_pool_capacity));
                    }
                    if(ds.getKey().equals("rock_climbing_wall")){
                        rock_climbing_wall = ds.getValue().toString();
                        rock_climbing_wall = rock_climbing_wall.replace("{", "").replace("}", "");
                        rock_climbing_wall_capacity = parseInt(rock_climbing_wall.split("=")[2]);
                        current_capacity.set(6, rock_climbing_wall_capacity);
                        capacity.set(6, (float)(rock_climbing_wall_capacity/max_rock_climbing_capacity));
                    }

                    if (ds.getKey().equals("fitness_centre")) {
                        fitness_centre = ds.getValue().toString();
                        fitness_centre = fitness_centre.replace("{", "").replace("}", "");
                        fitness_centre_capacity = parseInt(fitness_centre.split("=")[2]);
                        current_capacity.set(7, fitness_centre_capacity);
                        capacity.set(7, (float)(fitness_centre_capacity/max_fitness_centre));
                    }
                }

                Log.i("firebase", "places: " + places);
                Log.i("firebase", "currentcapacity: " + current_capacity);


            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("firebase", "Failed to read value.", error.toException());
            }
        });




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_newtabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
/*    public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_newtabbed, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new tab1();
                case 1:
                    return new tab2();
                case 2:
                    return new tab3();
                default:
                    return new tab1();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch(position){
                case 0:
                    return "categories";
                case 1:
                    return "available";
                case 2:
                    return "favourites";
                default:
                    return "categories";
            }
        }

    }
}
