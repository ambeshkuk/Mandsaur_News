package com.mandasur.app.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mandasur.app.Config;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.news.NewsDetailsActivity;
import com.mandasur.app.util.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ambesh on 15-04-2017.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;



        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                //code for debugging purpose
                Map<String,String> hashMap=  remoteMessage. getData();
                JSONObject jsonObject=new JSONObject();
                for (String key:hashMap.keySet()){
                    jsonObject.put(key,hashMap.get(key));
                }

                JSONObject json = new JSONObject(jsonObject.toString());

                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }



    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json;

            String newsId = data.getString(News.ID);
            String newsTitle = data.getString(News.TITLE);
            String newsCategroryName=data.getString(NewsDetailsActivity.CATEGORY_NAME);






                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(),
                        NewsDetailsActivity.class);
                resultIntent.putExtra(NewsDetailsActivity.NEWS_ID, newsId);
                resultIntent.putExtra(NewsDetailsActivity.CATEGORY_NAME,newsCategroryName);

                // check for image attachment

                    // image is present, show notification with image
                    showNotificationMessage(getApplicationContext(), "", newsTitle,resultIntent);


        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message,
                                         Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }



}
