package com.mandasur.app.data.source.dao.requestdao;

/**
 * Created by ambesh on 09-02-2017.
 */
public class NewsFromMainCategoryRequest extends Request {

    public static final String CAT="cat";
    public static final String SUB_CAT="sub_cat";


    public static final String CATEGORY="category";

    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer();
        if(this.containsKey(CAT)){
            stringBuffer.append(this.get(CAT));
        }
        if (this.containsKey(SUB_CAT)){
            stringBuffer.append(this.get(SUB_CAT));
        }
        if (this.containsKey(CATEGORY)){
            stringBuffer.append(this.get(CATEGORY));
        }
        return stringBuffer.toString();
    }
}
