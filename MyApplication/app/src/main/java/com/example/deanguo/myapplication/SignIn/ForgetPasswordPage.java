package com.example.deanguo.myapplication.SignIn;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deanguo.myapplication.R;
import com.example.deanguo.myapplication.databinding.ForgetPasswordPageBinding;

/**
 * Created by DeanGuo on 1/20/17.
 */

public class ForgetPasswordPage extends BaseAuthorizeFragment {
    private LoginActivity activity;
    private ForgetPasswordPageBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (LoginActivity) getActivity();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.forget_password_page, container, false);
        binding.setViewModel(new ForgetPasswordViewModel(activity));
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // phoneInputView
        setInputHint(binding.phoneInputView, R.string.forget_password_page_phone);
        setInputView(binding.phoneInputView, new onTextChangeCallback() {
            @Override
            public void onTextChange() {
                binding.getViewModel().setSendVerificationCodeButtonEnable(validateInputPhone());
            }
        });

        // verifyCodeInput
        setInputHint(binding.verifyCodeInput, R.string.forget_password_page_code);
        setInputView(binding.verifyCodeInput, new onTextChangeCallback() {
            @Override
            public void onTextChange() {
                binding.getViewModel().setContinueButtonEnable(validate());
            }
        });

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContinuePress();
            }
        });
    }

    @Override
    public void onContinuePress() {
        activity.navigator.nextFragment(ResetPasswordPage.class, null, R.id.fragment_container);
    }

    public boolean validateInputPhone() {
        return true;
    }

    @Override
    public boolean validate() {
        return true;
    }
}
