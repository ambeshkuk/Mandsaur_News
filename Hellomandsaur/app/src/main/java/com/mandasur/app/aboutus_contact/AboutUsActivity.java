package com.mandasur.app.aboutus_contact;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.util.MandsaurNewsTextView;

public class AboutUsActivity extends ActionBarActivity implements AboutUsViewAndPresenterContract.AboutUsView{


    private TextView aboutUsTv;
    private AboutUsPresenter aboutUsPresenter;
    private MandsaurNewsTextView homeAsUpIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_about_us);
        homeAsUpIcon  = (MandsaurNewsTextView) findViewById(R.id.homeAsUpIcon);
        homeAsUpIcon.setText(getString(R.string.textArrowIcon));
        TextView titleTv= (TextView) findViewById(R.id.titleTv);
        titleTv.setText(getString(R.string.textAboutus));
        findViewById(R.id.filtericonIv).setVisibility(View.GONE);
        aboutUsTv= (TextView) findViewById(R.id.aboutUsTv);
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
        aboutUsTv.setText(aboutUsContent);
    }

    @Override
    public void showContactDetails(String contactDetails) {

    }

    @Override
    public void setPresenter(AboutUsViewAndPresenterContract.AboutUsPresenter presenter) {

    }
}
