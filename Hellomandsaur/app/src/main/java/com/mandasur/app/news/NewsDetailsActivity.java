package com.mandasur.app.news;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mandasur.app.R;
import com.mandasur.app.util.MandsaurNewsTextView;

/**
 * Created by ambesh on 11-02-2017.
 */
public class NewsDetailsActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View view=getLayoutInflater().inflate(R.layout.layout_custom_tool_bar_layout,null);
        MandsaurNewsTextView homeAsUpIcon= (MandsaurNewsTextView) view.findViewById(R.id.homeAsUpIcon);

        homeAsUpIcon.setText(getString(R.string.textArrowIcon));
//        toolbar.addView(view);
    }
}
