package com.mandasur.app;

/**
 * Base View class
 * Created by ambesh on 29-01-2017.
 */
public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
