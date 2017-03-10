package com.example.deanguo.myapplication;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by deanguo on 1/10/17.
 */

public class SetBirthdayPage extends BaseSignUpFragment {
    public static final String BIRTHDAY_PATTERN = "MMM dd, yyyy";
    private static final String GTM_BIRTHDAY_PATTERN = "yyyy-MM-dd";
    private View bottomContainer;
    private TextView birthdayInput;
    private DatePicker datePicker;
    private Date birthday;
    Calendar calendar;

    public SetBirthdayPage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        birthdayInput = (TextView) rootView.findViewById(R.id.birthday_text);
        bottomContainer = rootView.findViewById(R.id.bottom_view);
        bottomContainer.setVisibility(View.GONE);
        birthdayInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomContainer.setVisibility(View.VISIBLE);
            }
        });
        datePicker = (DatePicker) rootView.findViewById(R.id.date_picker);
        initPicker();
    }

    protected void initPicker() {

        Resources res = getActivity().getResources();

        int maximumAge = res.getInteger(R.integer.maximum_age);
        int minimumAge = res.getInteger(R.integer.minimum_age);

        calendar = Calendar.getInstance();
        if (null != birthday) {
            calendar.setTime(birthday);
        } else {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 25);
        }

        setBirthday(calendar.getTime());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                setBirthday(calendar.getTime());
            }
        });

        try {

            Date maxDate = new Date();
            maxDate.setYear(maxDate.getYear() - minimumAge);
            Date minDate = new Date(maxDate.getYear() - maximumAge, 0, 1);

            // api 10 doesn't support this.
            datePicker.setMaxDate(maxDate.getTime());
            datePicker.setMinDate(minDate.getTime());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        if (null != birthday && null != birthdayInput) {
            SimpleDateFormat sdf;
//            try {
                sdf = new SimpleDateFormat(BIRTHDAY_PATTERN);
//            } catch (Exception e){
//                sdf = new SimpleDateFormat(GTM_BIRTHDAY_PATTERN);
//            }
            String d = sdf.format(birthday);
            birthdayInput.setText(d);
        }
    }
    public static String convertGMTToLoacale(String gmt){
        String cc = gmt.substring(0, 19) + gmt.substring(33, 38);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy",new Locale("English"));
        try {
            Date date = sdf.parse(cc);
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM");
            String result = dateformat.format(date);
            return result;
        } catch (ParseException e) {
        }
        return "";
    }

    @Override
    public int getPageIndex() {
        return STEP_SET_BIRTHDAY;
    }

    @Override
    public boolean validate() {
        return !TextUtils.isEmpty(birthdayInput.getText().toString().trim());
    }

    @Override
    public boolean isShowKeyBoard() {
        return false;
    }

    @Override
    public int getContentView() {
        return R.layout.set_birthday_page;
    }

    @Override
    public void initView() {
        bottomContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomContainer.setVisibility(View.VISIBLE);
            }
        }, 600);


        String birthday = "1990-07-11";
        if (!TextUtils.isEmpty(birthday)) {
            SimpleDateFormat sdf = new SimpleDateFormat(GTM_BIRTHDAY_PATTERN);
            Date birthdayDate = null;
            try {
                birthdayDate = sdf.parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(birthdayDate);
            datePicker.updateDate(calendar.YEAR, calendar.MONTH, calendar.DAY_OF_MONTH);
        }
    }

    @Override
    public void clearView() {

    }

    @Override
    public void onContinuePress() {
        bottomContainer.setVisibility(View.GONE);
    }
}
