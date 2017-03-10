package com.example.deanguo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by deanguo on 1/10/17.
 */

public class SetProfilePage extends BaseSignUpFragment {
    View avatar;

    public SetProfilePage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        avatar = rootView.findViewById(R.id.profile_avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getPageIndex() {
        return STEP_SET_PROFILE;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public boolean isShowKeyBoard() {
        return true;
    }

    @Override
    public int getContentView() {
        return R.layout.set_profile_page;
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
