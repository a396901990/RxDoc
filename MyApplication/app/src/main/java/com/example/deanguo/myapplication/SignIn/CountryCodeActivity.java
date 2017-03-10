package com.example.deanguo.myapplication.SignIn;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deanguo.myapplication.R;

import java.util.List;
import java.util.Map;

/**
 * Created by deanguo on 1/17/17.
 */

public class CountryCodeActivity extends AppCompatActivity implements CountryCodeAdapter.CountrySelector {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_code_layout);

        RecyclerView recyclerView_countryDialog = (RecyclerView) this.findViewById(R.id.recycler_country);

        final EditText search = (EditText) this.findViewById(R.id.editText_search);
        search.getBackground().mutate().setColorFilter(getResources().getColor(R.color.aquamarine), PorterDuff.Mode.SRC_ATOP);
        Drawable[] drawables = search.getCompoundDrawables();
        search.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ico_search_countrycode), drawables[1], drawables[2], drawables[3]);

        TextView textView_noResult = (TextView) this.findViewById(R.id.textView_noresult);
        final CountryCodeAdapter cca = new CountryCodeAdapter(this, this, search, textView_noResult);
        recyclerView_countryDialog.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_countryDialog.setAdapter(cca);

        getCountryInfo();
        View top_back = this.findViewById(R.id.top_back);
        top_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = 0;
            }
        });
    }

    public Map.Entry<String, String> getCountryInfo() {

        TelephonyManager tm = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);

        // step1: try to get sim country iso
        String countryCode = tm.getSimCountryIso();

        // setp2: try to get network country ios
        if (TextUtils.isEmpty(countryCode)) {
            countryCode = tm.getNetworkCountryIso();
        }

        Map.Entry<String, String> result = null;
        if (!TextUtils.isEmpty(countryCode)) {
        }
        return result;
    }

    @Override
    public void onSelectedCountry(Country country) {
        Country country1 = country;
    }
}
