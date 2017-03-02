package com.mandasur.app.news;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.requestdao.NewsDetail;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailsFromResponse;
import com.mandasur.app.util.ActivityUtil;
import com.mandasur.app.util.MandsaurNewsTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ambesh on 11-02-2017.
 */
public class NewsDetailsActivity extends AppCompatActivity implements NewsDetailContract.NewsDetailView{

    public static final String NEWS_ID="newsId";
    private ImageView image1,image2;
    private MandsaurNewsTextView titleNewsTv,dateTv,consisenewsTv,newsDetailsPart1Tv,newsDetailsPart2Tv;
    private FloatingActionButton bookmarkFb,shareFb;
    private CoordinatorLayout newsDetailParent;
    private ProgressBar progressIndicator;



    private NewsDetailContract.NewsDetailsPresenter newsDetailPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String newsId=getIntent().getStringExtra(NEWS_ID);
        View view=getLayoutInflater().inflate(R.layout.layout_custom_tool_bar_layout,null);
        MandsaurNewsTextView homeAsUpIcon= (MandsaurNewsTextView) view.findViewById(R.id.homeAsUpIcon);
        newsDetailPresenter= new NewsDetailPresenter(Injector.getNewsDetailsFromServer(this),this,newsId);
        homeAsUpIcon.setText(getString(R.string.textArrowIcon));
        intiateUI();
        fetchNewsDetsil();
//        toolbar.addView(view);
    }

    private void fetchNewsDetsil() {
        newsDetailPresenter.fetchNewsDetailsFromServer();
    }

    private void intiateUI(){
        image1= (ImageView) findViewById(R.id.image1);
        image2= (ImageView) findViewById(R.id.image2);
        titleNewsTv= (MandsaurNewsTextView) findViewById(R.id.titleNewsTv);
        dateTv= (MandsaurNewsTextView) findViewById(R.id.dateTv);

        consisenewsTv= (MandsaurNewsTextView) findViewById(R.id.consisenewsTv);
        newsDetailsPart1Tv= (MandsaurNewsTextView) findViewById(R.id.newsDetailsPart1Tv);
        newsDetailsPart2Tv= (MandsaurNewsTextView) findViewById(R.id.newsDetailsPart2Tv);
        bookmarkFb= (FloatingActionButton) findViewById(R.id.bookmarkFb);
        shareFb= (FloatingActionButton) findViewById(R.id.shareFb);
        newsDetailParent= (CoordinatorLayout) findViewById(R.id.newsDetailParent);
        progressIndicator= (ProgressBar) findViewById(R.id.progressIndicator);
        bookmarkFb= (FloatingActionButton) findViewById(R.id.bookmarkFb);
        shareFb= (FloatingActionButton) findViewById(R.id.shareFb);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void showNewsDetailsToScreen(NewsDetailsFromResponse newsDetailsFromResponse) {

        if (newsDetailsFromResponse!=null&&newsDetailsFromResponse.isSuccessful()){

            if (newsDetailsFromResponse.getData()!=null){
                newsDetailParent.setVisibility(View.VISIBLE);
                progressIndicator.setVisibility(View.GONE);
                ArrayList<NewsDetail> newsDetails=newsDetailsFromResponse.getData();
                if (!newsDetails.isEmpty()){

//                    if (!TextUtils.isEmpty(newsDetails.get(0).getImage1())){

                        Picasso.with(this).load("http://"+newsDetails.get(0).getImage1()).placeholder(R.drawable.logo).into(image1);
//                    }
                    Picasso.with(this).load("http://"+newsDetails.get(0).getImage2()).placeholder(R.drawable.logo).into(image2);

                        if (!TextUtils.isEmpty(newsDetails.get(0).getTitle())){
                            titleNewsTv.setText(newsDetails.get(0).getTitle());
                        }
                    if (!TextUtils.isEmpty(newsDetails.get(0).getDate())){
                        dateTv.setText(newsDetails.get(0).getDate());
                    }
                    if (!TextUtils.isEmpty(newsDetails.get(0).getDescr())){
                        newsDetailsPart1Tv.setText(newsDetails.get(0).getDescr());
                    }

                    if (!TextUtils.isEmpty(newsDetails.get(0).getDescr2())){

                        Picasso.with(this).load(newsDetails.get(0).getImage2()).placeholder(R.drawable.logo).into(image2);
                        newsDetailsPart2Tv.setText(newsDetails.get(0).getDescr2());
                    }
                }
            }

        }

    }

    @Override
    public void showProgressBar() {
        newsDetailParent.setVisibility(View.GONE);
        progressIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNewsImage() {

    }

    @Override
    public void showNetworkNotAvaible() {
        newsDetailsPart1Tv.setText(getString(R.string.textNetworkNotAvaliable));

    }

    @Override
    public void showErrorMessage(String errorMessage) {
        newsDetailsPart1Tv.setText(errorMessage);

    }

    @Override
    public void setPresenter(NewsDetailContract.NewsDetailsPresenter presenter) {


    }
}
