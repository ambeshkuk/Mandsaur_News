package com.mandasur.app.news.adapters;

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
import com.mandasur.app.data.source.dao.requestdao.NewsDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ambesh on 13-02-2017.
 */



public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.NewsListViewHolder>{


    public interface OnNewsItemSelected{
        public void openNewsItem(String newsId);
        public void onClickOnShareBtn(News news);
    }



    private ArrayList<NewsDetail> newsArrayList;
    private HashMap<String,String> tranlationAndIndicatorHashMap=new HashMap<>();
    public RelatedNewsAdapter(ArrayList<NewsDetail> newsArrayList) {
        this.newsArrayList = newsArrayList;

    }

    @Override
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_related_news_list_item,parent,false);

        return new NewsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsListViewHolder holder, int position) {

         NewsDetail newsDetail=newsArrayList.get(position);


        holder.newsListBackgroundLl.setTag(newsDetail.getId());
        holder.newsListBackgroundLl.setOnClickListener(onClickListener);

        holder.newsTitleTv.setText(newsDetail.getTitle());


        if (!TextUtils.isEmpty(newsDetail.getImage())){

            Picasso.with(holder.newsImageIv.getContext()).load(  newsDetail.getImage()).into(holder.newsImageIv);
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
