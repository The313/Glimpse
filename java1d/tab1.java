package com.example.jin.java1d;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.example.jin.java1d.newtabbed.categories;

public class tab1 extends Fragment{
    boolean first = true;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflator.inflate(R.layout.tab1, container, false);

        LinearLayout myRoot = (LinearLayout) rootView.findViewById(R.id.tab1linear);
        for(int i = 0; i< categories.length; i++){
            ImageButton newbutton = new ImageButton(rootView.getContext());
            int imageid = getResources().getIdentifier("category_" + categories[i],"drawable", getContext().getPackageName());
            newbutton.setImageResource(imageid);
            newbutton.setBackgroundColor(Color.WHITE);
            newbutton.setAdjustViewBounds(true);
            newbutton.setPadding(0,0,0,0);
            final String category = categories[i];
            if(first){
                newbutton.setPadding(0,10,0,0);
                first = false;
            }
            newbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        final Class<?> cat = Class.forName("com.example.jin.java1d." + category);
                        Intent intenttoactivity = new Intent(tab1.this.getActivity(), cat);
                        startActivity(intenttoactivity);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            myRoot.addView(newbutton);

        }

        return rootView;
    }


}
