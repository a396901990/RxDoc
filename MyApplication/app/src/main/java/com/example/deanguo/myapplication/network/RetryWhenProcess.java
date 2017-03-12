package com.example.deanguo.myapplication.network;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by DeanGuo on 3/12/17.
 */

public class RetryWhenProcess implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private long mInterval;

    private int retryTimes;

    public RetryWhenProcess(long interval) {
        mInterval = interval;
    }

    @Override
    public Observable<?> call(final Observable<? extends Throwable> observable) {
        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(Throwable throwable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable instanceof UnknownHostException) {
                            return Observable.error(throwable);
                        }

                        return Observable.just(throwable).filter(new Func1<Throwable, Boolean>() {
                            @Override
                            public Boolean call(Throwable throwable) {
                                retryTimes ++;
                                return retryTimes < mInterval;
                            }
                        }).delay((long) Math.pow(mInterval, retryTimes), TimeUnit.SECONDS);
                    }
                });
            }
        });
    }
}
