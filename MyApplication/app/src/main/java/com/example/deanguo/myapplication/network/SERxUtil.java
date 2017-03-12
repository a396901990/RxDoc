package com.example.deanguo.myapplication.network;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.RxActivity;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by DeanGuo on 3/9/17.
 */
public class SERxUtil {

    public static final <T> Observable.Transformer<T, T> changeScheduler() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static final <T> Observable.Transformer<SEResponse<T>, T> init() {
        return new Observable.Transformer<SEResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<SEResponse<T>> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Func1<SEResponse<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(SEResponse<T> response) {
                                if (response.isSuccess()) {
                                    return createData(response.getData());
                                } else {
                                    return Observable.error(new SEApiException(response.getRc()));
                                }
                            }
                        });
            }
        };
    }

    public static final <T> Observable.Transformer<T, T> setTimeOut(final long timeOut) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.timeout(timeOut, TimeUnit.SECONDS);
            }
        };
    }

    public static final <T> Observable.Transformer<T, T> bindToLifecycle(RxActivity rxActivity) {
        return RxLifecycle.bindActivity(rxActivity.lifecycle());
    }

    public static final <T> Observable.Transformer<T, T> bindToLifecycle(BehaviorSubject<ActivityEvent> lifecycleSubject) {
        return RxLifecycle.bindActivity(lifecycleSubject);
    }

    /**
     * handle error
     */
    public static final <T> Observable.Transformer<SEResponse<T>, T> handleErrorResult() {
        return new Observable.Transformer<SEResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<SEResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<SEResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(SEResponse<T> response) {
                        if (response.isSuccess()) {
                            return createData(response.getData());
                        } else {
                            return Observable.error(new SEApiException(response.getRc()));
                        }
                    }
                });
            }
        };
    }

    public static final <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Func1<Observable<? extends Throwable>, Observable<?>> retryTimes(final int times) {
        return new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> errors) {
                return errors.zipWith(Observable.range(1, times), new Func2<Throwable, Integer, Integer>() {
                    @Override
                    public Integer call(Throwable throwable, Integer i) {
                        return i;
                    }
                }).flatMap(new Func1<Integer, Observable<? extends Long>>() {
                    @Override
                    public Observable<? extends Long> call(Integer retryCount) {
                        return Observable.timer((long) Math.pow(5, retryCount), TimeUnit.SECONDS);
                    }
                });
            }
        };
    }
}
