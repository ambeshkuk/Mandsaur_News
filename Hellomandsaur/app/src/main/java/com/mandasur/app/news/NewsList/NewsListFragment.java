package com.mandasur.app.news.NewsList;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mandasur.app.BaseView;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.mandasur.app.news.CategoryTabsAndDrawerPresenter;
import com.mandasur.app.news.NewsListContract;
import com.mandasur.app.news.NewsListPresenter;
import com.mandasur.app.news.adapters.NewsListAdapterWithSubCateories;

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

    // TODO: Rename and change types of parameters
    private String mainCategory;
    private String subCategory;

    private OnFragmentInteractionListener mListener;

    private NewsListContract.NewsListPresenter newsListPresenter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mainCat Parameter 1.
     * @param subCat Parameter 2.
     * @return A new instance of fragment NewsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsListFragment newInstance(String mainCat, String subCat) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(MAIN_CATEOGRY_STRING, mainCat);

        args.putString(SUBCATEGORY_STING, subCat);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showNetworkNotAvailbel() {

    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void showNewsListingBasedOnFilter(NewsFromMainCategoryResponse newsFromMainCategoryResponse) {

        NewsListAdapterWithSubCateories newsListAdapterWithSubCateories=
                new NewsListAdapterWithSubCateories(newsFromMainCategoryResponse.getData().getNewsList());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsListAdapterWithSubCateories);


    }

    @Override
    public void showErrorOccured(String errorMessage) {

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

}
