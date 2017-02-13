package com.mandasur.app.news.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mandasur.app.Injector;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.news.NewsList.NewsListFragment;
import com.mandasur.app.news.NewsListContract;
import com.mandasur.app.news.NewsListPresenter;

import java.util.ArrayList;

/**
 * Created by ambesh on 01-02-2017.
 */
public class CategoryAdapter extends FragmentStatePagerAdapter {


    private ArrayList<Category> categories;


    private Context context;
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

        Category category=categories.get(position);
        NewsListFragment newsListFragment=NewsListFragment.newInstance(category.getCategoryIdentifier(),"sports");
        newsListFragment.setMainCategory(category.getCategoryTitle());
        NewsListContract.NewsListPresenter newsListPresenter=new NewsListPresenter(Injector.getNewsListByCategory(newsListFragment.getActivity()),newsListFragment,category.getCategoryIdentifier());


        return newsListFragment;
    }

    @Override
    public int getCount() {
        return categories.size();
    }
}
