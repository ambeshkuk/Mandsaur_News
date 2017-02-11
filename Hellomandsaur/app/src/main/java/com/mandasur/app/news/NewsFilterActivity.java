package com.mandasur.app.news;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.news.adapters.SubCategoriesAdapter;
import com.mandasur.app.util.MandsaurNewsTextView;

import java.util.ArrayList;

/**
 * Created by ambesh on 11-02-2017.
 */
public class NewsFilterActivity extends AppCompatActivity  implements SubCategoryContract.SubCateogryFilterView{

    private SubCateoriesFilterPresenter subCateoriesFilterPresenter;
    private ListView listViewSubCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filter_mandsaur_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View view=getLayoutInflater().inflate(R.layout.layout_custom_tool_bar_layout,null);
        MandsaurNewsTextView homeAsUpIcon= (MandsaurNewsTextView) view.findViewById(R.id.homeAsUpIcon);

        homeAsUpIcon.setText(getString(R.string.textArrowIcon));
        toolbar.addView(view);
        subCateoriesFilterPresenter=new SubCateoriesFilterPresenter(Injector.getSubCategoryUseCase(this),Injector.getSubCategorySelected(this),this);

        listViewSubCategories= (ListView) findViewById(R.id.listViewSubCategories);

        subCateoriesFilterPresenter.start();

    }

    @Override
    public void showSubCatergories(ArrayList<SubCategories> subCategories) {
        SubCategoriesAdapter subCategoriesAdapter=new SubCategoriesAdapter(this,R.layout.layout_subcategories_list_item,subCategories);



        listViewSubCategories.setAdapter(subCategoriesAdapter);
    }

    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if(parent.getAdapter() instanceof SubCategoriesAdapter){


                SubCategories subCategories= (SubCategories) parent.getAdapter().getItem(position);

                if (subCategories.isItemChecked()){
                    subCategories.setIsItemChecked(false);

                }
                else {
                    subCategories.setIsItemChecked(true);
                }

                listViewSubCategories.notify();

            }

        }
    };
    @Override
    public void setPresenter(SubCateoriesFilterPresenter presenter) {
        this.subCateoriesFilterPresenter=presenter;
    }
}
