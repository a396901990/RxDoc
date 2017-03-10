package com.example.deanguo.myapplication.SignIn;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhangzhennan on 14/10/4.
 */
public class NavigatorUtil {

    Context context;
    AppCompatActivity activity;

    public NavigatorUtil(AppCompatActivity activity) {
        this.activity = activity;
        this.context = activity;
    }

    public int getCount() {
        return activity.getSupportFragmentManager().getBackStackEntryCount();
    }

    public void setRootFragment(Class<? extends Fragment> fragmentClass, Bundle arguments, int parentId) {

        FragmentManager fm = activity.getSupportFragmentManager();
        if (0 < fm.getBackStackEntryCount()) {
            fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        Fragment fragment = Fragment.instantiate(context, fragmentClass.getName(), arguments);

        fm.beginTransaction()
                .replace(parentId, fragment)
                .commit();
    }

    public void nextFragment(Class<? extends Fragment> fragmentClass, Bundle arguments, int parentId) {
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = Fragment.instantiate(context, fragmentClass.getName(), arguments);

        fm.beginTransaction()
                .replace(parentId, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void prevFragment() {
        FragmentManager fm = activity.getSupportFragmentManager();
        if (0 < fm.getBackStackEntryCount()) {
            fm.popBackStack();
        }
    }
}
