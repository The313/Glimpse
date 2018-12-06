package com.example.jin.java1d;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import static com.example.jin.java1d.MainActivity.DEFAULT_MESSAGE;
import static com.example.jin.java1d.newtabbed.capacity;
import static com.example.jin.java1d.newtabbed.current_capacity;
import static com.example.jin.java1d.newtabbed.categories;
import static com.example.jin.java1d.newtabbed.current_date;
import static com.example.jin.java1d.newtabbed.current_time;
import static com.example.jin.java1d.newtabbed.places;


public class tab2 extends Fragment{
    SwipeRefreshLayout refreshLayout;
    Class place = MainActivity.class;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflator.inflate(R.layout.tab1, container, false);

        LinearLayout myRoot = (LinearLayout) rootView.findViewById(R.id.tab1linear);
        for (int i = 0; i < places.length; i++) {
            if (newtabbed.capacity.get(i) < 0.99999 && capacity.get(i) != null) {
                ImageButton newbutton = new ImageButton(rootView.getContext());
                final String date = current_date.get(i);
                final String time = current_time.get(i);
                final Integer currentcapacity = current_capacity.get(i);
                int imageid = getResources().getIdentifier("labelled_" + places[i], "drawable", getContext().getPackageName());
                final String currentplace = places[i];
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
                            Intent intenttoactivity = new Intent(tab2.this.getActivity(), place);
                            intenttoactivity.putExtra("EMPTY", currentcapacity.toString());
                            intenttoactivity.putExtra("DATE", date);
                            intenttoactivity.putExtra("TIME", time);
                            startActivity(intenttoactivity);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                });

                myRoot.addView(newbutton);
            }
        }

        /*
        refreshLayout = rootView.findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(tab2.this).attach(tab2.this).commit();
            }
        });
        */


        return rootView;

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }


}
