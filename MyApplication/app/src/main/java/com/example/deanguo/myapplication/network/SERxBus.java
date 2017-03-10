package com.example.deanguo.myapplication.network;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by DeanGuo on 3/8/17.
 */
public class SERxBus {
    private final Subject<Object, Object> bus;

    private SERxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static SERxBus getDefault() {
        return RxBusHolder.sInstance;
    }

    private static class RxBusHolder {
        private static final SERxBus sInstance = new SERxBus();
    }


    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
