package com.example.deanguo.myapplication.SignIn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.deanguo.myapplication.R;
import com.example.deanguo.myapplication.network.SEBaseSubscriber;
import com.example.deanguo.myapplication.network.SEApiException;
import com.example.deanguo.myapplication.network.SEResponse;
import com.example.deanguo.myapplication.network.SERxUtil;
import com.example.deanguo.myapplication.network.SelectClient;

import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by deanguo on 1/17/17.
 */

public class LoginActivity extends AppCompatActivity {
    protected NavigatorUtil navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        navigator = new NavigatorUtil(this);
        navigator.setRootFragment(LoginPage.class, null, R.id.fragment_container);

        SelectClient selectClient = new SelectClient();
        selectClient.SignUpService()
                .checkPhone("phone", "+8613142423363")
                .compose(SERxUtil.<SEResponse<JSONObject>>changeScheduler())
                .compose(SERxUtil.<JSONObject>handleErrorResult())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SEApiException) {
                            int a = ((SEApiException)e).getErrorCode();
                            int b = a;
                        }
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        int i = 0;
                    }
                });
    }

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<SEResponse<T>, T> applyError() {
        return new Observable.Transformer<SEResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<SEResponse<T>> responseObservable) {
                responseObservable.map(new Func1<SEResponse<T>, T>() {
                    @Override
                    public T call(SEResponse<T> tResponse) {
                        if (!tResponse.isSuccess()) {
                            throw new SEApiException(tResponse.getRc());
                        }
                        return tResponse.getData();
                    }
                });
                return null;
            }
        };
    }
}
