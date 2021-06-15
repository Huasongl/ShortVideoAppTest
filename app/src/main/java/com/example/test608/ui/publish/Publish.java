package com.example.test608.ui.publish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.libnavannotation.ActivityDestination;
import com.example.test608.R;

@ActivityDestination(pageUrl = "main/tabs/publish")
public class Publish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
    }
}