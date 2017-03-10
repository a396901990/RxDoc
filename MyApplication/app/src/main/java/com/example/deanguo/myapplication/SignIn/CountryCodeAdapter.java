package com.example.deanguo.myapplication.SignIn;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.deanguo.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by DeanGuo on 1/18/17.
 */
class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.CountryCodeViewHolder> {
    private List<Country> filteredCountries = null, masterCountries = null, preferredCountries = null;
    private LayoutInflater inflater;
    private Context context;
    private CountrySelector countrySelector;
    private TextView textView_noResult;
    private EditText editText_search;
    private String countryPreference = "cn,us,in,jp,br,id,ru";

    public interface CountrySelector {
        void onSelectedCountry(Country country);
    }

    CountryCodeAdapter(Context context, CountrySelector countrySelector, final EditText editText_search, TextView textView_noResult) {
        this.context = context;
        this.countrySelector = countrySelector;
        this.inflater = LayoutInflater.from(context);
        this.textView_noResult = textView_noResult;
        this.editText_search = editText_search;

        Locale locale = context.getResources().getConfiguration().locale;
        this.masterCountries = Country.getCountryList(locale);
        setTextWatcher();
        setPreferredCountries();

        this.filteredCountries = getFilteredCountries("");
    }

    private void setPreferredCountries() {
        if (countryPreference == null || countryPreference.length() == 0) {
            preferredCountries = null;
        } else {
            List<Country> localCountryList = new ArrayList<>();
            for (String nameCode : countryPreference.split(",")) {
                Country country = Country.getCountryForNameCode(masterCountries, nameCode);
                if (country != null) {
                    if (!isAlreadyInList(country, localCountryList)) { //to avoid duplicate entry of country
                        localCountryList.add(country);
                    }
                }
            }

            if (localCountryList.size() == 0) {
                preferredCountries = null;
            } else {
                preferredCountries = localCountryList;
            }
        }
    }

    private boolean isAlreadyInList(Country country, List<Country> countryList) {
        if (country != null && countryList != null) {
            for (Country iterationCountry : countryList) {
                if (iterationCountry.getNameCode().equalsIgnoreCase(country.getNameCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * add textChangeListener, to apply new query each time editText get text changed.
     */
    private void setTextWatcher() {
        if (this.editText_search != null) {
            this.editText_search.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    applyQuery(s.toString());
                }
            });
        }
    }

    /**
     * Filter country list for given keyWord / query.
     * Lists all countries that contains @param query in country's name, name code or phone code.
     *
     * @param query : text to match against country name, name code or phone code
     */
    private void applyQuery(String query) {
        query = query.toLowerCase();

        //if query started from "+" ignore it
        if (query.length() > 0 && query.charAt(0) == '+') {
            query = query.substring(1);
        }

        filteredCountries = getFilteredCountries(query);

        if (filteredCountries.size() == 0) {
            textView_noResult.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    private List<Country> getFilteredCountries(String query) {
        List<Country> tempCountryList = new ArrayList<Country>();
        if (preferredCountries != null && preferredCountries.size() > 0) {
            for (Country country : preferredCountries) {
                if (country.isEligibleForQuery(query)) {
                    tempCountryList.add(country);
                }
            }

            if (tempCountryList.size() > 0) { //means at least one preferred country is added.
                Country divider = null;
                tempCountryList.add(divider);
            }
        }

        for (Country country : masterCountries) {
            if (country.isEligibleForQuery(query)) {
                tempCountryList.add(country);
            }
        }
        return tempCountryList;
    }

    @Override
    public CountryCodeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = inflater.inflate(R.layout.layout_recycler_country_tile, viewGroup, false);
        CountryCodeViewHolder viewHolder = new CountryCodeViewHolder(rootView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CountryCodeViewHolder countryCodeViewHolder, final int i) {
        countryCodeViewHolder.setCountry(filteredCountries.get(i));
        countryCodeViewHolder.getMainView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countrySelector != null) {
                    countrySelector.onSelectedCountry(filteredCountries.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredCountries.size();
    }

    class CountryCodeViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout_main;
        TextView textView_name, textView_code;
        View divider;

        public CountryCodeViewHolder(View itemView) {
            super(itemView);
            relativeLayout_main = (RelativeLayout) itemView;
            textView_name = (TextView) relativeLayout_main.findViewById(R.id.textView_countryName);
            textView_code = (TextView) relativeLayout_main.findViewById(R.id.textView_code);
            divider = relativeLayout_main.findViewById(R.id.preferenceDivider);
        }

        public void setCountry(Country country) {
            if (country != null) {
                divider.setVisibility(View.GONE);
                textView_name.setVisibility(View.VISIBLE);
                textView_code.setVisibility(View.VISIBLE);
                textView_name.setText(country.getName());
                textView_code.setText("+" + country.getPhoneCode());
            } else {
                divider.setVisibility(View.VISIBLE);
                textView_name.setVisibility(View.GONE);
                textView_code.setVisibility(View.GONE);
            }
        }

        public RelativeLayout getMainView() {
            return relativeLayout_main;
        }
    }
}

