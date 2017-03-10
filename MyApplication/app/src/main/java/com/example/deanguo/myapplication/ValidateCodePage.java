package com.example.deanguo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.deanguo.myapplication.ui.CountDown;

/**
 * Created by deanguo on 1/4/17.
 */

public class ValidateCodePage extends BaseSignUpFragment {

    protected View resendButton;
    protected View callMeButton;
    protected View hintView;
    protected TextView countDownTextView;

    public ValidateCodePage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        resendButton = rootView.findViewById(R.id.resend_btn);
        callMeButton = rootView.findViewById(R.id.call_me_btn);
        hintView = rootView.findViewById(R.id.validate_hint);

        countDownTextView = (TextView) rootView.findViewById(R.id.count_down_text);
        callMeButton.setAlpha(0.2f);
        resendButton.setAlpha(0.2f);
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        callMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private CountDown countdown;

    protected void startCountDown() {
        final long SECONDS = 59;
        if (countdown != null) {
            countdown.cancel();
        }
        countdown = new CountDown(SECONDS);
        countdown.setListener(new CountDown.OnCountDownListener() {
            @Override
            public void onCountDown(long value) {
                countDownTextView.setText(value + "");
            }

            @Override
            public void onStartCountDown() {
                countDownTextView.setVisibility(View.VISIBLE);
                hintView.setVisibility(View.INVISIBLE);
                resendButton.setEnabled(false);
                callMeButton.setEnabled(false);
                resendButton.setAlpha(0.2f);
                callMeButton.setAlpha(0.2f);
            }

            @Override
            public void onStopCountDown() {
                countDownTextView.setVisibility(View.INVISIBLE);
                hintView.setVisibility(View.VISIBLE);
                resendButton.setEnabled(true);
                callMeButton.setEnabled(true);
                resendButton.setAlpha(1.0f);
                callMeButton.setAlpha(1.0f);
            }
        });

        countdown.start();
    }

    @Override
    public int getPageIndex() {
        return STEP_VALIDATE_CODE;
    }

    @Override
    public boolean validate() {
        if (inputView.getText().toString().trim().length() > 5) {
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
        return R.layout.validate_code_page;
    }

    @Override
    public void initView() {

        startCountDown();
    }

    @Override
    public void clearView() {
        if (countdown != null) {
            countdown.cancel();
        }
        countDownTextView.setText("60");
        inputView.setText("");
    }

    @Override
    public void onContinuePress() {
        actionListener.onConfirm(getPageIndex()+1);
    }
}
