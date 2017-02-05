package com.mandasur.app.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mandasur.app.R;
import com.mandasur.app.data.Category;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ambesh on 02-02-2017.
 */










public class DrawerAdpater extends ArrayAdapter<Category> {

    private ArrayList<Category> categories;

    private LayoutInflater layoutInflater;

    public DrawerAdpater(Context context, ArrayList<Category> objects) {

        super(context, R.layout.row_item, objects);

        layoutInflater=LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            convertView=layoutInflater.inflate(R.layout.row_item,parent,false);

            Category category=getItem(position);
            TextView categoryNameTv= (TextView) convertView.findViewById(R.id.categoryNameTv);
           categoryNameTv.setText(category.getCategoryTitle());
            categoryNameTv.setCompoundDrawables(categoryNameTv
                .getContext().getResources().getDrawable(category.getImageId())
                ,null,null,null);
                return convertView;
    }


    @Override
    public Category getItem(int position) {
        return super.getItem(position);
    }


}
