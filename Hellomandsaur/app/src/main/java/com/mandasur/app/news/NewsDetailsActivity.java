package com.mandasur.app.news;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mandasur.app.R;
import com.mandasur.app.util.MandsaurNewsTextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ambesh on 11-02-2017.
 */
public class NewsDetailsActivity extends AppCompatActivity implements NewsDetailContract.NewsDetailView{


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
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void showNewsDetailsToScreen() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void showNewsImage() {

    }

    @Override
    public void showNetworkNotAvaible() {

    }

    @Override
    public void setPresenter(NewsDetailContract.NewsDetailsPresenter presenter) {

    }
}
