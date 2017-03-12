package com.example.deanguo.myapplication.network;

import android.os.Bundle;
import android.widget.Button;

import com.example.deanguo.myapplication.R;
import com.trello.rxlifecycle.components.RxActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class MainActivity extends RxActivity {

    @BindView(R.id.test_rxbus)
    Button test_rxbus;


    @BindView(R.id.test_zip)
    Button test_zip;


    @BindView(R.id.test_merge)
    Button test_merge;

    @BindView(R.id.test_contact)
    Button test_contact;


    @BindView(R.id.test_take)
    Button test_take;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        registerEvent();
    }

    @OnClick(R.id.test_rxbus)
    public void rxBusTest() {
        SERxBus.getDefault().post(new TestEvent("Event Success"));
    }

    @OnClick(R.id.test_zip)
    public void rxZipTest() {
        Observable
                .zip(analogNetwork(1, "test1"), analogNetwork(3, "test2")
                        , new Func2<String, String, String>() {
                            @Override
                            public String call(String s, String s2) {
                                return s + " + " + s2;
                            }
                        })
                .compose(this.<String>bindToLifecycle())
                .compose(SERxUtil.<String>changeScheduler())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        test_zip.setText(s);
                    }
                });
    }


    String result = "";

    @OnClick(R.id.test_merge)
    public void rxMergeTest() {

        Observable
                .merge(analogNetwork(1, "test1"), analogNetwork(3, "test2"))
                .compose(this.<String>bindToLifecycle())
                .compose(SERxUtil.<String>changeScheduler())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        result += s;
                        test_merge.setText(result);
                    }
                });
    }

    @OnClick(R.id.test_contact)
    public void rxContactTest() {
        Observable
                .concat(analogNetwork(0, "fromCache"), analogNetwork(2, "fromDatabase"), analogNetwork(3, "fromInternet"))
                .compose(this.<String>bindToLifecycle())
                .compose(SERxUtil.<String>changeScheduler())
                .first(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.equals("fromDatabase");
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                test_contact.setText(s);
            }
        });
    }

    int try_times = 0;
    @OnClick(R.id.test_take)
    public void testTakeUntil() {
        Observable.interval(1, TimeUnit.SECONDS)
                .flatMap(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long aLong) {
                        return analogNetwork(0, "test" + try_times);
                    }
                })
//                .takeUntil(new Func1<String, Boolean>() {
//
//                    @Override
//                    public Boolean call(String s) {
//                        return s.contains("5");
//                    }
//                })
                .take(3)
                .compose(this.<String>bindToLifecycle())
                .compose(SERxUtil.<String>changeScheduler())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        try_times ++;
                        test_take.setText(s);
                    }
                });

    }

    @OnClick(R.id.test_retry)
    public void testRetry() {
        analogBadNetwork(1, "test")
                .compose(SERxUtil.<String>bindToLifecycle(this))
                .compose(SERxUtil.<String>changeScheduler())
                .retryWhen(SERxUtil.retryTimes(3))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int a =0 ;
                    }

                    @Override
                    public void onNext(String s) {
                        int a =0 ;
                    }
                });
    }

    public void registerEvent() {
        SERxBus.getDefault()
                .toObservable(TestEvent.class)
                .compose(this.<TestEvent>bindToLifecycle())
                .compose(SERxUtil.<TestEvent>changeScheduler())
                .subscribe(new Action1<TestEvent>() {
                    @Override
                    public void call(TestEvent testEvent) {
                        test_zip.setText(testEvent.test);
                    }
                });
    }

    public Observable<String> analogNetwork(long delay, final String data) {
        return Observable.just(data).delay(delay, TimeUnit.SECONDS);
//        return Observable.timer(delay, TimeUnit.SECONDS).map(new Func1<Long, String>() {
//            @Override
//            public String call(Long aLong) {
//                return data;
//            }
//        });
    }

    public Observable<String> analogBadNetwork(long delay, final String data) {
        return Observable.just(data).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.error(new RuntimeException());
            }
        }).delay(delay, TimeUnit.SECONDS);
    }

}
