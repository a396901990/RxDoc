package com.example.deanguo.myapplication.SignIn;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.example.deanguo.myapplication.BR;
import com.example.deanguo.myapplication.R;
import com.example.deanguo.myapplication.databinding.LoginPageBinding;

/**
 * Created by deanguo on 1/17/17.
 */

public class LoginViewModel extends BaseObservable {

    private boolean isLoginButtonEnable = true;
    private LoginPageBinding binding;

    public LoginViewModel(LoginActivity activity, LoginPageBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    private LoginActivity activity;

    @Bindable
    public boolean isLoginButtonEnable() {
        return isLoginButtonEnable;
    }

    public void setLoginButtonEnable(boolean isLoginButtonEnable) {
        this.isLoginButtonEnable = isLoginButtonEnable;
        notifyPropertyChanged(BR.loginButtonEnable);
    }

    public void onLogin(View view) {

    }

    public void onForgetPassword(View view) {
        activity.navigator.nextFragment(ForgetPasswordPage.class, null, R.id.fragment_container);
    }

    public void onSignUp(View view) {

    }

    public void onCountrySelector(View view) {
        onCountrySelectorPressed();
    }

    // country code logic
    protected void onCountrySelectorPressed() {
        final CountryCodeDialog dialog = new CountryCodeDialog(new CountryCodeAdapter.CountrySelector() {
            @Override
            public void onSelectedCountry(Country country) {
                if (null != binding.countrySelector) {
                    binding.country.setText(country.getName());
                }

                if (null != binding.countryCode) {
                    binding.countryCode.setText(country.getPhoneCode());
                }
            }
        });
        dialog.show(activity.getSupportFragmentManager(), CountryCodeDialog.class.getName());
    }
}
