package com.example.deanguo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * Created by deanguo on 1/4/17.
 */

public class SetUserNamePage extends BaseSignUpFragment {

    TextView wrongHint;

    public SetUserNamePage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wrongHint = (TextView) rootView.findViewById(R.id.wrong_hint);
    }

    @Override
    public int getPageIndex() {
        return STEP_SET_USERNAME;
    }

    @Override
    public boolean validate() {
        int length = inputView.getText().length();
        if (length > 0 && length < 16) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isShowKeyBoard() {
        return true;
    }

    @Override
    public int getContentView() {
        return R.layout.set_user_name_page;
    }

    @Override
    public void initView() {
        wrongHint.setVisibility(View.INVISIBLE);
        inputView.setText("");
        inputView.requestFocus();

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
