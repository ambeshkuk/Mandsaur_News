package com.mandasur.app.news.adapters;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mandasur.app.R;

import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.news.FiltredNewsListActivity;
import com.mandasur.app.news.NewsList.FiltredNewsListWithSubCategoryFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ambesh on 13-02-2017.
 */



public class NewsListAdapterWithSubCateories extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int TYPE_ADVERTISEMENT=0;
    private final int TYPE_NEWS=1;

    public interface OnNewsItemSelected{
        public void openNewsItem(String newsId);
        public void onClickOnShareBtn(News news);
    }


    private ArrayList<News> newsArrayList;

    public NewsListAdapterWithSubCateories(ArrayList<News> newsArrayList) {
        this.newsArrayList = newsArrayList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){

            case TYPE_ADVERTISEMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_advertising_item,parent,false);

                return new AdvertisementViewHolder(view);

            case TYPE_NEWS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_list_item,parent,false);

                return new NewsListViewHolder(view);
            default:
                 view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_list_item,parent,false);

                return new NewsListViewHolder(view);

        }

    }

    @Override
    public int getItemViewType(int position) {
        News news=newsArrayList.get(position);

        if (news.isAdvertisedNewsBean()){
            return TYPE_ADVERTISEMENT;
        }
        else
        {
            return TYPE_NEWS;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemType=getItemViewType(position);

        switch (itemType){
            case TYPE_NEWS:
               NewsListViewHolder newsListViewHolder= (NewsListViewHolder) holder;
                News news=newsArrayList.get(position);

                if (news.isSubcategoryStart()){
                    if (!TextUtils.isEmpty(news.getSubCategoryName())&&(!news.getSubCategoryName().equals("news"))){}
                    newsListViewHolder.newsHeader.setVisibility(View.VISIBLE);
                    newsListViewHolder.subCategoryTitle.setText(news.getSubCategoryName());
                    newsListViewHolder.viewAllTv.setTag(news.getSubCategoryName());
                    newsListViewHolder.viewAllTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(v.getContext(), FiltredNewsListActivity.class);
                            intent.putExtra(FiltredNewsListWithSubCategoryFragment.SUBCATEGORY_STING,(String)v.getTag());
                            v.getContext().startActivity(intent);

                        }
                    });

                }
                else
                {
                    newsListViewHolder.newsHeader.setVisibility(View.GONE);
                }
                newsListViewHolder.newsListBackgroundLl.setTag(news.getId());
                newsListViewHolder.newsListBackgroundLl.setOnClickListener(onClickListener);
                newsListViewHolder.newsTimeTv.setText(news.getDate());
                newsListViewHolder.newsTitleTv.setText(news.getTitle());

                newsListViewHolder.shareFb.setTag(news);
//                newsListViewHolder.shareFb.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (v.getTag() instanceof News){
//                            onNewsItemSelected.onClickOnShareBtn((News) v.getTag());
//                        }
//                    }
//                });
                if (!TextUtils.isEmpty(news.getImage())){

                    Picasso.with(newsListViewHolder.newsImageIv.getContext()).load("http://" + news.getImage()).
                            into(newsListViewHolder.newsImageIv);
                }
                break;
            case TYPE_ADVERTISEMENT:


                break;
        }

    }




private View.OnClickListener onClickListener=new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String fid= (String) v.getTag();
        onNewsItemSelected.openNewsItem(fid);
    }
};

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }


    public class AdvertisementViewHolder extends RecyclerView.ViewHolder{

        TextView adView;
        public AdvertisementViewHolder(View itemView) {
            super(itemView);
            adView= (TextView) itemView.findViewById(R.id.adView);
        }
    }

    public class NewsListViewHolder extends RecyclerView.ViewHolder{

        TextView subCategoryTitle,viewAllTv,newsTitleTv,newsTimeTv;
        ImageView newsImageIv;
        RelativeLayout newsHeader,newsListBackgroundLl;
        FloatingActionButton shareFb;

        public NewsListViewHolder(View itemView) {
            super(itemView);





            subCategoryTitle= (TextView) itemView.findViewById(R.id.subCategoryTitle);
            viewAllTv= (TextView) itemView.findViewById(R.id.viewAllTv);
            newsTitleTv= (TextView) itemView.findViewById(R.id.newsTitleTv);
            newsTimeTv= (TextView) itemView.findViewById(R.id.newsTimeTv);
            newsImageIv= (ImageView) itemView.findViewById(R.id.newsImageIv);
            newsHeader= (RelativeLayout) itemView.findViewById(R.id.newsHeader);
            shareFb= (FloatingActionButton) itemView.findViewById(R.id.shareFb);
            newsListBackgroundLl= (RelativeLayout) itemView.findViewById(R.id.newsListBackgroundLl);
        }
    }

    private  OnNewsItemSelected onNewsItemSelected;

    public void setOnNewsItemSelected(OnNewsItemSelected onNewsItemSelected) {
        this.onNewsItemSelected = onNewsItemSelected;
    }
}
