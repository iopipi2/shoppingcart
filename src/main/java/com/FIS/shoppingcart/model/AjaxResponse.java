package com.FIS.shoppingcart.model;

import java.util.List;

public class AjaxResponse {
    private int statusCode;
    private Object data;
    private List<Object> list;

    public AjaxResponse() {
    }

    public AjaxResponse(int statusCode,  Object data) {
        super();
        this.statusCode = statusCode;
        this.data = data;
    }



    public AjaxResponse(int statusCode, Object data, List<Object> list) {
        super();
        this.statusCode = statusCode;
        this.data = data;
        this.list = list;
    }



    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }






}
