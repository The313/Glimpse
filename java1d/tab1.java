package com.example.jin.java1d;

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

public class tab1 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflator.inflate(R.layout.tab1, container, false);

        LinearLayout myRoot = (LinearLayout) rootView.findViewById(R.id.tab1linear);
        for(int i=0; i<newtabbed.categories.length; i++){
            ImageButton newbutton = new ImageButton(rootView.getContext());
            int imageid = getResources().getIdentifier(newtabbed.categories[i],"drawable", getContext().getPackageName());
            newbutton.setImageResource(imageid);
            newbutton.setBackgroundColor(Color.WHITE);
            newbutton.setAdjustViewBounds(true);
            newbutton.setPadding(0,0,0,0);
            newbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("button", "button");
                }
            });
            //newbutton.setId(@+id/(categories[i]));
            myRoot.addView(newbutton);

        }

        return rootView;
    }


}
