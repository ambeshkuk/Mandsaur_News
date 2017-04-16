package com.mandasur.app.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetail;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailsFromResponse;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.news.NewsList.FiltredNewsListWithSubCategoryFragment;
import com.mandasur.app.news.adapters.AdvertiseWithUsAdapter;
import com.mandasur.app.news.adapters.RelatedNewsAdapter;
import com.mandasur.app.util.ActivityUtil;
import com.mandasur.app.util.MandsaurNewsTextView;
import com.mandasur.app.util.WrapContentLinearLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ambesh on 11-02-2017.
 */
public class NewsVideoActivity extends AppCompatActivity
        implements
        NewsDetailContract.NewsDetailView ,
        YouTubePlayer.OnInitializedListener{

    public static final String NEWS_ID="newsId";
    public static final String CATEGORY_NAME="category_name";
    public static final String VIDEO_URL="video_url";

    private ImageView image2;
    private TextView titleNewsTv,dateTv,consisenewsTv,newsDetailsPart1Tv,newsDetailsPart2Tv;
    private FloatingActionButton bookmarkFb,shareFb;

    private ProgressBar progressIndicator;
    private NewsDetail newsDetail;
    private String newsDetailString;
    private  MandsaurDataBaseHelper mandsaurDataBaseHelper;
    private float detailViewTextSize;

    private NewsVideoPresenter newsDetailPresenter;
    private SeekBar fontSizeSb;
    private PublisherAdView mAdView;
    private RecyclerView relatedNewsRv,advertiseUsRv;
    private TextView titleTv;
    TextView homeAsUpIcon;
    private YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    private LinearLayout newsDetailParent;
    private String newsUrl;
    private TextView viewsTv;


    private RelatedNewsAdapter.OnNewsItemSelected onNewsItemSelected=new RelatedNewsAdapter.OnNewsItemSelected() {
        @Override
        public void openNewsItem(String newsId) {
            Intent intent=new Intent(NewsVideoActivity.this, NewsDetailsActivity.class);
            intent.putExtra(NewsDetailsActivity.NEWS_ID,newsId);
            intent.putExtra(NewsDetailsActivity.CATEGORY_NAME,
                    getIntent().getStringExtra(CATEGORY_NAME));
            startActivity(intent);
        }

        @Override
        public void onClickOnShareBtn(News news) {

        }
    };
    private  String videoUrl;
    private ScrollView scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video_news_detail);
        scrollView= (ScrollView) findViewById(R.id.scrollView);

        intialiseAdsOnScreen();
        String newsId=getIntent().getStringExtra(NEWS_ID);
        ActivityUtil.log(NewsDetailsActivity.class.getSimpleName(), "News Id:" + newsId);
        String categoryName=getIntent().getStringExtra(CATEGORY_NAME);
        videoUrl=getIntent().getStringExtra(VIDEO_URL);
        findViewById(R.id.filtericonIv).setVisibility(View.GONE);

        homeAsUpIcon= (MandsaurNewsTextView)findViewById(R.id.homeAsUpIcon);
        newsDetailParent= (LinearLayout) findViewById(R.id.newsDetailParent);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homeAsUpIcon.setText(getString(R.string.textArrowIcon));
        homeAsUpIcon.setOnClickListener(onClickListener);

        findViewById(R.id.filtericonIv).setVisibility(View.GONE);
        TextView titleTv= (TextView) findViewById(R.id.titleTv);
        titleTv.setText(categoryName);

        youTubePlayerSupportFragment= (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.youtubePlayerFragment);
//        if (!TextUtils.isEmpty(videoUrl)){
        youTubePlayerSupportFragment.initialize(getString(R.string.google_key),this);
//        }
//        else {
//            getSupportFragmentManager().beginTransaction().hide(youTubePlayerSupportFragment).commit();
//        }











        relatedNewsRv= (RecyclerView) findViewById(R.id.relatedNewsRv);




        newsDetailPresenter= new NewsVideoPresenter(
                Injector.getNewsDetailsFromServer(this)
                ,Injector.getShareNewsDetailsUseCase(this),this,newsId);
        mandsaurDataBaseHelper  = DatabaseNewsDataSource.getInstance(NewsVideoActivity.this);
        intiateUI();

        fetchNewsDetsil();

