package com.mandasur.app.news;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.news.adapters.DrawerAdpater;
import com.mandasur.app.util.ActivityUtil;
import com.mandasur.app.util.MandsaurNewsTextView;

import java.util.ArrayList;



public class NewsBaseActiivty extends AppCompatActivity implements DrawerContract.DrawerView {

    private DrawerLayout mDrawerLayout;
    private ListView categorylv;
    private CategoryTabsAndDrawerPresenter categoryTabsAndDrawerPresenter;
    private ArrayList<Category> categories=new ArrayList<>();
    private NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_base_actiivty);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        View view=getLayoutInflater().inflate(R.layout.layout_custom_tool_bar_layout,null);
//        toolbar.addView(view);
        MandsaurNewsTextView filtericonTv= (MandsaurNewsTextView) view.findViewById(R.id.filtericonTv);
        MandsaurNewsTextView homeAsUpIcon= (MandsaurNewsTextView) view.findViewById(R.id.homeAsUpIcon);
        filtericonTv.setOnClickListener(onClickListener);
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


    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.filtericonTv:


                    Intent intent=new Intent(NewsBaseActiivty.this,NewsFilterActivity.class);
                    startActivity(intent);
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




    @Override
    public void showCategoriesOnSidePanel(ArrayList<Category> categories) {

        DrawerAdpater drawerAdpater=new DrawerAdpater(this,categories);


        categorylv.setAdapter(drawerAdpater);
        categorylv.setOnItemClickListener(onItemClickListener);


    }







    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
             Category category= (Category) parent.getAdapter().getItem(position);
             categoryTabsAndDrawerPresenter.openCategory(category.getCategoryId());
                mDrawerLayout.closeDrawer(nav_view);
        }
    };


    @Override
    public void setPresenter(DrawerContract.CategroyPresenter presenter) {


    }
}
