package com.example.deanguo.myapplication;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.deanguo.myapplication.databinding.SignUpActivityBinding;

/**
 * Created by deanguo on 1/16/17.
 */

public class SignUpViewModel extends BaseObservable {

    public SignUpViewModel(SignUpActivityBinding binding) {
        this.binding = binding;
    }

    SignUpActivityBinding binding;
    @Bindable
    public boolean isContinueButtonEnable() {
        return isContinueButtonEnable;
    }

    private boolean isContinueButtonEnable = true;

    public void setContinueButtonEnable(boolean continueButtonEnable) {
        isContinueButtonEnable = continueButtonEnable;
        notifyPropertyChanged(BR.continueButtonEnable);
    }

    public void gotoNextPage(int page) {
        binding.viewPager.setCurrentItem(page);
    }
//
//    public int setProgress(int progress) {
//        int curPage = viewPager.getCurrentItem() + 1;
//        int totalPage = adapter.getCount();
//        int progress = 100 * curPage / totalPage;
//        return progress;
//    }


}
