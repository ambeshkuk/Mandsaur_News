package com.mandasur.app.data.source;

import com.mandasur.app.data.source.dao.News;
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

    interface GetNewsDetailsOfNews{
        public <E extends Response> void onNewsDetailsFetchedSucessfully(E news);
        public boolean onDataLoadingUnsuccessful();
    }

    public <T extends Request> void getNewsListOnMainTabs(T request,LoadNewsCallBack loadNewsCallBack);
    public <T extends Request> void getNewsListOnSubCategories(T request,LoadNewsCallBack loadNewsCallBack);
    public <T extends Request> void getNewsDetailsFromId(T request,GetNewsDetailsOfNews getNewsDetailsOfNews);

}
