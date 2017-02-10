package com.mandasur.app.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.news.NewsListFragment;

import java.util.ArrayList;

/**
 * Created by ambesh on 01-02-2017.
 */
public class CategoryAdapter extends FragmentStatePagerAdapter {


    private ArrayList<Category> categories;


    public CategoryAdapter(FragmentManager fm,
                           ArrayList<Category>  categories) {
        super(fm);
        this.categories=categories;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getCategoryTitle();
    }


    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance("","");
    }

    @Override
    public int getCount() {
        return categories.size();
    }
}
