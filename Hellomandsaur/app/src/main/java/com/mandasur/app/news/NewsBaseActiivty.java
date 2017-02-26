package com.mandasur.app.news;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.news.adapters.DrawerAdpater;
import com.mandasur.app.util.ActivityUtil;
import com.mandasur.app.util.MandsaurNewsTextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_base_actiivty);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


//        toolbar.addView(view);
         filtericonIv= (ImageView) findViewById(R.id.filtericonIv);
        homeAsUpIcon = (MandsaurNewsTextView) findViewById(R.id.homeAsUpIcon);
         titleTv= (MandsaurNewsTextView) findViewById(R.id.titleTv);
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




    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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


    @Override
    public void showCategoriesOnSidePanel(ArrayList<Category> categories) {

        drawerAdpater =new DrawerAdpater(this,categories);


        categorylv.setAdapter(drawerAdpater);
//        drawerAdpater.setSelectedPosition(0);
//        drawerAdpater.notifyDataSetChanged();

        categorylv.setOnItemClickListener(onItemClickListener);


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

        if (position==0){
            setTheVisibilityOfFilterActivity(true);
        }
        else {
            setTheVisibilityOfFilterActivity(false);
        }

    }


    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
             Category category= (Category) parent.getAdapter().getItem(position);
             categoryTabsAndDrawerPresenter.openCategory(category.getCategoryId());
                mDrawerLayout.closeDrawer(nav_view);

//            if (category.getCategoryIdentifier().equals("Main News")){
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
