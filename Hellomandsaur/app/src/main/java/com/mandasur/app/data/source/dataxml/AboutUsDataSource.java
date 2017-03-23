package com.mandasur.app.data.source.dataxml;

import android.content.Context;

import com.mandasur.app.R;

/**
 * Created by ambesh on 23-03-2017.
 */
public class AboutUsDataSource {
    private Context context;

    public AboutUsDataSource(Context context) {
        this.context = context;
    }

    public String getAboutUsContentFromDataXml(){

        String aboutUsContent=context.getString(R.string.about_us_content);

        return aboutUsContent;
    }
}
