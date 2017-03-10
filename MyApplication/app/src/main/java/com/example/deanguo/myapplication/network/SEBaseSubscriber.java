package com.example.deanguo.myapplication.network;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by DeanGuo on 3/8/17.
 */

public class SEBaseSubscriber<T> extends Subscriber<T> {
    private Context mContext;

    public SEBaseSubscriber() {
    }

    public SEBaseSubscriber(Context context) {
        mContext = context;
    }

    @Override
    public void onStart() {
        boolean hasInternet = false;
        if (!hasInternet) {
            this.onError(new SEApiException(SEApiException.ERROR_NO_INTERNET));
            return;
        }

    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        int a = 0;
//        ApiErrorHelper.handleCommonError(mContext, e);
    }

    @Override
    public void onNext(T t) {
        int b = 0;
    }
}
