package com.mandasur.app.news.exceptions;

import android.content.Context;

import com.mandasur.app.R;

/**
 * Created by ambesh on 28-02-2017.
 */
public class ErrorMessageHandler {







    private static ErrorMessageHandler errorMessageHandler;
    public static ErrorMessageHandler getInstance(Context context){

        if (errorMessageHandler==null){
            errorMessageHandler=new ErrorMessageHandler(context);
        }
        return errorMessageHandler;
    }

    private Context context;
    public ErrorMessageHandler(Context context){
        this.context=context;
    }

    public String getApiErrorMessage(int errorCode){
        String errorMessage=null;
        switch (errorCode){

            case ErrorRequestCode.API_ERROR_REQUEST_CODE.ERRORCODE_NEESLIST_NOT_FOUDN:
                errorMessage=context.getString(R.string.textNoNewsFound);
                break;
            case ErrorRequestCode.API_ERROR_REQUEST_CODE.ERRORCODE_DETAILS_NOT_FOUDN:
                errorMessage=context.getString(R.string.textNewsDetailsNotFound);
                break;

            default:
                errorMessage=context.getString(R.string.textCannotProcessYourRequest);
        }
        return errorMessage;
    }

}
