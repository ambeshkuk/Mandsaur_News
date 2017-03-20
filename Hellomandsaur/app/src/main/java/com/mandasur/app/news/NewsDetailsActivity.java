package com.mandasur.app.news;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetail;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailsFromResponse;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.news.adapters.RelatedNewsAdapter;
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
    public static final String CATEGORY_NAME="category_name";
    private ImageView image1,image2;
    private MandsaurNewsTextView titleNewsTv,dateTv,consisenewsTv,newsDetailsPart1Tv,newsDetailsPart2Tv;
    private FloatingActionButton bookmarkFb,shareFb;
    private CoordinatorLayout newsDetailParent;
    private ProgressBar progressIndicator;
    private NewsDetail newsDetail;
    private  MandsaurDataBaseHelper mandsaurDataBaseHelper;
    private float detailViewTextSize;

    private NewsDetailContract.NewsDetailsPresenter newsDetailPresenter;
    private SeekBar fontSizeSb;
    private PublisherAdView mAdView;
    private RecyclerView relatedNewsRv;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_details);
        intialiseAdsOnScreen();
        String newsId=getIntent().getStringExtra(NEWS_ID);
        String categoryName=getIntent().getStringExtra(CATEGORY_NAME);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);





        View view=getLayoutInflater().inflate(R.layout.layout_custom_tool_bar_layout,null);

        MandsaurNewsTextView homeAsUpIcon= (MandsaurNewsTextView) view.findViewById(R.id.homeAsUpIcon);
        TextView titleTv= (TextView) findViewById(R.id.titleTv);
        titleTv.setText(categoryName);
        findViewById(R.id.filtericonIv).setVisibility(View.GONE);
        relatedNewsRv= (RecyclerView) findViewById(R.id.relatedNewsRv);
        newsDetailPresenter= new NewsDetailPresenter(
                Injector.getNewsDetailsFromServer(this),Injector.getShareNewsDetailsUseCase(this),this,newsId);
        mandsaurDataBaseHelper  = DatabaseNewsDataSource.getInstance(NewsDetailsActivity.this);
        homeAsUpIcon.setText(getString(R.string.textArrowIcon));

        homeAsUpIcon.setOnClickListener(onClickListener);
        intiateUI();

        fetchNewsDetsil();
//        toolbar.addView(view);
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }


    private void intialiseAdsOnScreen(){
        mAdView = (PublisherAdView) findViewById(R.id.adView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        detailViewTextSize=newsDetailsPart1Tv.getTextSize();

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




        fontSizeSb= (SeekBar) findViewById(R.id.fontSizeSb);
        fontSizeSb.setOnSeekBarChangeListener(onSeekBarChangeListener);

        bookmarkFb.setOnClickListener(onClickListener);
        shareFb.setOnClickListener(onClickListener);

    }





    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {






            float newTextSize=detailViewTextSize+(progress);


            newsDetailsPart1Tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,newTextSize);
            newsDetailsPart2Tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,newTextSize);

            newsDetailsPart1Tv.invalidate();
            newsDetailsPart2Tv.invalidate();

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    } ;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bookmarkFb:


                    boolean isNewsSaved= (boolean) bookmarkFb.getTag();
                    if (isNewsSaved){

                        mandsaurDataBaseHelper.getSavedNewsTable().deleteNewsDetailFromDb(mandsaurDataBaseHelper.getSqLiteDatabase(), newsDetail.getFid());
                        bookmarkFb.setImageResource(R.drawable.icn_read_later);
                        bookmarkFb.setTag(false);


                    }
                    else {
                        mandsaurDataBaseHelper.getSavedNewsTable().setNewsDetailToDb(mandsaurDataBaseHelper.getSqLiteDatabase(),newsDetail);
                        bookmarkFb.setImageResource(R.drawable.icn_read_later_selected);
                        bookmarkFb.setTag(true);
                    }





                    break;
                case R.id.shareFb:
                    News news=new News();
                    news.setTitle(newsDetail.getTitle());
                    news.setDate(newsDetail.getDate());

                    newsDetailPresenter.shareNewsOnSocialMedia(news);

                    break;
                case R.id.homeAsUpIcon:
                    finish();
                    break;
            }
        }
    };
    @Override
    public void showNewsDetailsToScreen(NewsDetailsFromResponse newsDetailsFromResponse) {

        if (newsDetailsFromResponse!=null&&newsDetailsFromResponse.isSuccessful()){


            if (newsDetailsFromResponse.getData()!=null){

                newsDetailParent.setVisibility(View.VISIBLE);
                progressIndicator.setVisibility(View.GONE);
                ArrayList<NewsDetail> newsDetails=newsDetailsFromResponse.getData();
                RelatedNewsAdapter relatedNewsAdapter=new RelatedNewsAdapter(new ArrayList<News>());

                relatedNewsRv.
                        setLayoutManager(new
                                LinearLayoutManager(NewsDetailsActivity.this
                                , LinearLayoutManager.HORIZONTAL,false));
                relatedNewsRv.setAdapter(relatedNewsAdapter);
                if (!newsDetails.isEmpty()){
                     newsDetail=newsDetails.get(0);
                    if (mandsaurDataBaseHelper.getSavedNewsTable().isNewsAlreadySaved(mandsaurDataBaseHelper.getSqLiteDatabase(),newsDetail.getFid())){

                        bookmarkFb.setTag(true);
                        bookmarkFb.setImageResource(R.drawable.icn_read_later_selected);
                    }
                    else {
                        bookmarkFb.setTag(false);
                        bookmarkFb.setImageResource(R.drawable.icn_read_later);
                    }
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
