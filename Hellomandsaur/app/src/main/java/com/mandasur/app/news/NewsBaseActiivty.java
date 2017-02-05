package com.mandasur.app.news;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.data.Category;
import com.mandasur.app.news.adapters.DrawerAdpater;
import com.mandasur.app.util.ActivityUtil;

import java.util.ArrayList;



public class NewsBaseActiivty extends AppCompatActivity implements NewsDrawerContract.DrawerView {

    private DrawerLayout mDrawerLayout;
    private ListView categorylv;
    private NewsListPresenter newsListPresenter;
    private ArrayList<Category> categories=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_base_actiivty);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.navigation_icon);
        ab.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.red);


        categorylv = (ListView) findViewById(R.id.categorylv);
        BaseNewsFragment baseNewsFragment =
                (BaseNewsFragment) getSupportFragmentManager().findFragmentByTag(BaseNewsFragment.class.getSimpleName());

        if (baseNewsFragment == null) {
            // Create the fragment
            baseNewsFragment = BaseNewsFragment.newInstance("","");
            newsListPresenter =new NewsListPresenter(Injector.getCategoriesUseCase(this),baseNewsFragment,this);

            ActivityUtil.addFragmentToActivity(R.id.content_frame,
                    getSupportFragmentManager(), baseNewsFragment, BaseNewsFragment.class.getSimpleName());
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_news_base_actiivty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showCategoriesOnSidePanel(ArrayList<Category> categories) {

        DrawerAdpater drawerAdpater=new DrawerAdpater(this,categories);


        categorylv.setAdapter(drawerAdpater);


    }




    @Override
    public void setPresenter(NewsDrawerContract.NewsPresenter presenter) {


    }
}
