package com.example.deanguo.myapplication.SignIn;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deanguo.myapplication.R;

/**
 * Created by deanguo on 1/17/17.
 */

public class ResetPasswordPage extends Fragment {
    private LoginActivity activity;
    private View rootView;
    private EditText inputView;
    private Button continueButton;
    private boolean isShowPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (LoginActivity) getActivity();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.reset_password_page, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // continue
        continueButton = (Button) rootView.findViewById(R.id.confirm_btn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContinuePress();
            }
        });

        // back
        View backButton = rootView.findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.navigator.prevFragment();
            }
        });

        // password edit
        inputView = (EditText) rootView.findViewById(R.id.password_input_view);
        updateIconClear(inputView);
        inputView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // clear text action for EditText
                    int xDown = (int) event.getX();
                    if (xDown >= (inputView.getWidth() - inputView.getCompoundPaddingRight()) && xDown < inputView.getWidth()) {
                        isShowPassword = !isShowPassword;
                        updateIconClear(inputView);
                    }
                }
                return false;
            }
        });
        inputView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    hideSoftKeyboard(getActivity(), v.getWindowToken());
                } else {
                    showSoftKeyboard(getActivity(), v);
                }
            }
        });
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
                refreshConfirmButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void refreshConfirmButton() {
        continueButton.setEnabled(validate());
    }

    public boolean validate() {
        String password = inputView.getText().toString();
        if (!TextUtils.isEmpty(password) && password.length() > 4) {
            return true;
        }
        return false;
    }

    private void onContinuePress() {
        if (!validate()) {
            return;
        }

        String password = inputView.getText().toString();
    }

    public void updateIconClear(EditText editText) {
        Drawable[] drawables = editText.getCompoundDrawables();
        if (isShowPassword) {
            editText.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], getResources().getDrawable(R.drawable.ico_eyes_open), drawables[3]);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], getResources().getDrawable(R.drawable.ico_eyes_close), drawables[3]);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
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
