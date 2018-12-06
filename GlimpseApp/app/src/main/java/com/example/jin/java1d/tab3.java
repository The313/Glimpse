package com.example.jin.java1d;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import static com.example.jin.java1d.newtabbed.capacity;
import static com.example.jin.java1d.newtabbed.current_capacity;
import static com.example.jin.java1d.newtabbed.current_date;
import static com.example.jin.java1d.newtabbed.current_time;
import static com.example.jin.java1d.newtabbed.favourites;
import static com.example.jin.java1d.newtabbed.places;

public class tab3 extends Fragment{
    SwipeRefreshLayout refreshLayout;
    Class place = MainActivity.class;
    Integer index = 0;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflator.inflate(R.layout.tab3, container, false);


        final LinearLayout myRoot = (LinearLayout) rootView.findViewById(R.id.tab3linear);
        for (int i = 0; i < favourites.size(); i++) {
            for (int j=0;j<places.length;j++) {
                if (places[j].equals(favourites.get(i))) {
                    index = j;
                    break;
                }
            }
            if (capacity.get(index) != null) {
                ImageButton newbutton = new ImageButton(rootView.getContext());
                final String date = current_date.get(index);
                final String time = current_time.get(index);
                final Integer currentcapacity = current_capacity.get(index);
                int imageid = getResources().getIdentifier("labelled_" + places[index], "drawable", getContext().getPackageName());
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
                            Intent intenttoactivity = new Intent(tab3.this.getActivity(), place);
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
