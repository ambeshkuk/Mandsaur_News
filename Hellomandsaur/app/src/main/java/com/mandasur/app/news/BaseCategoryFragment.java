package com.mandasur.app.news;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.news.adapters.CategoryAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseCategoryFragment extends Fragment implements DrawerContract.CategoryView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CategoryTabsAndDrawerPresenter categoryTabsAndDrawerPresenter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseCategoryFragment newInstance(String param1, String param2) {
        BaseCategoryFragment fragment = new BaseCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BaseCategoryFragment() {
        // Required empty public constructor
    }




    private TabLayout newsTl;
    private ViewPager baseNewsVp;
    private CategoryAdapter categoryAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_base_news, container, false);




        newsTl= (TabLayout) view.findViewById(R.id.newsTl);
        newsTl.setOnTabSelectedListener(onTabSelectedListener);
        baseNewsVp= (ViewPager) view.findViewById(R.id.baseNewsVp);

        categoryTabsAndDrawerPresenter.start();

        return  view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void showCategories(ArrayList<Category> categories) {

        baseNewsVp.setAdapter(new CategoryAdapter(getChildFragmentManager(), categories));

        newsTl.setupWithViewPager(baseNewsVp);



    }

    @Override
    public void openCategory(int categoryId) {

        baseNewsVp.setCurrentItem(categoryId);


    }

    @Override
    public void setPresenter(DrawerContract.CategroyPresenter presenter) {
            this.categoryTabsAndDrawerPresenter = (CategoryTabsAndDrawerPresenter) presenter;
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
/***
 * Listenr Objects
 */
    private TabLayout.OnTabSelectedListener onTabSelectedListener=new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
};
}
