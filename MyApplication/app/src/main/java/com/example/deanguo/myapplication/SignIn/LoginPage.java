package com.example.deanguo.myapplication.SignIn;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deanguo.myapplication.R;
import com.example.deanguo.myapplication.databinding.LoginPageBinding;

/**
 * Created by deanguo on 1/17/17.
 */

public class LoginPage extends Fragment {
    private LoginActivity activity;
    private LoginViewModel viewModel;
    private LoginPageBinding binding;
    private boolean isShowPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (LoginActivity) getActivity();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_page, container, false);
        viewModel = new LoginViewModel(activity, binding);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshConfirmButton();
        // password edit
        updateIconClear(binding.passwordInputView);
        binding.passwordInputView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // clear text action for EditText
                    int xDown = (int) event.getX();
                    if (xDown >= (binding.passwordInputView.getWidth() - binding.passwordInputView.getCompoundPaddingRight()) && xDown < binding.passwordInputView.getWidth()) {
                        isShowPassword = !isShowPassword;
                        updateIconClear(binding.passwordInputView);
                    }
                }
                return false;
            }
        });
        binding.passwordInputView.addTextChangedListener(new SETextWatcher());

        binding.phoneInputView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onContinuePress();
                }
                return false;
            }
        });
        binding.phoneInputView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.setBackgroundResource(R.drawable.edittext_background_white_focus);
                } else {
                    view.setBackgroundResource(R.drawable.edittext_background_white_normal);
                }
            }
        });
        binding.phoneInputView.addTextChangedListener(new SETextWatcher());
        setHint(binding.phoneInputView, R.string.login_page_phone_hint);
        setHint(binding.passwordInputView, R.string.login_page_password_hint);

        binding.signUpBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    Animation animation= AnimationUtils.loadAnimation(getContext(), R.anim.btn_scale_down);
                    binding.signUpBtn.startAnimation(animation);
                }
//                else {
//                    Animation animation= AnimationUtils.loadAnimation(getContext(), R.anim.btn_scale_up);
//                    binding.signUpBtn.startAnimation(animation);
//                }
                return false;
            }
        });
    }

    public void setHint(EditText editText, int string) {
        String hint = getString(string);
        SpannableString ss = new SpannableString(hint);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(20,true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

    public class SETextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            refreshConfirmButton();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    public void updateIconClear(EditText editText) {
        Drawable[] drawables = editText.getCompoundDrawables();
        if (isShowPassword) {
//            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editText.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], getResources().getDrawable(R.drawable.ico_eyes_open), drawables[3]);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
//            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editText.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], getResources().getDrawable(R.drawable.ico_eyes_close), drawables[3]);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private void refreshConfirmButton() {
        viewModel.setLoginButtonEnable(validate());
    }

    public boolean validate() {
        String password = binding.passwordInputView.getText().toString();
        String phoneNumber = binding.phoneInputView.getText().toString().trim();
        String countryCode = binding.countryCode.getText().toString().trim();

        if (!TextUtils.isEmpty(password) && password.length() > 4) {
            return true;
        }

        return false;
    }

    private void onContinuePress() {
        if (!validate()) {
            return;
        }

        viewModel.onLogin(null);
    }

}
