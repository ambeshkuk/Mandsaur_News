package com.mandasur.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mandasur.app.data.source.AdvertisingDataRepository;
import com.mandasur.app.data.source.CategoryDataRepository;
import com.mandasur.app.data.source.dao.requestdao.AdvertiseRequest;
import com.mandasur.app.data.source.dao.requestdao.CategoryResponseBean;
import com.mandasur.app.data.source.dao.requestdao.Request;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.news.NewsBaseActiivty;
import com.mandasur.app.util.ActivityUtil;
import com.mandasur.app.util.DialogUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashScrees extends Activity {

    Handler handler;
    private ProgressBar progressBar;

    private CategoryDataRepository categoryDataRepository;

    private TextView informationSplash;

    @Override
    protected void attachBaseContext(Context newBase) {




        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_splash_screen);
        categoryDataRepository=Injector.getCategoryDataReporsitory(SplashScrees.this);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        informationSplash= (TextView) findViewById(R.id.informationSplash);



        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                MandsaurDataBaseHelper mandsaurDataBaseHelper=categoryDataRepository.getCategoriesDataSource()
                        .getDatabaseNewsDataSource().getInstance(SplashScrees.this);
                if (ActivityUtil.isNetworkAvaliable(SplashScrees.this)
                        ){
                    new FetchCategoriesAndSubCategories().execute();
                }
                else  if (mandsaurDataBaseHelper.getCategoriesTable()
                        .getRowCount(mandsaurDataBaseHelper.getSqLiteDatabase())!=0){
                    Intent intent=new Intent(SplashScrees.this,NewsBaseActiivty.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    DialogUtils.showFinishDialog(SplashScrees.this
                            , getString(R.string.textNetworkNotAvaliableSplash), new DialogUtils.OnFinishDialogClickInterface() {
                        @Override
                        public void onFinishDialog() {
                            finish();

                        }
                    });
                }
            }
        }, 4000);



    }



    private class FetchCategoriesAndSubCategories
            extends AsyncTask<Void,Void,CategoryResponseBean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DialogUtils.showProgressDialog(SplashScrees.this,"",getString(R.string.textPleaseWait),false);

        }

        @Override
        protected CategoryResponseBean doInBackground(Void... params) {

            final CategoryResponseBean[] categoryResponseBean = new CategoryResponseBean[1];
            AdvertiseRequest advertiseRequest=new AdvertiseRequest();
            advertiseRequest.put(Request.REQUEST_URL,getString(R.string.baseUrl)
                    +getString(R.string.advertiseingList));
            try{
                Injector.getAdvertisingDataRepository(SplashScrees.this)
                        .loadAdvertiseMentToDb(new AdvertisingDataRepository.LoadAdvertisinments() {
                            @Override
                            public void onAdvertismentsLoaded(boolean advertisementsLoaded) {


                                if (!advertisementsLoaded){
                                    Toast.makeText(SplashScrees.this
                                            ,"Unable to Load Advertisement",Toast.LENGTH_LONG).show();
                                }


                            }

                            @Override
                            public void onAdvertisiementsNotVaialbel() {

                            }
                        },advertiseRequest);
            }
            catch (Exception e){
                e.printStackTrace();
            }

           categoryDataRepository.
                   loadCategoriesToDb(new CategoryDataRepository.LoadCategoriesCallBack() {
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
          return categoryResponseBean[0];

        }

        @Override
        protected void onPostExecute(CategoryResponseBean categoryResponseBean) {
            super.onPostExecute(categoryResponseBean);
            DialogUtils.dismissProgressDialog();
            progressBar.setVisibility(View.GONE);
            if (categoryResponseBean!=null){
                if(categoryResponseBean.isSuccessful()){
                    Intent intent=new Intent(SplashScrees.this,NewsBaseActiivty.class);
                    startActivity(intent);
                    finish();
                }
                else{

                    DialogUtils.showFinishDialog(SplashScrees.this,
                            "Could Not initiate the App , please try again later", new DialogUtils.OnFinishDialogClickInterface() {
                        @Override
                        public void onFinishDialog() {
                                  finish();
                        }
                    });

                }
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
