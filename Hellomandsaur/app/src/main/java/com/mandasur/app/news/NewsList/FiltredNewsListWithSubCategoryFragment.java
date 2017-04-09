package com.mandasur.app.news.NewsList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Ads;
import com.mandasur.app.data.source.dao.requestdao.BaseNews;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.news.AdvertiseDetailActivity;
import com.mandasur.app.news.NewsDetailsActivity;
import com.mandasur.app.news.NewsListContract;
import com.mandasur.app.news.adapters.NewsListAdapterWithSubCateories;
import com.mandasur.app.util.DividerItemDecoration;
import com.mandasur.app.util.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FiltredNewsListWithSubCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FiltredNewsListWithSubCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiltredNewsListWithSubCategoryFragment extends Fragment implements NewsListContract.NewsListView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static final String SUBCATEGORY_STING = "sub_category";





    public static final String SUB_CATEGORY_NAME="sub_category_name";


    // TODO: Rename and change types of parameters
    private String subCategory;
    private ProgressBar progressBar;
    private TextView networkNotAvalibleTv;

    private String subCategroyName;
    private SwipeRefreshLayout swipeToRefreshLayout;
    private OnFragmentInteractionListener mListener;
    private NewsListAdapterWithSubCateories newsListAdapterWithSubCateories;
    private ArrayList<BaseNews> newsArrayList;
    private NewsListContract.NewsListPresenter newsListPresenter;
    private boolean toRefreshCompleteList=false;
    private boolean toLoadMoreNews=false;
    private MandsaurDataBaseHelper mandsaurDataBaseHelper;
    private ProgressBar loadMoreProgress;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subCat Parameter 2.
     * @return A new instance of fragment NewsListFragment.
     */
    // TODO: Rename and change types and number of parameters


    public static FiltredNewsListWithSubCategoryFragment newInstance(String subCat) {
        FiltredNewsListWithSubCategoryFragment fragment = new FiltredNewsListWithSubCategoryFragment();
        Bundle args = new Bundle();

        args.putString(SUBCATEGORY_STING, subCat);

        fragment.setArguments(args);
        return fragment;
    }

    public FiltredNewsListWithSubCategoryFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subCategory = getArguments().getString(SUBCATEGORY_STING);


        }
    }

    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_news_list, container, false);;
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        loadMoreProgress= (ProgressBar) view.findViewById(R.id.loadMoreProgress);
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        networkNotAvalibleTv= (TextView) view.findViewById(R.id.networkNotAvalibleTv);
        swipeToRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefreshLayout);
        swipeToRefreshLayout.setOnRefreshListener(onRefreshListener);
        mandsaurDataBaseHelper= DatabaseNewsDataSource.getInstance(getActivity());
        subCategroyName=mandsaurDataBaseHelper.getSubCategoriesTable().
                getSubCategoryNameFromSubCategroyIndicator(mandsaurDataBaseHelper
                        .getSqLiteDatabase(),subCategory);
        newsListPresenter.fetchNewsFromServerBasedOnFiltre(subCategory);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            toRefreshCompleteList=true;
            endlessRecyclerViewScrollListener.resetState();
            newsListPresenter.fetchNewsFromServerBasedOnFiltre(subCategory);


        }
    };
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showNetworkNotAvailbel() {

        networkNotAvalibleTv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        swipeToRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingIndicator() {
        if (toLoadMoreNews){
            loadMoreProgress.setVisibility(View.VISIBLE);


        }
        else if (toRefreshCompleteList){
            networkNotAvalibleTv.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            swipeToRefreshLayout.setVisibility(View.GONE);

        }
        else{
            networkNotAvalibleTv.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            swipeToRefreshLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void showNewsListingBasedOnFilter(NewsFromMainCategoryResponse newsFromMainCategoryResponse) {

        Log.i(FiltredNewsListWithSubCategoryFragment.class.getSimpleName(), "showNewsListingBasedOnFilter");
        toRefreshCompleteList=false;


        if (newsFromMainCategoryResponse!=null&&newsFromMainCategoryResponse.getData()!=null){
            networkNotAvalibleTv.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            swipeToRefreshLayout.setVisibility(View.VISIBLE);
            loadMoreProgress.setVisibility(View.GONE);
            if (newsListAdapterWithSubCateories!=null){
                int lastSelectedPosition=0;
                if (!toLoadMoreNews){
                    newsArrayList.clear();

                }
                else{
                    toLoadMoreNews=false;
                    lastSelectedPosition=newsListAdapterWithSubCateories.getItemCount();
                }

                newsArrayList.addAll(newsFromMainCategoryResponse.getData().getNewsList());
                newsListAdapterWithSubCateories.notifyItemRangeInserted(lastSelectedPosition, newsArrayList.size()-1);
                swipeToRefreshLayout.setRefreshing(false);
            }
            else {
                newsArrayList=newsFromMainCategoryResponse.getData().getNewsList();
                newsListAdapterWithSubCateories =
                new NewsListAdapterWithSubCateories(newsArrayList);

                newsListAdapterWithSubCateories.setOnNewsItemSelected(onNewsItemSelected);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider_item_shape));
                endlessRecyclerViewScrollListener=new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        toLoadMoreNews=true;
                        newsListPresenter.setPageNumber(page);
                        newsListPresenter.fetchNewsFromServerBasedOnFiltre(subCategory);
                    }
                };
                recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
                recyclerView.setAdapter(newsListAdapterWithSubCateories);

            }




        }
        else {
            networkNotAvalibleTv.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            swipeToRefreshLayout.setVisibility(View.GONE);
            loadMoreProgress.setVisibility(View.GONE);
            networkNotAvalibleTv.setText(getString(R.string.textNoNewsFound));
        }



    }

    @Override
    public void showErrorOccured(String errorMessage) {
        networkNotAvalibleTv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        swipeToRefreshLayout.setVisibility(View.GONE);
        networkNotAvalibleTv.setText(errorMessage);
        toRefreshCompleteList=false;
        toLoadMoreNews=false;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void setPresenter(NewsListContract.NewsListPresenter presenter) {
            this.newsListPresenter=presenter;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private NewsListAdapterWithSubCateories.OnNewsItemSelected
            onNewsItemSelected=new NewsListAdapterWithSubCateories.OnNewsItemSelected() {
        @Override
        public void openNewsItem(String newsId) {

            Intent intent=new Intent(FiltredNewsListWithSubCategoryFragment.this.getActivity(), NewsDetailsActivity.class);
            intent.putExtra(NewsDetailsActivity.NEWS_ID,newsId);
            intent.putExtra(NewsDetailsActivity.CATEGORY_NAME,subCategroyName);
            startActivity(intent);
        }

        @Override
        public void onClickOnShareBtn(News news) {
            newsListPresenter.shareNewsOnSocialMedia(news);
        }

        @Override
        public void onNewsItemClickListner(Ads ads) {
            Intent intent=new Intent(FiltredNewsListWithSubCategoryFragment.this.getActivity()
                    , AdvertiseDetailActivity.class);



            intent.putExtra(AdvertiseDetailActivity.ADS_URL,ads.getAd_url());
            intent.putExtra(AdvertiseDetailActivity.ADS_FULL_IMAGE
                    ,ads.getAd_image_full());
            intent.putExtra(AdvertiseDetailActivity.ADS_TITLE,ads.getAd_title());
            FiltredNewsListWithSubCategoryFragment.this.startActivity(intent);
        }
    };



}
