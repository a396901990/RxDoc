package com.example.deanguo.myapplication.SignIn;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by DeanGuo on 1/20/17.
 */

public abstract class BaseAuthorizeFragment extends Fragment {
    public abstract void onContinuePress();

    public abstract boolean validate();

    public interface onTextChangeCallback {
        void onTextChange();
    }

    public void setInputHint(EditText editText, int string) {
        String hint = getString(string);
        SpannableString ss = new SpannableString(hint);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(20, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

    public void setInputView(final EditText inputView, final onTextChangeCallback textChangeCallback) {
        inputView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onContinuePress();
                }
                return false;
            }
        });
        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (textChangeCallback != null) {
                    textChangeCallback.onTextChange();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(getActivity(), v.getWindowToken());
                } else {
                    showSoftKeyboard(getActivity(), v);
                }
            }
        });

    }

    public static void showSoftKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideSoftKeyboard(Activity activity, IBinder token) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(token, 0);
    }
}

