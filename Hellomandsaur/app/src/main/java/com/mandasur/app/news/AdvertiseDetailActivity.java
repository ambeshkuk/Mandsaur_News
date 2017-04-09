package com.mandasur.app.news;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mandasur.app.R;
import com.squareup.picasso.Picasso;

public class AdvertiseDetailActivity extends Activity {








    public static final String ADS_URL="ads_url";
    public static final String ADS_FULL_IMAGE="ads_full_image";
    public static final String ADS_TITLE="ads_title";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_advertisement_detail);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);





        lp.width = (displaymetrics.widthPixels*90)/100;
        WebView webView= (WebView) findViewById(R.id.webView);

        String ads_url=getIntent().getStringExtra(ADS_URL);
        String ads_full_image=getIntent().getStringExtra(ADS_FULL_IMAGE);
        String ads_title=getIntent().getStringExtra(ADS_TITLE);
        if (!TextUtils.isEmpty(ads_url)){
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(ads_url);
            findViewById(R.id.imagerRl).setVisibility(View.GONE);

        }
        else {
            webView.setVisibility(View.GONE);

            findViewById(R.id.imagerRl).setVisibility(View.VISIBLE);
            ImageView imageView= (ImageView) findViewById(R.id.imageView);
            TextView titleTv= (TextView) findViewById(R.id.titleTv);
            Picasso.with(this).load(ads_full_image)
                    .placeholder(R.drawable.no_image_found).into(imageView);
            titleTv.setText(ads_title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advertise_detail, menu);
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
