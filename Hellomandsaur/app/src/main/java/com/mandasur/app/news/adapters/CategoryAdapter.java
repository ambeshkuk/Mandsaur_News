package com.mandasur.app.news.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.news.NewsList.NewsListFragment;
import com.mandasur.app.news.NewsListContract;
import com.mandasur.app.news.NewsListPresenter;

import java.util.ArrayList;

/**
 * Created by ambesh on 01-02-2017.
 */
public class CategoryAdapter extends FragmentStatePagerAdapter {


    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private ArrayList<Category> categories;


    private Context context;
    public CategoryAdapter(FragmentManager fm,
                           ArrayList<Category>  categories,Context context) {
        super(fm);
        this.context=context;
        this.categories=categories;
    }


    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        TextView v = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_tab_textview, null);

        v.setText(categories.get(position).getCategoryTitle());

        return v;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }


    @Override
    public Fragment getItem(int position) {

        Category category=categories.get(position);
        NewsListFragment newsListFragment=NewsListFragment.newInstance(category.getCategoryIdentifier(),"sports");
        newsListFragment.setMainCategory(category.getCategoryTitle());
        NewsListContract.NewsListPresenter newsListPresenter=new NewsListPresenter(Injector.getNewsListByCategory(context),newsListFragment,category.getCategoryIdentifier());


        return newsListFragment;
    }




    @Override
    public int getCount() {
        return categories.size();
    }
}
