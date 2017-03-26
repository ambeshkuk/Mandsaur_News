package com.mandasur.app.aboutus_contact;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.util.MandsaurNewsTextView;

public class AboutUsActivity extends ActionBarActivity implements AboutUsViewAndPresenterContract.AboutUsView{


    private AboutUsPresenter aboutUsPresenter;
    private MandsaurNewsTextView homeAsUpIcon;
    private WebView aboutUsTv;
    private TextView titleNewsTv;
    private TextView emailTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_about_us);
        aboutUsTv= (WebView) findViewById(R.id.aboutUsTv);
        aboutUsTv.loadUrl("file:///android_asset/aboutus/about.html");
        titleNewsTv= (TextView) findViewById(R.id.titleNewsTv);
        emailTv= (TextView) findViewById(R.id.emailTv);
        emailTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "info@hellomandsaur.com");


                startActivity(Intent.createChooser(intent, "Send Email"));
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
