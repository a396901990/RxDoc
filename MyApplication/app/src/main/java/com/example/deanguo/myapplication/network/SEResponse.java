package com.example.deanguo.myapplication.network;

/**
 * Created by DeanGuo on 3/8/17.
 */

public class SEResponse<T> {

    private int rc;
    private T data;

    public boolean isSuccess() {
        return rc == 0;
    }
    public int getRc() {
        return rc;
    }

    public T getData() {
        return data;
    }

}
