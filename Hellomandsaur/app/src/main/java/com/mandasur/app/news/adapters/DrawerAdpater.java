package com.mandasur.app.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Category;

import java.util.ArrayList;


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

    private int position=-1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            convertView=layoutInflater.inflate(R.layout.row_item,parent,false);

        if (position==this.position){
            convertView.setSelected(true);
        }
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


    public void setSelectedPosition(int position){
this.position=position;
    }
}
