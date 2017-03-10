package com.example.deanguo.myapplication.ui;

import android.os.Handler;

/**
 * Created by zhangzhennan on 15/2/6.
 */
public class CountDown {

    final static private long COUNTDOWN_GAP = 1000;

    private long timeGap;
    private long value;
    private OnCountDownListener listener;

    private Handler handler;
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if(0 > value){
                stop();
            }else{
                if(null != getListener()){
                    getListener().onCountDown(value);
                }

                handler.postDelayed(task, getTimeGap());
            }
            value --;
        }
    };

    public CountDown(long value){
        this(value, COUNTDOWN_GAP);
    }

    public CountDown(long value, long timeGap){
        setTimeGap(timeGap);
        setValue(value);
        handler = new Handler();
    }

    public long getTimeGap() {
        return timeGap;
    }

    public void setTimeGap(long timeGap) {
        this.timeGap = timeGap;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public OnCountDownListener getListener() {
        return listener;
    }

    public void setListener(OnCountDownListener listener) {
        this.listener = listener;
    }

    public void start(){
        start(getValue());
    }

    public void start(long startWith){
        setValue(startWith);
        handler.removeCallbacks(task);
        handler.postDelayed(task, getTimeGap());

        if(null != getListener()){
            getListener().onStartCountDown();
        }
    }

    protected void stop(){
        handler.removeCallbacks(task);

        if(null != getListener()){
            getListener().onStopCountDown();
        }
    }

    public void cancel(){
        stop();
    }

    public interface OnCountDownListener{
        void onCountDown(long value);
        void onStartCountDown();
        void onStopCountDown();
    }


}
