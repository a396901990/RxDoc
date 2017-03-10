package com.example.deanguo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by deanguo on 1/4/17.
 */

public class SetEmailPage extends BaseSignUpFragment {

    private View root;

    public SetEmailPage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public int getPageIndex() {
        return STEP_SET_EMAIL;
    }

    @Override
    public boolean validate() {
        String email = inputView.getText().toString().trim();
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean isShowKeyBoard() {
        return true;
    }

    @Override
    public int getContentView() {
        return R.layout.set_email_page;
    }

    @Override
    public void initView() {

    }

    @Override
    public void clearView() {

    }

    @Override
    public void onContinuePress() {
    }
}
