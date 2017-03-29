package com.mandasur.app.data.source.dao.requestdao;

/**
 * Created by ambesh on 09-02-2017.
 */
public class NewsFromMainCategoryRequest extends Request {





    public static final String MAIN_CAT="main_cat";
    public static final String SUB_CAT="sub_cat";


    public static final String PAGE_NO="page_no";

    public static final String CATEGORY="category";


    public static final String TRUE="true";
    public static final String FALSE="false";

    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer();
        if(this.containsKey(MAIN_CAT)){
            stringBuffer.append(this.get(MAIN_CAT));
        }
        if (this.containsKey(SUB_CAT)){
            stringBuffer.append(this.get(SUB_CAT));
        }

        return stringBuffer.toString();
    }
}
