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

import com.mandasur.app.BaseView;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Ads;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.requestdao.BaseNews;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.news.AdvertiseDetailActivity;
import com.mandasur.app.news.CategoryTabsAndDrawerPresenter;
import com.mandasur.app.news.NewsDetailsActivity;
import com.mandasur.app.news.NewsFilterActivity;
import com.mandasur.app.news.NewsListContract;
import com.mandasur.app.news.NewsListPresenter;
import com.mandasur.app.news.NewsVideoActivity;
import com.mandasur.app.news.adapters.NewsListAdapterWithSubCateories;
import com.mandasur.app.util.DividerItemDecoration;
import com.mandasur.app.util.EndlessRecyclerViewScrollListener;
import com.mandasur.app.util.MandsaurAppSharedPref;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends Fragment implements NewsListContract.NewsListView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MAIN_CATEOGRY_STRING = "main_category";
    private static final String SUBCATEGORY_STING = "sub_category";

    private static final String MAIN_CATEGORY_NAME="manin_category_name";
    private static final String IS_SUBCAT_AVALIBLE="IS_SUB_CAT_AVALIABLE";
    // TODO: Rename and change types of parameters
    private String mainCategory;

    private ProgressBar progressBar;
    private TextView networkNotAvalibleTv;
    private String categoryName;

    private SwipeRefreshLayout swipeToRefreshLayout;
    private OnFragmentInteractionListener mListener;
    private NewsListAdapterWithSubCateories newsListAdapterWithSubCateories;
    private ArrayList<BaseNews> newsArrayList;
    private NewsListContract.NewsListPresenter newsListPresenter;
    private boolean toRefreshCompleteList=false;
    private boolean toLoadMoreNews=false;
    private ProgressBar loadMoreProgress;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mainCat Parameter 1.
     * @param subCat Parameter 2.
     * @return A new instance of fragment NewsListFragment.
     */
    // TODO: Rename and change types and number of parameters


    public static NewsListFragment newInstance(String mainCat,String mainCategroyName,int subCatAvaliable) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(MAIN_CATEOGRY_STRING, mainCat);
        args.putString(MAIN_CATEGORY_NAME,mainCategroyName);
        args.putInt(IS_SUBCAT_AVALIBLE,subCatAvaliable);

        fragment.setArguments(args);
        return fragment;
    }

    public NewsListFragment() {
        // Required empty public constructor
    }


    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mainCategory = getArguments().getString(MAIN_CATEOGRY_STRING);

            categoryName=getArguments().getString(MAIN_CATEGORY_NAME);



            subCatAvalaible=getArguments().getInt(IS_SUBCAT_AVALIBLE);
        }
    }

    private RecyclerView recyclerView;
    private int subCatAvalaible=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_news_list, container, false);;
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        networkNotAvalibleTv= (TextView) view.findViewById(R.id.networkNotAvalibleTv);
        swipeToRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefreshLayout);
        swipeToRefreshLayout.setOnRefreshListener(onRefreshListener);
        loadMoreProgress= (ProgressBar) view.findViewById(R.id.loadMoreProgress);
        newsListPresenter.start();




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
        if (isVisibleToUser){
            MandsaurAppSharedPref.setCategoryName(getActivity(),mainCategory);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== NewsFilterActivity.REQUEST_CODE_FILTER_CHANGED){

            newsListPresenter.fetchNewsFromServerBasedOnFiltre("");

        }
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            toRefreshCompleteList=true;
            newsListPresenter.fetchNewsFromServerBasedOnFiltre("");

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

        Log.i(NewsListFragment.class.getSimpleName(), "showNewsListingBasedOnFilter");
        if (newsFromMainCategoryResponse!=null&&newsFromMainCategoryResponse.getData()!=null){
            networkNotAvalibleTv.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            swipeToRefreshLayout.setVisibility(View.VISIBLE);


            if (newsListAdapterWithSubCateories!=null){

                newsArrayList.clear();
                newsArrayList.addAll(newsFromMainCategoryResponse.getData().getNewsList());
                newsListAdapterWithSubCateories.notifyDataSetChanged();
                swipeToRefreshLayout.setRefreshing(false);
            }
            else {
                newsArrayList=newsFromMainCategoryResponse.getData().getNewsList();
                newsListAdapterWithSubCateories =
                new NewsListAdapterWithSubCateories(newsArrayList);
            }



            newsListAdapterWithSubCateories.setOnNewsItemSelected(onNewsItemSelected);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());

            endlessRecyclerViewScrollListener=new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    toLoadMoreNews=true;
                    newsListPresenter.setPageNumber(page);
                    newsListPresenter.fetchNewsFromServerBasedOnFiltre("");
                }
            };

            if (subCatAvalaible== Category.SUBCATEGORY_UN_AVAIALBLE){
                recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
            }

            recyclerView.setAdapter(newsListAdapterWithSubCateories);

        }
        else {
            networkNotAvalibleTv.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            swipeToRefreshLayout.setVisibility(View.GONE);
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
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void setPresenter(NewsListContract.NewsListPresenter presenter) {
            this.newsListPresenter=presenter;
        Log.i(this.getClass().getSimpleName(),"Presenter Setted "+mainCategory);
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

    private NewsListAdapterWithSubCateories.OnNewsItemSelected onNewsItemSelected=new
            NewsListAdapterWithSubCateories.OnNewsItemSelected() {
        @Override
        public void openNewsItem(String newsId) {

            Intent intent=new Intent(NewsListFragment.this.getActivity(), NewsVideoActivity.class);
            intent.putExtra(NewsDetailsActivity.NEWS_ID,newsId);
            intent.putExtra(NewsDetailsActivity.CATEGORY_NAME,categoryName);
            startActivity(intent);
        }

        @Override
        public void onClickOnShareBtn(News news) {

            newsListPresenter.shareNewsOnSocialMedia(news);
        }

                @Override
                public void onNewsItemClickListner(Ads ads) {


                    Intent intent=new Intent(NewsListFragment.this.getActivity()
                            , AdvertiseDetailActivity.class);



                    intent.putExtra(AdvertiseDetailActivity.ADS_URL,ads.getAd_url());
                    intent.putExtra(AdvertiseDetailActivity.ADS_FULL_IMAGE
                            ,ads.getAd_image_full());
                    intent.putExtra(AdvertiseDetailActivity.ADS_TITLE,ads.getAd_title());
                    NewsListFragment.this.startActivity(intent);

                }
            };
}
