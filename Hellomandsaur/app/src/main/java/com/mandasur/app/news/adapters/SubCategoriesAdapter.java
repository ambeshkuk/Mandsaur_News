package com.mandasur.app.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.util.MandsaurNewsTextView;

import java.util.List;

/**
 * Created by ambesh on 11-02-2017.
 */




public class SubCategoriesAdapter extends ArrayAdapter<SubCategories> {
    LayoutInflater layoutInflater;
    public SubCategoriesAdapter(Context context, int resource, List<SubCategories> objects) {
        super(context, resource, objects);

        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        SubCategories subCategories=getItem(position);
        convertView=layoutInflater.inflate(R.layout.layout_subcategories_list_item,parent,false);

        MandsaurNewsTextView subcategoryTv= (MandsaurNewsTextView) convertView.findViewById(R.id.subcategoryTv);
        CheckBox subcategoryCb= (CheckBox) convertView.findViewById(R.id.subcategoryCb);
        if (subCategories.isItemChecked()){
            subcategoryCb.setChecked(true);
        }
        else {
            subcategoryCb.setChecked(false);
        }

        subcategoryTv.setText(subCategories.getSubCategoryName());


        return convertView;

    }



}
