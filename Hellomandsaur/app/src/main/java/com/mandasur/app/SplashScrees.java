package com.mandasur.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mandasur.app.R;
import com.mandasur.app.data.source.CategoryDataRepository;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.requestdao.CategoryResponseBean;
import com.mandasur.app.news.NewsBaseActiivty;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashScrees extends Activity {

    Handler handler;
    private ProgressBar progressBar;



    private TextView informationSplash;

    @Override
    protected void attachBaseContext(Context newBase) {




        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_splash_screen);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        informationSplash= (TextView) findViewById(R.id.informationSplash);



//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(SplashScrees.this, NewsBaseActiivty.class);
//                startActivity(i);
//
//                // close this activity
//                finish();
//            }
//        }, 6000);

        new FetchCategoriesAndSubCategories().execute();
    }



    private class FetchCategoriesAndSubCategories
            extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            final CategoryResponseBean[] categoryResponseBean = new CategoryResponseBean[1];
            Injector.getCategoryDataReporsitory(SplashScrees.this).
                    getCategories(new CategoryDataRepository.LoadCategoriesCallBack() {
                        @Override
                        public void onCategoriesLoaded(CategoryResponseBean categories) {

                            categoryResponseBean[0] = categories;
                        }

                        @Override
                        public void onCategoriesNotAvaliable() {

                        }
                    });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {


            }
            if (categoryResponseBean[0]!=null&&categoryResponseBean[0].isSuccessful()){
                return true;
            }
            else {
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressBar.setVisibility(View.GONE);
            if (aBoolean){
                Intent intent=new Intent(SplashScrees.this,NewsBaseActiivty.class);
                startActivity(intent);
                finish();
            }
            else {

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screes, menu);
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
