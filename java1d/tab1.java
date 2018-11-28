package com.example.jin.java1d;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class tab1 extends Fragment{
    public static String[] teststring = {"firstview", "secondview", "thirdview", "fgh", "jf", "df", "fgh", "jf", "df", "fgh", "jf", "df"};
    
    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflator.inflate(R.layout.tab1, container, false);

        LinearLayout myRoot = (LinearLayout) rootView.findViewById(R.id.tab1linear);
        for(int i=0; i<teststring.length; i++){
            Button textview = new Button(rootView.getContext());
            textview.setText("Textview" + teststring[i]);
            myRoot.addView(textview);
        }

        return rootView;
    }


}
