package com.example.deanguo.myapplication.network;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.deanguo.myapplication.R;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.ActivityLifecycleProvider;
import com.trello.rxlifecycle.components.RxActivity;

import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class MainActivity extends AppCompatActivity implements ActivityLifecycleProvider {
    MainActivity appCompatActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resigerEvent();
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        setContentView(R.layout.activity_main);
        appCompatActivity = this;
        Button button = (Button) this.findViewById(R.id.test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SERxBus.getDefault().post(new TestEvent(3));
            }
        });

        SelectClient.getInstance().SignUpService()
                .checkPhone("phone", "123885858")
                .compose(SERxUtil.<JSONObject>init())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        int a = 1;
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        int b =0 ;
                    }
                });
    }

    public void resigerEvent() {
        SERxBus.getDefault().toObservable(TestEvent.class).subscribe(new Action1<TestEvent>() {
            @Override
            public void call(TestEvent testEvent) {
                int a = testEvent.test;
                int b = 0;
            }
        });
    }
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindUntilEvent(ActivityEvent event) {
        return RxLifecycle.bindUntilActivityEvent(lifecycleSubject, event);
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindActivity(lifecycleSubject);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }
}
