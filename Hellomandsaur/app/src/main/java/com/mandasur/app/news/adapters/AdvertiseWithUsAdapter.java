package com.mandasur.app.news.adapters;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.requestdao.News;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ambesh on 13-02-2017.
 */



public class AdvertiseWithUsAdapter extends RecyclerView.Adapter<AdvertiseWithUsAdapter.AdvertiseWithUsViewHolder>{


    public interface OnNewsItemSelected{
        public void openNewsItem(String newsId);
        public void onClickOnShareBtn(News news);
    }



    private ArrayList<News> newsArrayList;

    public AdvertiseWithUsAdapter(ArrayList<News> newsArrayList) {
        this.newsArrayList = newsArrayList;

    }

    @Override
    public AdvertiseWithUsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_advertise_with_us,parent,false);

        return new AdvertiseWithUsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdvertiseWithUsViewHolder holder, int position) {

//        final News news=newsArrayList.get(position);
//
//
//        holder.newsListBackgroundLl.setTag(news.getFid());
//        holder.newsListBackgroundLl.setOnClickListener(onClickListener);
//
//        holder.newsTitleTv.setText(news.getTitle());
//
//
//        if (!TextUtils.isEmpty(news.getImage())){
//
//            Picasso.with(holder.newsImageIv.getContext()).load("http://" + news.getImage()).into(holder.newsImageIv);
//        }
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
        return 7;
    }


    public class AdvertiseWithUsViewHolder extends RecyclerView.ViewHolder{

        TextView subCategoryTitle,viewAllTv,newsTitleTv,newsTimeTv;
        ImageView newsImageIv;
        RelativeLayout newsHeader,newsListBackgroundLl;
        FloatingActionButton shareFb;

        public AdvertiseWithUsViewHolder(View itemView) {
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
