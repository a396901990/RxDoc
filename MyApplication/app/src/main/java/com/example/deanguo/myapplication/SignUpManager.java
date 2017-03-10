package com.example.deanguo.myapplication;

import android.util.SparseArray;

/**
 * Created by deanguo on 1/4/17.
 */

public class SignUpManager {
    public static int STEP_SET_PHONE = 0;
    public static int SIGN_VALIDATE_CODE = 1;
    public static int SIGN_FIND_FRIEND = 2;
    public static int SIGN_SET_USERNAME = 3;
    public static int SIGN_SET_PASSWORD = 4;
    public static int SIGN_SET_PROFILE = 5;
    public static int SIGN_SET_BIRTHDAY = 6;
    public static int SIGN_SET_GENDER = 7;
    public static int SIGN_SET_EMAIL = 8;

    public static SparseArray<Class> stepFragments = new SparseArray<>();
    static {
        stepFragments.put(STEP_SET_PHONE, SetPhonePage.class);
    }
}
