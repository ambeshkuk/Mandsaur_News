package com.mandasur.app.news;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mandasur.app.Config;
import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.aboutus_contact.AboutUsAndAdvertiseWithUsActivity;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.news.adapters.DrawerAdpater;
import com.mandasur.app.socailmedia.SocialMediaUtil;
import com.mandasur.app.util.ActivityUtil;
import com.mandasur.app.util.DialogResponseInterface;
import com.mandasur.app.util.DialogUtils;
import com.mandasur.app.util.MandsaurAppSharedPref;
import com.mandasur.app.util.MandsaurNewsTextView;
import com.mandasur.app.util.NotificationUtils;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class NewsBaseActiivty extends AppCompatActivity implements DrawerContract.DrawerView {

    private DrawerLayout mDrawerLayout;
    private ListView categorylv;
    private CategoryTabsAndDrawerPresenter categoryTabsAndDrawerPresenter;
    private ArrayList<Category> categories=new ArrayList<>();
    private NavigationView nav_view;
    private MandsaurNewsTextView homeAsUpIcon,titleTv;
    private ImageView filtericonIv;
    private LinearLayout footerViewDrawerLayout;
    private PublisherAdView mAdView;
   private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_base_actiivty);

        toolbar  = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        intialiseAdsOnScreen();

