package com.mandasur.app.news.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.GoogleAPIException;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.google.api.translate.TranslateV2;
import com.mandasur.app.R;

import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ambesh on 13-02-2017.
 */



public class NewsListAdapterWithSubCateories extends RecyclerView.Adapter<NewsListAdapterWithSubCateories.NewsListViewHolder>{


    public interface OnNewsItemSelected{
        public void openNewsItem(String newsId);
    }


    private ArrayList<News> newsArrayList;
    private HashMap<String,String> tranlationAndIndicatorHashMap=new HashMap<>();
    public NewsListAdapterWithSubCateories(ArrayList<News> newsArrayList) {
        this.newsArrayList = newsArrayList;

    }

    @Override
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_list_item,parent,false);

        return new NewsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsListViewHolder holder, int position) {

        News news=newsArrayList.get(position);

        if (news.isSubcategoryStart()){
            if (!TextUtils.isEmpty(news.getSubCategoryName())&&(!news.getSubCategoryName().equals("news"))){}
            holder.newsHeader.setVisibility(View.VISIBLE);
            holder.subCategoryTitle.setText(news.getSubCategoryName());

            holder.viewAllTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Comming Soon",Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            holder.newsHeader.setVisibility(View.GONE);
        }
        holder.newsListBackgroundLl.setTag(news.getFid());
        holder.newsListBackgroundLl.setOnClickListener(onClickListener);
        holder.newsTimeTv.setText(news.getDate());
        holder.newsTitleTv.setText(news.getTitle());

        if (!TextUtils.isEmpty(news.getImage())){

            Picasso.with(holder.newsImageIv.getContext()).load("http://" + news.getImage()).into(holder.newsImageIv);
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


    public class NewsListViewHolder extends RecyclerView.ViewHolder{

        TextView subCategoryTitle,viewAllTv,newsTitleTv,newsTimeTv;
        ImageView newsImageIv;
        RelativeLayout newsHeader,newsListBackgroundLl;
        ;

        public NewsListViewHolder(View itemView) {
            super(itemView);





            subCategoryTitle= (TextView) itemView.findViewById(R.id.subCategoryTitle);
            viewAllTv= (TextView) itemView.findViewById(R.id.viewAllTv);
            newsTitleTv= (TextView) itemView.findViewById(R.id.newsTitleTv);
            newsTimeTv= (TextView) itemView.findViewById(R.id.newsTimeTv);
            newsImageIv= (ImageView) itemView.findViewById(R.id.newsImageIv);
            newsHeader= (RelativeLayout) itemView.findViewById(R.id.newsHeader);
            newsListBackgroundLl= (RelativeLayout) itemView.findViewById(R.id.newsListBackgroundLl);
        }
    }

    private  OnNewsItemSelected onNewsItemSelected;

    public void setOnNewsItemSelected(OnNewsItemSelected onNewsItemSelected) {
        this.onNewsItemSelected = onNewsItemSelected;
    }
}
