package com.example.deanguo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by deanguo on 1/4/17.
 */

public abstract class BaseSignUpFragment extends Fragment {

    View rootView;

    Button confirmButton;
    EditText inputView;
    View backButton;
    private boolean isPrepared;
    SignUpViewModel model;

    public SignUpActionListener actionListener;

    public static int STEP_SET_PHONE = 0;
    public static int STEP_VALIDATE_CODE = 1;
    public static int STEP_FIND_FRIEND = 2;
    public static int STEP_SET_USERNAME = 3;
    public static int STEP_SET_PASSWORD = 4;
    public static int STEP_SET_PROFILE = 5;
    public static int STEP_SET_BIRTHDAY = 6;
    public static int STEP_SET_GENDER = 7;
    public static int STEP_SET_EMAIL = 8;
    public static int STEP_RECOMMEND = 9;

    public BaseSignUpFragment(SignUpViewModel model) {
        this.model = model;
    }

    public abstract int getPageIndex();

    public abstract boolean validate();

    public abstract boolean isShowKeyBoard();

    public abstract int getContentView();

    public abstract void initView();

    public abstract void clearView();

    public abstract void onContinuePress();

    public interface SignUpActionListener {
        void onBack(int preStep);
        void onConfirm(int nextStep);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isPrepared && isVisibleToUser) {
            setKeyboardStatus();
            refreshConfirmButton();
            initView();
        }

    }

    public void setKeyboardStatus() {
        if (inputView != null) {
            inputView.requestFocus();
            showSoftKeyboard(getActivity(), inputView);
        } else {
            hideSoftKeyboard(getActivity(), getView().getWindowToken());
        }
    }

    public void refreshConfirmButton() {
        model.setContinueButtonEnable(validate());
        if (confirmButton == null) {
            return;
        }

        if (validate()) {
            confirmButton.setEnabled(true);
        } else {
            confirmButton.setEnabled(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentView(), null);
        return rootView;
    }

    private void setUpView() {
        if (rootView == null) {
            return;
        }

        // input view
        inputView = (EditText) rootView.findViewById(R.id.input_view);
        if (inputView != null) {
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

            inputView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        actionListener.onConfirm(getPageIndex() + 1);
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
        }

        // confirm button
        confirmButton = (Button) rootView.findViewById(R.id.confirm_btn);
        if (confirmButton != null) {
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onContinuePress();
//                    if (actionListener != null) {
//                        actionListener.onConfirm(getPageIndex() + 1);
//                    }
                }
            });
        }

        // back button
        backButton = rootView.findViewById(R.id.back_btn);
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearView();
                    if (actionListener != null) {
                        actionListener.onBack(getPageIndex() - 1);
                    }
                }
            });
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpView();
        isPrepared = true;
    }

    @Override
    public void onResume() {
        super.onResume();
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
