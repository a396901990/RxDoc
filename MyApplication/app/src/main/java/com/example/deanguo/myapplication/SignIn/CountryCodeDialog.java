package com.example.deanguo.myapplication.SignIn;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deanguo.myapplication.R;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by deanguo on 1/17/17.
 */

public class CountryCodeDialog extends DialogFragment implements CountryCodeAdapter.CountrySelector {

    List<Country> masterCountries;

    CountryCodeAdapter.CountrySelector countrySelector;

    public CountryCodeDialog(CountryCodeAdapter.CountrySelector countrySelector) {
        this.countrySelector = countrySelector;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        masterCountries = Country.getLibraryMasterCountryList(Country.Language.CHINESE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.country_code_layout, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_country);
        EditText search = (EditText) root.findViewById(R.id.editText_search);
        Drawable[] drawables = search.getCompoundDrawables();
        search.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ico_search_countrycode), drawables[1], drawables[2], drawables[3]);

        TextView noResult = (TextView) root.findViewById(R.id.textView_noresult);

        CountryCodeAdapter codeAdapter = new CountryCodeAdapter(getActivity(), this, search, noResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(codeAdapter);

        View backBtn = root.findViewById(R.id.top_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        root.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(root.getWindowToken(), 0);
            }
        });

        return root;
    }

    public String getLanguage() {
        Locale config = getActivity().getApplication().getResources().getConfiguration().locale;
        config.getLanguage();
        return config.getCountry();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Light_NoTitleBar);
        return dialog;
    }

    @Override
    public void onSelectedCountry(Country country) {
        if (countrySelector != null) {
            countrySelector.onSelectedCountry(country);
        }
        dismiss();
    }
}