//        toolbar.addView(view);
         filtericonIv= (ImageView) findViewById(R.id.filtericonIv);
        homeAsUpIcon = (MandsaurNewsTextView) findViewById(R.id.homeAsUpIcon);
         titleTv= (MandsaurNewsTextView) findViewById(R.id.titleTv);

        footerViewDrawerLayout= (LinearLayout) getLayoutInflater().inflate(R.layout.layout_bottom_drawer_list_view,null);
        intializeTheFooterView();
        titleTv.setText(getString(R.string.title_activity_detail));
        filtericonIv.setOnClickListener(onClickListener);
        homeAsUpIcon.setOnClickListener(onClickListener);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.red);
        nav_view= (NavigationView) findViewById(R.id.nav_view);

        categorylv = (ListView) findViewById(R.id.categorylv);
        BaseCategoryFragment baseNewsFragment =
                (BaseCategoryFragment) getSupportFragmentManager().findFragmentByTag(BaseCategoryFragment.class.getSimpleName());

        if (baseNewsFragment == null) {
            // Create the fragment
            baseNewsFragment = BaseCategoryFragment.newInstance("", "");
            categoryTabsAndDrawerPresenter =new CategoryTabsAndDrawerPresenter(Injector.getCategoriesUseCase(this),baseNewsFragment,this);

            ActivityUtil.addFragmentToActivity(R.id.content_frame,
                    getSupportFragmentManager(), baseNewsFragment, BaseCategoryFragment.class.getSimpleName());
        }
        else{
            categoryTabsAndDrawerPresenter =new CategoryTabsAndDrawerPresenter(Injector.getCategoriesUseCase(this),baseNewsFragment,this);

            ActivityUtil.addFragmentToActivity(R.id.content_frame,
                    getSupportFragmentManager(), baseNewsFragment, BaseCategoryFragment.class.getSimpleName());
        }



        registerBroadcastReciver();

    }


    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private void registerBroadcastReciver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_NEWS);



                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received


                }
            }
        };
    }

    private MandsaurNewsTextView aboutUsTv,contactUsTv,privacyPolicyTv
            ,advertiseUsTv,siteMapTv,facebookTv,twitterTv,googleTv,youtubeTv,humaraMandsurTv,buisnessTv;
    private void intializeTheFooterView(){
        aboutUsTv= (MandsaurNewsTextView) footerViewDrawerLayout.findViewById(R.id.aboutUsTv);
        contactUsTv= (MandsaurNewsTextView) footerViewDrawerLayout.findViewById(R.id.contactUsTv);
        privacyPolicyTv= (MandsaurNewsTextView) footerViewDrawerLayout
                .findViewById(R.id.privacyPolicyTv);
        advertiseUsTv= (MandsaurNewsTextView) footerViewDrawerLayout.findViewById(R.id.advertiseUsTv);
        facebookTv= (MandsaurNewsTextView) footerViewDrawerLayout.findViewById(R.id.facebookTv);
        twitterTv= (MandsaurNewsTextView) footerViewDrawerLayout.findViewById(R.id.twitterTv);
        googleTv= (MandsaurNewsTextView) footerViewDrawerLayout.findViewById(R.id.googleTv);
        youtubeTv= (MandsaurNewsTextView) footerViewDrawerLayout.findViewById(R.id.youtubeTv);
        humaraMandsurTv= (MandsaurNewsTextView) footerViewDrawerLayout.findViewById(R.id.humaraMandsurTv);
        buisnessTv= (MandsaurNewsTextView) footerViewDrawerLayout.findViewById(R.id.buisnessTv);


        aboutUsTv.setOnClickListener(onClickListener);
        contactUsTv.setOnClickListener(onClickListener);
        privacyPolicyTv.setOnClickListener(onClickListener);
        advertiseUsTv.setOnClickListener(onClickListener);

        facebookTv.setOnClickListener(onClickListener);
        twitterTv.setOnClickListener(onClickListener);
        googleTv.setOnClickListener(onClickListener);
        youtubeTv.setOnClickListener(onClickListener);
        humaraMandsurTv.setOnClickListener(onClickListener);
        buisnessTv.setOnClickListener(onClickListener);

        advertiseUsTv.setOnClickListener(onClickListener);

    }

    private void intialiseAdsOnScreen(){
        mAdView = (PublisherAdView) findViewById(R.id.adView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mRegistrationBroadcastReceiver);

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
            mAdView.resume();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
        ActivityUtil.log(NewsBaseActiivty.class.getSimpleName(), toolbar.getHeight() + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case NewsFilterActivity.REQUEST_CODE_FILTER_CHANGED:
                    if (resultCode==RESULT_OK){


                        Fragment baseCategoryFragment= getSupportFragmentManager().findFragmentByTag(BaseCategoryFragment.class.getSimpleName());

                        if ((baseCategoryFragment!=null)&&(baseCategoryFragment instanceof BaseCategoryFragment)){
                            ((BaseCategoryFragment)baseCategoryFragment).onActivityResult(requestCode, resultCode, data);
                        }

                    }
                break;
            default:
                break;
        }

    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.filtericonIv:
                     Intent intent=new Intent(NewsBaseActiivty.this,NewsFilterActivity.class);
                    startActivityForResult(intent, NewsFilterActivity.REQUEST_CODE_FILTER_CHANGED);
                    break;
                case R.id.homeAsUpIcon:
                    if (mDrawerLayout.isDrawerOpen(nav_view)){
                        mDrawerLayout.closeDrawer(nav_view);
                    }
                    else{
                        mDrawerLayout.openDrawer(nav_view);
                    }
                    break;
                case R.id.facebookTv:
                    mDrawerLayout.closeDrawer(nav_view);
                    SocialMediaUtil.openFacebookPageURL(NewsBaseActiivty.this);

                    break;
                case R.id.googleTv:
                    mDrawerLayout.closeDrawer(nav_view);
                    SocialMediaUtil.openGooglePlusPage(NewsBaseActiivty.this);
                    break;
                case R.id.twitterTv:
                    mDrawerLayout.closeDrawer(nav_view);
                    SocialMediaUtil.openTwitterPageUrl(NewsBaseActiivty.this);
                    break;
                case R.id.youtubeTv:
                    SocialMediaUtil.openYouTubePage(NewsBaseActiivty.this);
                    break;
                case R.id.aboutUsTv:
                    Intent aboutUsIntent=new Intent(NewsBaseActiivty.this, AboutUsAndAdvertiseWithUsActivity.class);
                    aboutUsIntent.putExtra(AboutUsAndAdvertiseWithUsActivity.TYPE_OF_SCREEN,AboutUsAndAdvertiseWithUsActivity.TYPE_ABOUT_US);
                    startActivity(aboutUsIntent);
                    break;
                case R.id.advertiseUsTv:
                    Intent advertiseWithUs=new Intent(NewsBaseActiivty.this, AboutUsAndAdvertiseWithUsActivity.class);
                    advertiseWithUs.putExtra(AboutUsAndAdvertiseWithUsActivity.TYPE_OF_SCREEN,AboutUsAndAdvertiseWithUsActivity.TYPE_ADVERTISE);
                    startActivity(advertiseWithUs);
                    break;
                case R.id.humaraMandsurTv:
                    Intent humaraMandsaur=new Intent(NewsBaseActiivty.this, AboutUsAndAdvertiseWithUsActivity.class);
                    humaraMandsaur.
                            putExtra(AboutUsAndAdvertiseWithUsActivity.TYPE_OF_SCREEN
                                    , AboutUsAndAdvertiseWithUsActivity.TYPE_HUMARA_MANDSAUR);
                    startActivity(humaraMandsaur);
                    break;
                case R.id.buisnessTv:
                    Intent businsess=new Intent(NewsBaseActiivty.this, AboutUsAndAdvertiseWithUsActivity.class);
                    businsess.
                            putExtra(AboutUsAndAdvertiseWithUsActivity.TYPE_OF_SCREEN
                                    ,AboutUsAndAdvertiseWithUsActivity.TYPE_BUISNESS);
                    startActivity(businsess);
                    break;
            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_news_base_actiivty, menu);
        return true;
    }

