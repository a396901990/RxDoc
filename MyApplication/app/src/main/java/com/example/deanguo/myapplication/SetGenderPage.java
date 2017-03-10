package com.example.deanguo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by deanguo on 1/10/17.
 */

public class SetGenderPage extends BaseSignUpFragment {

    public static final String F = "female";
    public static final String M = "male";

    private View genderMaleContainer;
    private View genderFemaleContainer;
    private View genderMalePick;
    private View genderFemalePick;

    public SetGenderPage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        genderMaleContainer = rootView.findViewById(R.id.gender_m_container);
        genderFemaleContainer = rootView.findViewById(R.id.gender_f_container);
        genderMalePick = rootView.findViewById(R.id.gender_m_pick);
        genderFemalePick = rootView.findViewById(R.id.gender_f_pick);
        genderMaleContainer.setOnTouchListener(new GenderTouchListener(M));
        genderFemaleContainer.setOnTouchListener(new GenderTouchListener(F));
    }

    public class GenderTouchListener implements View.OnTouchListener {
        String gender;

        public GenderTouchListener(String gender) {
            this.gender = gender;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (M.equals(gender)) {
                    genderMalePick.setVisibility(View.VISIBLE);
                    genderFemalePick.setVisibility(View.GONE);
                } else if (F.equals(gender)) {
                    genderFemalePick.setVisibility(View.VISIBLE);
                    genderMalePick.setVisibility(View.GONE);
                }
            }

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                onContinuePress();
            }
            return true;
        }
    }

    @Override
    public int getPageIndex() {
        return STEP_SET_GENDER;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public boolean isShowKeyBoard() {
        return false;
    }

    @Override
    public int getContentView() {
        return R.layout.set_gender_page;
    }

    @Override
    public void initView() {

    }

    @Override
    public void clearView() {

    }

    @Override
    public void onContinuePress() {
        if (genderMalePick.getVisibility() == View.VISIBLE) {
            actionListener.onConfirm(getPageIndex() + 1);

        } else if (genderFemalePick.getVisibility() == View.VISIBLE) {
            actionListener.onConfirm(getPageIndex() + 1);
        }
    }
}
