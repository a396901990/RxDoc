package com.example.deanguo.myapplication.SignIn;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.deanguo.myapplication.BR;
import com.example.deanguo.myapplication.databinding.SignUpActivityBinding;

/**
 * Created by deanguo on 1/17/17.
 */

public class SignInViewModel extends BaseObservable {

    @Bindable
    public boolean isContinueButtonEnable() {
        return isContinueButtonEnable;
    }

    private boolean isContinueButtonEnable = true;

    public void setContinueButtonEnable(boolean continueButtonEnable) {
        isContinueButtonEnable = continueButtonEnable;
        notifyPropertyChanged(BR.continueButtonEnable);
    }
}
