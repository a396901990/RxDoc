package com.example.deanguo.myapplication.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.deanguo.myapplication.R;


/**
 * Created by DeanGuo on 12/1/16.
 */

public class AuthorizeButton extends FrameLayout {
    private TextView hint;
    private TextView text;
    private ProgressBar progress;
    private View view;
    private View buttonHolder;

    public AuthorizeButton(Context context) {
        super(context);
    }

    public AuthorizeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.authorize_button, this);
        text = (TextView) view.findViewById(R.id.button_text);
        hint = (TextView) view.findViewById(R.id.button_hint);
        progress = (ProgressBar) view.findViewById(R.id.button_progress);
        buttonHolder = view.findViewById(R.id.button_holder);
    }

    public void showHint(final String hintText) {
        hint.post(new Runnable() {
            @Override
            public void run() {
                reset();
                hint.setText(hintText);
                hint.setVisibility(VISIBLE);
            }
        });
    }

    public void showButton() {
        reset();
        text.setVisibility(VISIBLE);
        buttonHolder.setVisibility(VISIBLE);
    }

    public void showProgress() {
        reset();
        buttonHolder.setVisibility(VISIBLE);
        progress.setVisibility(VISIBLE);
    }

    public void reset() {
        view.setVisibility(VISIBLE);
        buttonHolder.setVisibility(INVISIBLE);
        text.setVisibility(INVISIBLE);
        hint.setVisibility(INVISIBLE);
        progress.setVisibility(INVISIBLE);
    }

    public void setInvisible() {
        view.setVisibility(INVISIBLE);
    }
}
