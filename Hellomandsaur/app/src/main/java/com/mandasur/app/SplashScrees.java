package com.mandasur.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mandasur.app.R;
import com.mandasur.app.news.NewsBaseActiivty;

public class SplashScrees extends Activity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_splash_screen);

handler=new Handler();

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

        loadSubCategoriesOnDb();
    }

    private void loadSubCategoriesOnDb()
    {
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                Injector.getSubCategoryDataReporsitory(SplashScrees.this).getCategories(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(SplashScrees.this, NewsBaseActiivty.class);
                startActivity(i);
//
//                // close this activity
                finish();
                    }
                },6000);
            }
        };
        thread.start();
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
