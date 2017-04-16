package com.mandasur.app.news.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.requestdao.News;

import java.util.ArrayList;

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


            switch (position){
                case 0:
                    holder.advertisementImageIv.setImageResource(R.drawable.advertise_with_us);
                    break;
                case 1:
                    holder.advertisementImageIv
                            .setImageResource(R.drawable.advertise_with_us_image3);

                    break;
                case 2:
                    holder.advertisementImageIv
                            .setImageResource(R.drawable.advertise_with_us);
                    break;
                case 3:
                    holder.advertisementImageIv
                            .setImageResource(R.drawable.advertise_with_us_image3);
                    break;
                case 4:
                    holder.advertisementImageIv
                            .setImageResource(R.drawable.advertise_with_us);
                    break;
                case 5:
                    holder.advertisementImageIv
                            .setImageResource(R.drawable.advertise_with_us_image3);
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
        return 5;
    }


    public class AdvertiseWithUsViewHolder extends RecyclerView.ViewHolder{


        ImageView advertisementImageIv;


        public AdvertiseWithUsViewHolder(View itemView) {
            super(itemView);






            advertisementImageIv = (ImageView) itemView.findViewById(R.id.advertisementImageIv);

        }
    }

    private  OnNewsItemSelected onNewsItemSelected;

    public void setOnNewsItemSelected(OnNewsItemSelected onNewsItemSelected) {
        this.onNewsItemSelected = onNewsItemSelected;
    }
}
