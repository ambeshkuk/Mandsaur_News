package com.mandasur.app.aboutus_contact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.util.MandsaurNewsTextView;

public class AboutUsAndAdvertiseWithUsActivity extends ActionBarActivity implements AboutUsViewAndPresenterContract.AboutUsView{







    public static final String TYPE_OF_SCREEN="type_of_screen";
    public static final String TYPE_ADVERTISE="Advertise";
    public static final String TYPE_ABOUT_US="AboutUs";
    private AboutUsPresenter aboutUsPresenter;
    private MandsaurNewsTextView homeAsUpIcon;
    private WebView aboutUsTv;
    private TextView titleNewsTv;
    private TextView emailTv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_about_us);
        aboutUsTv= (WebView) findViewById(R.id.aboutUsTv);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        aboutUsTv.getSettings().setLoadWithOverviewMode(true);

        aboutUsTv.getSettings().setUseWideViewPort(true);


        aboutUsTv.getSettings().setJavaScriptEnabled(true);


        aboutUsTv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                findViewById(R.id.parent).setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                view.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.parent).setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);

            }
        });
        if(getIntent().getStringExtra(TYPE_OF_SCREEN).equals(TYPE_ABOUT_US)){
            aboutUsTv.loadUrl("http://hellomandsaur.com/about-us/");
        }
        else{
            aboutUsTv.loadUrl("http://hellomandsaur.com/advertise-with-us/");
        }



//
        titleNewsTv= (TextView) findViewById(R.id.titleNewsTv);
        emailTv= (TextView) findViewById(R.id.emailTv);
        emailTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent=new Intent();
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@hellomandsaur.com"});


                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });
        titleNewsTv.
                setText(getString(R.string.textContactUs) + ":" +
                        getString(R.string.brijeshNo));
        titleNewsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + getString(R.string.brijeshNo)));
                startActivity(intent);
            }
        });

        homeAsUpIcon  = (MandsaurNewsTextView) findViewById(R.id.homeAsUpIcon);
        homeAsUpIcon.setText(getString(R.string.textArrowIcon));
        homeAsUpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView titleTv= (TextView) findViewById(R.id.titleTv);
        titleTv.setText(getString(R.string.textAboutus));
        findViewById(R.id.filtericonIv).setVisibility(View.GONE);
        aboutUsPresenter=  new AboutUsPresenter(Injector.getAboutUsContent(this),this);


        aboutUsPresenter.fetchAboutUsContent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
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

    @Override
    public void showAboutUsContentOnScreen(String aboutUsContent) {

    }

    @Override
    public void showContactDetails(String contactDetails) {

    }

    @Override
    public void setPresenter(AboutUsViewAndPresenterContract.AboutUsPresenter presenter) {

    }
}
