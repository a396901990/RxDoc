package com.example.deanguo.myapplication.SignIn;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.example.deanguo.myapplication.BR;
import com.example.deanguo.myapplication.R;
import com.example.deanguo.myapplication.databinding.LoginPageBinding;
import com.example.deanguo.myapplication.ui.CountDown;

/**
 * Created by deanguo on 1/17/17.
 */

public class ForgetPasswordViewModel extends BaseObservable {

    public static final String SECOND = "60";
    private Country country;
    private LoginActivity activity;
    private CountDown countdown;
    private boolean isContinueButtonEnable = false;
    private boolean isSendVerificationCodeButtonEnable = false;
    private boolean isResendButtonEnable = false;

    private String resendBtnText = SECOND;

    public ForgetPasswordViewModel(LoginActivity activity) {
        this.activity = activity;
    }

    @Bindable
    public boolean isSendVerificationCodeButtonEnable() {
        return isSendVerificationCodeButtonEnable;
    }

    @Bindable
    public boolean isContinueButtonEnable() {
        return isContinueButtonEnable;
    }

    @Bindable
    public boolean isResendButtonEnable() {
        return isResendButtonEnable;
    }


    @Bindable
    public Country getCountry() {
        return country;
    }

    @Bindable
    public String getResendBtnText() {
        return resendBtnText;
    }

    public void setResendButtonEnable(boolean resendButtonEnable) {
        isResendButtonEnable = resendButtonEnable;
        notifyPropertyChanged(BR.resendButtonEnable);
    }

    public void setResendBtnText(String resendBtnText) {
        this.resendBtnText = resendBtnText;
        notifyPropertyChanged(BR.resendBtnText);
    }

    public void setCountry(Country country) {
        this.country = country;
        notifyPropertyChanged(BR.country);
    }

    public void setSendVerificationCodeButtonEnable(boolean sendVerificationCodeButtonEnable) {
        isSendVerificationCodeButtonEnable = sendVerificationCodeButtonEnable;
        notifyPropertyChanged(BR.sendVerificationCodeButtonEnable);
    }

    public void setContinueButtonEnable(boolean isContinueButtonEnable) {
        this.isContinueButtonEnable = isContinueButtonEnable;
        notifyPropertyChanged(BR.continueButtonEnable);
    }

    public void onResend(View view) {
        startCountDown();
    }

    public void onSendVerificationCode(View view) {
        startCountDown();
    }

    public void onBack(View view) {
        activity.navigator.prevFragment();
    }

    public void onCountrySelector(View view) {
        onCountrySelectorPressed();
    }

    // country code logic
    protected void onCountrySelectorPressed() {
        final CountryCodeDialog dialog = new CountryCodeDialog(new CountryCodeAdapter.CountrySelector() {
            @Override
            public void onSelectedCountry(Country country) {
                setCountry(country);
            }
        });
        dialog.show(activity.getSupportFragmentManager(), CountryCodeDialog.class.getName());
    }

    protected void startCountDown() {
        final long SECONDS = 59;
        if (countdown != null) {
            countdown.cancel();
        }
        countdown = new CountDown(SECONDS);
        countdown.setListener(new CountDown.OnCountDownListener() {
            @Override
            public void onCountDown(long value) {
                setResendBtnText(value + "");
            }

            @Override
            public void onStartCountDown() {
                setResendButtonEnable(false);
            }

            @Override
            public void onStopCountDown() {
                setResendBtnText(activity.getString(R.string.sign_up_validate_code_resend));
                setResendButtonEnable(true);
            }
        });

        countdown.start();
    }

}
