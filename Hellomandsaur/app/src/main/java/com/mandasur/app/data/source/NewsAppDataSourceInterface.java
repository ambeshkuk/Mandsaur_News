package com.mandasur.app.data.source;


import com.mandasur.app.data.source.dao.requestdao.NewsDetailFromIdRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailsFromResponse;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.mandasur.app.data.source.dao.requestdao.Request;
import com.mandasur.app.data.source.dao.requestdao.Response;


/**
 * Created by ambesh on 29-01-2017.
 */
public interface NewsAppDataSourceInterface {

    interface LoadNewsCallBack<E extends Response>{

        public void onNewsLoadedSuccesfully(E response);
        public boolean onDataLoadingUnsuccessful();
    }

    interface GetNewsDetailsOfNews<E extends Response>{
        public void onNewsDetailsFetchedSucessfully(E news);
        public boolean onDataLoadingUnsuccessful();
    }

    public NewsFromMainCategoryResponse getNewsListOnMainTabs(NewsFromMainCategoryRequest request);
    public NewsDetailsFromResponse  getNewsListOnSubCategories(NewsDetailFromIdRequest request);
    public NewsDetailsFromResponse  getNewsDetailsFromId(NewsDetailFromIdRequest request);

}