private DrawerAdpater drawerAdpater;


//
    @Override
    public void showCategoriesOnSidePanel(ArrayList<Category> categories) {
        this.categories.addAll(categories);
        drawerAdpater =new DrawerAdpater(this,categories);


        categorylv.setAdapter(drawerAdpater);
//        drawerAdpater.setSelectedPosition(0);
//        drawerAdpater.notifyDataSetChanged();

        categorylv.setOnItemClickListener(onItemClickListener);
        categorylv.addFooterView(footerViewDrawerLayout);


    }

    @Override
    public void onBackPressed() {

        DialogUtils.
                showConfirmationDialog(this
                        , "Hello Mandsur App", "क्या आप वाकई छोड़ना चाहते है"
                        , false, -1, "Ok", "Cancel", new DialogResponseInterface() {
                    @Override
                    public void doOnPositiveBtnClick(Activity activity) {
                        finish();
                    }

                    @Override
                    public void doOnNegativeBtnClick(Activity activity) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void setTheVisibilityOfFilterActivity(boolean isFilterBeVisible) {

        if (isFilterBeVisible){
            filtericonIv.setVisibility(View.VISIBLE);
        }
        else {
            filtericonIv.setVisibility(View.GONE);
        }


    }





    @Override
    public void setItemSelcetionOfDrawerView(int position) {

        if (drawerAdpater!=null){
            categorylv.setItemChecked(position,true);

            drawerAdpater.notifyDataSetChanged();
        }
        MandsaurAppSharedPref.setCategoryIndicator(NewsBaseActiivty.this,categories.get(position).getCategory_indicator());


            setTheVisibilityOfFilterActivity(categories.get(position).getIsSubCategoryAvailable()==Category.SUBCATEGORY_AVAIALBLE?true:false);



    }


    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
             Category category= (Category) parent.getAdapter().getItem(position);

             categoryTabsAndDrawerPresenter.openCategory(category.getCategroyId());
                mDrawerLayout.closeDrawer(nav_view);

//            if (category.getCategory_indicator().equals("Main News")){
//                setTheVisibilityOfFilterActivity(true);
//            }
//            else {
//                setTheVisibilityOfFilterActivity(false);

//            }
        }
    };


    @
            Override
    public void setPresenter(DrawerContract.CategroyPresenter presenter) {


    }
}