//        toolbar.addView(view);
    }

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener=new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

            String errorMessage = null;
            switch (errorReason){
                case NOT_PLAYABLE:
                    errorMessage="NOT_PLAYABLE";
                    break;
                case NETWORK_ERROR:
                    errorMessage="NETWORK_ERROR";
                    break;
                case UNAUTHORIZED_OVERLAY:
                    errorMessage="UNAUTHORIZED_OVERLAY";
                    break;
                case PLAYER_VIEW_TOO_SMALL:
                    errorMessage="PLAYER_VIEW_TOO_SMALL";
                    break;
                case PLAYER_VIEW_NOT_VISIBLE:
                    errorMessage="PLAYER_VIEW_NOT_VISIBLE";
                    break;
                case EMPTY_PLAYLIST:
                    errorMessage="EMPTY_PLAYLIST";
                    break;
                case AUTOPLAY_DISABLED:
                    errorMessage="AUTOPLAY_DISABLED";
                    break;
                case USER_DECLINED_RESTRICTED_CONTENT:
                    errorMessage="USER_DECLINED_RESTRICTED_CONTENT";
                    break;
                case USER_DECLINED_HIGH_BANDWIDTH:
                    errorMessage="USER_DECLINED_HIGH_BANDWIDTH";
                    break;
                case UNEXPECTED_SERVICE_DISCONNECTION:
                    errorMessage="UNEXPECTED_SERVICE_DISCONNECTION";
                    break;

                case INTERNAL_ERROR:
                    errorMessage="INTERNAL_ERROR";
                    break;
                case UNKNOWN:
                    errorMessage="UNKNOWN";
                    break;



            }
            Toast.makeText(NewsVideoActivity.this,"Some error occurred "+errorMessage,Toast.LENGTH_LONG).show();
        }
    };
    private YouTubePlayer.PlaybackEventListener playbackEventListener=new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();


        }
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQS_ErrorDialog) {
            // Retry initialization if user performed a recovery action
            youTubePlayerSupportFragment.initialize(getString(R.string.google_key), this);
        }
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
        youTubePlayerSupportFragment.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        if (mAdView != null) {
            mAdView.resume();


        }
        detailViewTextSize=newsDetailsPart1Tv.getTextSize();


    }

    private void fetchNewsDetsil() {
        newsDetailPresenter.fetchNewsDetailsFromServer();
    }

    private void intiateUI(){

        image2= (ImageView) findViewById(R.id.image2);
        titleNewsTv= (TextView)
                findViewById(R.id.titleNewsTv);
        dateTv= (TextView) findViewById(R.id.dateTv);

        consisenewsTv= (TextView) findViewById(R.id.consisenewsTv);
        newsDetailsPart1Tv= (TextView) findViewById(R.id.newsDetailsPart1Tv);
        newsDetailsPart2Tv= (TextView) findViewById(R.id.newsDetailsPart2Tv);
        bookmarkFb= (FloatingActionButton) findViewById(R.id.bookmarkFb);
        shareFb= (FloatingActionButton) findViewById(R.id.shareFb);

        progressIndicator= (ProgressBar) findViewById(R.id.progressIndicator);
        bookmarkFb= (FloatingActionButton) findViewById(R.id.bookmarkFb);
        shareFb= (FloatingActionButton) findViewById(R.id.shareFb);


        advertiseUsRv= (RecyclerView) findViewById(R.id.advertiseUsRv);

        fontSizeSb= (SeekBar) findViewById(R.id.fontSizeSb);
        viewsTv= (TextView) findViewById(R.id.viewsTv);
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

                        mandsaurDataBaseHelper.getSavedNewsTable().deleteNewsDetailFromDb(mandsaurDataBaseHelper.getSqLiteDatabase(), newsDetail.getId());
                        bookmarkFb.setImageResource(R.drawable.icn_read_later);
                        bookmarkFb.setTag(false);


                    }
                    else {
                        mandsaurDataBaseHelper.getSavedNewsTable()
                                .setNewsDetailToDb(mandsaurDataBaseHelper.getSqLiteDatabase(), newsDetailString,newsDetail.getId());
                        bookmarkFb.setImageResource(R.drawable.icn_read_later_selected);
                        bookmarkFb.setTag(true);
                    }





                    break;
                case R.id.shareFb:
                    News news=new News();
                    news.setTitle(Html.fromHtml(newsDetail.getTitle()).toString());
                    news.setDate(newsDetail.getDate());
                    news.setNewsUrl(newsUrl);
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
                ArrayList<NewsDetail> relatedNews=newsDetailsFromResponse.getRelated_news();
                if (relatedNews!=null&&!relatedNews.isEmpty()){
                    RelatedNewsAdapter relatedNewsAdapter=new RelatedNewsAdapter(newsDetails);

                    relatedNewsRv.
                            setLayoutManager(new
                                    WrapContentLinearLayoutManager(NewsVideoActivity.this
                                    , LinearLayoutManager.HORIZONTAL, false));
                    relatedNewsAdapter.setOnNewsItemSelected(onNewsItemSelected);
                    relatedNewsRv.setAdapter(relatedNewsAdapter);
                }
                else {
                    findViewById(R.id.suggestedNewsTv).setVisibility(View.GONE);
                    relatedNewsRv.setVisibility(View.GONE);
                }



                AdvertiseWithUsAdapter advertiseWithUsAdapter=new AdvertiseWithUsAdapter(new ArrayList<News>());

                advertiseUsRv.setLayoutManager(new
                        WrapContentLinearLayoutManager(NewsVideoActivity.this
                        , LinearLayoutManager.HORIZONTAL, false));
                advertiseUsRv.setAdapter(advertiseWithUsAdapter);
                if (!newsDetails.isEmpty()){

                    newsDetail=newsDetails.get(0);
                    newsUrl=newsDetail.getNewsurl();
                    newsDetailString=newsDetailsFromResponse.getUnderLineJson();
                    if (mandsaurDataBaseHelper.getSavedNewsTable().isNewsAlreadySaved(mandsaurDataBaseHelper.getSqLiteDatabase(),newsDetail.getId())){

                        bookmarkFb.setTag(true);
                        bookmarkFb.setImageResource(R.drawable.icn_read_later_selected);
                    }
                    else {
                        bookmarkFb.setTag(false);
                        bookmarkFb.setImageResource(R.drawable.icn_read_later);
                    }
//                    if (!TextUtils.isEmpty(newsDetails.get(0).getImage1())){

                       Picasso.with(this).load(newsDetails.get(0).getImage()).placeholder(R.drawable.logo).into(image2);
//                    }

                    if (!TextUtils.isEmpty(newsDetails.get(0).getTitle())){
                        titleNewsTv.setText(newsDetails.get(0).getTitle());
                    }
                    if (!TextUtils.isEmpty(newsDetails.get(0).getDate())){
                        dateTv.setText(newsDetails.get(0).getDate());
                    }
                    if (!TextUtils.isEmpty(newsDetails.get(0).getDescr())){
                        newsDetailsPart1Tv.setText(Html.fromHtml(newsDetails.get(0).getDescr()));
                    }
                    if (!TextUtils.isEmpty(newsDetails.get(0).getViews())){
                        viewsTv.setText(viewsTv.getText().toString().replace("0", newsDetails.get(0).getViews()));
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
        findViewById(R.id.suggestedNewsTv).setVisibility(View.GONE);
        findViewById(R.id.relatedNewsRv).setVisibility(View.GONE);
        findViewById(R.id.advertiseUsRv).setVisibility(View.GONE);
        findViewById(R.id.bookmarkFb).setVisibility(View.GONE);
        findViewById(R.id.shareFb).setVisibility(View.GONE);
        findViewById(R.id.bottomBarLv).setVisibility(View.GONE);

        newsDetailsPart1Tv.setText(getString(R.string.textNetworkNotAvaliable));

    }

    @Override
    public void showErrorMessage(String errorMessage) {

        newsDetailParent.setVisibility(View.VISIBLE);
        findViewById(R.id.suggestedNewsTv).setVisibility(View.GONE);
        findViewById(R.id.relatedNewsRv).setVisibility(View.GONE);
        findViewById(R.id.advertiseUsRv).setVisibility(View.GONE);
        findViewById(R.id.bookmarkFb).setVisibility(View.GONE);
        findViewById(R.id.shareFb).setVisibility(View.GONE);
        findViewById(R.id.bottomBarLv).setVisibility(View.GONE);
        progressIndicator.setVisibility(View.GONE);
        newsDetailsPart1Tv.setText(errorMessage);

    }

    @Override
    public void setPresenter(NewsDetailContract.NewsDetailsPresenter presenter) {


    }



    private YouTubePlayer youTubePlayer;
    private static final int RQS_ErrorDialog = 1;

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean wasRestored) {

        this.youTubePlayer = youTubePlayer;

        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);


        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored) {
            youTubePlayer.cueVideo(videoUrl);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RQS_ErrorDialog).show();
        } else {
            Toast.makeText(this,
                    "YouTubePlayer.onInitializationFailure(): " + youTubeInitializationResult.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
