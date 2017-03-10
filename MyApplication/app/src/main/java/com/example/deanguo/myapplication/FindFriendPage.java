package com.example.deanguo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by deanguo on 1/4/17.
 */

public class FindFriendPage extends BaseSignUpFragment {
    View passBtn;

    public FindFriendPage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        passBtn = rootView.findViewById(R.id.pass_btn);
        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionListener.onConfirm(getPageIndex()+1);
            }
        });
    }

    @Override
    public int getPageIndex() {
        return STEP_FIND_FRIEND;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public boolean isShowKeyBoard() {
        return false;
    }

    @Override
    public int getContentView() {
        return R.layout.find_friend_page;
    }

    @Override
    public void initView() {

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout(){
                        int heightDiff = rootView.getRootView().getHeight()- rootView.getHeight();
                        Log.e("heightDiff", heightDiff+"");
                        // r.left, r.top, r.right, r.bottom
                    }
                });
    }

    @Override
    public void clearView() {

    }

    @Override
    public void onContinuePress() {
        actionListener.onConfirm(getPageIndex()+1);
    }
}
