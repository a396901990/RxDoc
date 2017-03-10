package com.example.deanguo.myapplication;

import android.databinding.ObservableField;

public class RecyclerViewHeaderViewModel {

    public final ObservableField<String> headerText = new ObservableField<>();

    public RecyclerViewHeaderViewModel(String headerText) {
        this.headerText.set(headerText);
    }

}