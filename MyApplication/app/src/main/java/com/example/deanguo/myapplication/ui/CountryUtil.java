package com.example.deanguo.myapplication.ui;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.deanguo.myapplication.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by pavel on 12/4/14.
 */
public class CountryUtil {

    public static List<Map.Entry<String, String>> getCountryAndPhoneCodePairs(Resources resources) {
        XmlResourceParser parser = resources.getXml(R.xml.countries);
        List<Map.Entry<String, String>> result = new ArrayList<Map.Entry<String, String>>();
        try {
            int type = parser.getEventType();
            Map.Entry<String, String> pair = null;
            while (XmlPullParser.END_DOCUMENT != type) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        String code = parser.getAttributeValue(null, "phoneCode");
                        String name = parser.getAttributeValue(null, "name");
                        if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(name)) {
                            pair = new AbstractMap.SimpleImmutableEntry<String, String>(name, code);
                        }
                        break;
                    case XmlPullParser.END_TAG:

                        if (null != pair) {
                            result.add(pair);
                            pair = null;
                        }
                        break;
                }

                type = parser.next();
            }

            parser.close();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static View renderCountryCodeView(View view, Map.Entry<String, String> item) {

        View divider = view.findViewById(R.id.divider);
        TextView nameView = (TextView) view.findViewById(R.id.text_view);
        TextView codeView = (TextView) view.findViewById(R.id.code_view);

        if (Pattern.matches("-*", item.getKey())) {
            divider.setVisibility(View.VISIBLE);
            nameView.setVisibility(View.GONE);
            codeView.setVisibility(View.GONE);
        } else {
            divider.setVisibility(View.GONE);
            nameView.setVisibility(View.VISIBLE);
            codeView.setVisibility(View.VISIBLE);
            nameView.setText(item.getKey());
            codeView.setText("(+" + item.getValue() + ")");
        }

        return view;
    }
}
