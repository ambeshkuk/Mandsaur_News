package com.mandasur.app.news;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mandasur.app.Injector;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.news.adapters.SubCategoriesAdapter;
import com.mandasur.app.util.MandsaurNewsTextView;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ambesh on 11-02-2017.
 */
public class NewsFilterActivity extends AppCompatActivity  implements SubCategoryContract.SubCateogryFilterView{

    private SubCateoriesFilterPresenter subCateoriesFilterPresenter;
    private ListView listViewSubCategories;
    private TextView doneTv;
    private ImageView filtericonIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filter_mandsaur_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        MandsaurNewsTextView homeAsUpIcon= (MandsaurNewsTextView)findViewById(R.id.homeAsUpIcon);

        homeAsUpIcon.setText(getString(R.string.textArrowIcon));
        doneTv= (TextView) findViewById(R.id.doneTv);
        filtericonIv= (ImageView) findViewById(R.id.filtericonIv);
        filtericonIv.setVisibility(View.GONE);
        doneTv.setOnClickListener(onClickListener);
        homeAsUpIcon.setOnClickListener(onClickListener);

        subCateoriesFilterPresenter=new SubCateoriesFilterPresenter(Injector.getSubCategoryUseCase(this),Injector.getSubCategorySelected(this),this);

        listViewSubCategories= (ListView) findViewById(R.id.listViewSubCategories);

        subCateoriesFilterPresenter.start();

    }

    private View.OnClickListener  onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getId()){
                case R.id.doneTv:
                    finish();
                    break;
                case R.id.homeAsUpIcon:
                    finish();
                    break;
            }
        }
    };
    @Override
    public void showSubCatergories(ArrayList<SubCategories> subCategories) {
        SubCategoriesAdapter subCategoriesAdapter=new SubCategoriesAdapter(this,R.layout.layout_subcategories_list_item,subCategories);



        listViewSubCategories.setAdapter(subCategoriesAdapter);
        listViewSubCategories.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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


                DatabaseNewsDataSource.getInstance(NewsFilterActivity.this)
                        .getSubCategoriesTable().updateIsItemCheckdOrNot(DatabaseNewsDataSource
                        .getInstance(NewsFilterActivity.this).getSqLiteDatabase(),subCategories.getSubCategoryId(),subCategories.isItemChecked());
                listViewSubCategories.notify();

            }

        }
    };
    @Override
    public void setPresenter(SubCateoriesFilterPresenter presenter) {
        this.subCateoriesFilterPresenter=presenter;
    }
}
