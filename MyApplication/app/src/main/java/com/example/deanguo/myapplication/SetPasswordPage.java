package com.example.deanguo.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by deanguo on 1/4/17.
 */

public class SetPasswordPage extends BaseSignUpFragment {

    boolean isShowPassword;

    public SetPasswordPage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateIconClear(inputView);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void updateIconClear(EditText editText)
    {
        Drawable[] drawables = editText.getCompoundDrawables();
        if (isShowPassword)
        {
            editText.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], getResources().getDrawable(R.drawable.ico_eyes_open), drawables[3]);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], getResources().getDrawable(R.drawable.ico_eyes_close), drawables[3]);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    public int getPageIndex() {
        return STEP_SET_PASSWORD;
    }

    @Override
    public boolean validate() {
        String password = inputView.getText().toString();
        if (!TextUtils.isEmpty(password) && password.length() >= 4) {
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
        return R.layout.set_password_page;
    }

    @Override
    public void initView() {
        updateIconClear(inputView);

        inputView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    // clear text action for EditText
                    int xDown = (int) event.getX();
                    if (xDown >= (inputView.getWidth() - inputView.getCompoundPaddingRight()) && xDown < inputView.getWidth())
                    {
                        isShowPassword = !isShowPassword;
                        updateIconClear(inputView);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void clearView() {

    }

    @Override
    public void onContinuePress() {

    }
}
