package com.example.deanguo.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.deanguo.myapplication.ui.CountryUtil;
import com.example.deanguo.myapplication.ui.SimpleListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by deanguo on 1/4/17.
 */

public class SetPhonePage extends BaseSignUpFragment {

    private TextView termsButton;

    private TextView country, countryCode;

    private View countrySelector;

    protected SimpleListAdapter listAdapter;

    private Map.Entry<String, String> selection;

    public SetPhonePage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        country = (TextView) rootView.findViewById(R.id.country);
        countryCode = (TextView) rootView.findViewById(R.id.country_code);
        countrySelector = rootView.findViewById(R.id.country_selector);
        termsButton = (TextView) rootView.findViewById(R.id.terms_button);
        termsButton.setText(Html.fromHtml(getString(R.string.p01_terms_and_service)));
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTermsButtonPressed();
            }
        });

        inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        inputView.setError("error");

        onSetupListAdapter();
        countrySelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCountrySelectorPressed();

//                HashMap<String, String> properties = new HashMap<>();
//                properties.put("country_code_auto_fill", countryCodeAutoFill ? "1" : "0");
//                AmplitudeUtil.getInstance().logEvent("sign_up.phone_input.country_code.name", properties, true);
            }
        });
    }

    protected void onCountrySelectorPressed() {
        countrySelector.setEnabled(false);
        if (0 == listAdapter.getCount()) {
            loadCountryCodeList();
        } else {

            Map.Entry<String, String> selection = getSelection();
            String value = null == selection ? null : selection.getValue();

            new AlertDialog.Builder(getActivity())
                    .setAdapter(listAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Map.Entry<String, String> pair = (Map.Entry<String, String>) listAdapter.getItem(which);
                            onCountryCodePopupSelected(pair);
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            countrySelector.setEnabled(true);
                        }
                    })
                    .show();
        }
    }

    protected void onCountryCodePopupSelected(Map.Entry<String, String> selection) {
        if (null != selection && !Pattern.matches("-*", selection.getKey())) {
            setSelection(selection);
        }
        countrySelector.setEnabled(true);
    }

    public Map.Entry<String, String> getSelection() {
        return selection;
    }

    public void setSelection(Map.Entry<String, String> selection) {
        this.selection = selection;

        // update selection
        onCountryCodeUpdate();
    }

    protected void onCountryCodeUpdate() {
        Map.Entry<String, String> selection = getSelection();
        String country = null == selection ? "" : selection.getKey();
        String code = null == selection ? "" : "+" + selection.getValue();

        if (null != countrySelector) {
            this.country.setText(country);
        }

        if (null != countryCode) {
            this.countryCode.setText(code);
        }
    }

    protected void loadCountryCodeList() {

        new AsyncTask<Void, Void, List<Map.Entry<String, String>>>() {
            @Override
            protected List<Map.Entry<String, String>> doInBackground(Void... params) {
                return CountryUtil.getCountryAndPhoneCodePairs(getResources());
            }

            @Override
            protected void onPostExecute(List<Map.Entry<String, String>> nameValuePairs) {
                onCountryCodeLoadComplete(nameValuePairs);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    protected void onCountryCodeLoadComplete(List<Map.Entry<String, String>> list) {
        listAdapter.removeAllItems(false);
        listAdapter.addItemAll(list);
        onCountrySelectorPressed();
    }

    protected void onSetupListAdapter() {

        listAdapter = new SimpleListAdapter() {
            @Override
            public boolean isEnabled(int position) {
                return super.isEnabled(position);
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }
        };

        listAdapter.registerItemRenderDelegate(HashMap.SimpleImmutableEntry.class, new SimpleListAdapter.ItemRenderDelegate() {
            @Override
            public View onRenderItem(int position, Object item, View view, ViewGroup parent) {
                if (null == view) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.listitem_country, parent, false);
                }
                return CountryUtil.renderCountryCodeView(view, (Map.Entry<String, String>) item);
            }
        });
    }

    protected void onTermsButtonPressed() {
        termsButton.setMovementMethod(LinkMovementMethod.getInstance());

//        AmplitudeUtil.getInstance().logEvent("sign_up.phone_input.tos", null, true);
    }

    @Override
    public int getPageIndex() {
        return STEP_SET_PHONE;
    }

    @Override
    public boolean validate() {
        String phoneNumber = inputView.getText().toString().trim();
        String countryCode = this.countryCode.getText().toString().trim();

        String errorMessage = null;
        if (TextUtils.isEmpty(countryCode)) {

            return false;
        }

        if (!TextUtils.isEmpty(phoneNumber)) {
            String fullNumber = countryCode + phoneNumber;
        } else {
        }

        if (!TextUtils.isEmpty(errorMessage)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isShowKeyBoard() {
        return true;
    }

    @Override
    public int getContentView() {
        return R.layout.set_phone_page;
    }

    @Override
    public void initView() {

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout(){
                        int heightDiff = rootView.getRootView().getHeight()- rootView.getHeight();
                        Log.e("heightDiff", heightDiff+"");
                        // r.left, r.top, r.right, r.bottom
                    }
                });
    }

    @Override
    public void clearView() {

    }

    @Override
    public void onContinuePress() {
        String code = countryCode.getText().toString();
        String phone = inputView.getText().toString();
        model.gotoNextPage(getPageIndex()+1);
    }
}
