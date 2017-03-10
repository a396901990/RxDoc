package com.example.deanguo.myapplication;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

import com.example.deanguo.myapplication.databinding.SignUpActivityBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by deanguo on 1/4/17.
 */

public class SignUpActivity extends AppCompatActivity implements BaseSignUpFragment.SignUpActionListener {

    SignUpAdapter adapter;
    SignUpActivityBinding binding;
    SignUpViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sign_up_activity);
        model = new SignUpViewModel(binding);
        binding.setViewModel(model);

        String birthdayString = "12/05/1984";
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
        Date birthdayDate = null;
        try {
            birthdayDate = dateFormat1.parse(birthdayString);
        } catch (ParseException e) {
            String a = e.toString();
        }
        String bb = dateFormat2.format(birthdayDate);

        SparseArray<BaseSignUpFragment> frags = new SparseArray<>();
        frags.put(BaseSignUpFragment.STEP_SET_PHONE, new SetPhonePage(model));
        frags.put(BaseSignUpFragment.STEP_VALIDATE_CODE, new ValidateCodePage(model));
        frags.put(BaseSignUpFragment.STEP_FIND_FRIEND, new FindFriendPage(model));
        frags.put(BaseSignUpFragment.STEP_SET_USERNAME, new SetUserNamePage(model));
        frags.put(BaseSignUpFragment.STEP_SET_PASSWORD, new SetPasswordPage(model));
        frags.put(BaseSignUpFragment.STEP_SET_PROFILE, new SetProfilePage(model));
        frags.put(BaseSignUpFragment.STEP_SET_BIRTHDAY, new SetBirthdayPage(model));
        frags.put(BaseSignUpFragment.STEP_SET_GENDER, new SetGenderPage(model));
        frags.put(BaseSignUpFragment.STEP_SET_EMAIL, new SetEmailPage(model));
        frags.put(BaseSignUpFragment.STEP_RECOMMEND, new RecommendPage(model));

        adapter = new SignUpAdapter(getSupportFragmentManager(), frags);
        binding.viewPager.setAdapter(adapter);
        binding.confirmBtn.showButton();
        binding.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.confirmBtn.showHint("test");
//                int curPageIndex = binding.viewPager.getCurrentItem();
//                BaseSignUpFragment curPage = adapter.getItem(curPageIndex);
//                if (curPage != null) {
//                    curPage.onContinuePress();
//                }
            }
        });
    }

    @Override
    public void onBack(int preStep) {
        binding.viewPager.setCurrentItem(preStep);
    }

    @Override
    public void onConfirm(int nextStep) {
        binding.viewPager.setCurrentItem(nextStep);
    }

    public class SignUpAdapter extends FragmentPagerAdapter {

        SparseArray<BaseSignUpFragment> frags;

        public SignUpAdapter(FragmentManager fm, SparseArray<BaseSignUpFragment> frags) {
            super(fm);
            this.frags = frags;
        }

        @Override
        public BaseSignUpFragment getItem(int position) {
            return frags.get(position);
        }

        @Override
        public int getCount() {
            return frags.size();
        }
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
