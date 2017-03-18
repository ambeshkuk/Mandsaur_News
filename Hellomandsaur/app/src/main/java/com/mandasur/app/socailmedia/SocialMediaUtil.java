package com.mandasur.app.socailmedia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.mandasur.app.R;

/**
 * Utility class to perfrom common social media actions.
 * Created by ambesh on 18-03-2017.
 */
public class SocialMediaUtil {


    /**
     * Open the Hello Mandsur page on facebook.
     * @param activity
     */
    public static void openFacebookPageURL(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        String facebookUrl;
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                 facebookUrl="fb://facewebmodal/f?href=" + activity.getString(R.string.facebook_url);
            } else { //older versions of fb app
                 facebookUrl="fb://page/" + activity.getString(R.string.facebook_url);;
            }
        } catch (PackageManager.NameNotFoundException e) {
            facebookUrl=activity.getString(R.string.facebook_url);; //normal web url
        }

        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(facebookUrl));



        activity.startActivity(facebookIntent);
    }

    public static void openGooglePlusPage(Activity activity){

        activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(activity.getString(R.string.googleplus_url))));

    }

    public static void openTwitterPageUrl(Activity activity){
        activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(activity.getString(R.string.twitter_url))));
    }
}
