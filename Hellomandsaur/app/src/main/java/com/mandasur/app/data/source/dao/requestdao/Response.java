package com.mandasur.app.data.source.dao.requestdao;








/**
 * Created by ambesh on 09-02-2017.
 */
public class Response {






    boolean isSuccessful;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status="0";
    public boolean isSuccessful() {




        return (status!=null&&status.equals("1"))?true:false;
    }


}
