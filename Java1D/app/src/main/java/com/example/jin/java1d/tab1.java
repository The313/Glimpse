package com.example.jin.java1d;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class tab1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflator.inflate(R.layout.tab1, container, false);
        return rootView;
    }
}
