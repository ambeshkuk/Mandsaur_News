package com.mandasur.app.news;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.news.NewsList.FiltredNewsListWithSubCategoryFragment;
import com.mandasur.app.util.ActivityUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FiltredNewsListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filtred_news_list_activity);
        Intent intent=getIntent();

        FiltredNewsListWithSubCategoryFragment filtredNewsListWithSubCategoryFragment
                = FiltredNewsListWithSubCategoryFragment.newInstance(intent.
                getStringExtra(FiltredNewsListWithSubCategoryFragment.SUBCATEGORY_STING));
        filtredNewsListWithSubCategoryFragment.setPresenter(new NewsListPresenter(Injector.getNewsListByCategory(FiltredNewsListActivity.this)
                , filtredNewsListWithSubCategoryFragment,""));
        ActivityUtil.addFragmentToActivity(R.id.content_frame,
                getSupportFragmentManager(),
                filtredNewsListWithSubCategoryFragment, FiltredNewsListWithSubCategoryFragment.class.getSimpleName());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filtred_news_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
