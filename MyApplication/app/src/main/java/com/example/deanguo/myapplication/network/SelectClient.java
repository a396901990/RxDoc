package com.example.deanguo.myapplication.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectClient {

    static private SelectClient instance;

    static synchronized public SelectClient getInstance() {
        if (null == instance) {
            instance = new SelectClient();
        }
        return instance;
    }

    private OkHttpClient mOkHttpClient;

    //ConfigProvider.getInstance().getConfig().getApiServerUrlPre()
    private final String BASE_URL = "http://dev-api.selectdc.net/";

    private Retrofit select;

    public SelectClient() {

        select = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getHttpClient())
                .build();
    }


    private synchronized OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(50, TimeUnit.SECONDS);
        builder.writeTimeout(50, TimeUnit.SECONDS);
        builder.addInterceptor(new SEHttpInterceptor());
        // SSL certificate pining
        String hostname = "web.selectdc.net";
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(hostname, "sha256/h6801m+z8v3zbgkRHpq6L29Esgfzhj89C1SyUCOQmqU=") // sha256/h6801m+z8v3zbgkRHpq6L29Esgfzhj89C1SyUCOQmqU=: CN=GeoTrust Global CA,O=GeoTrust Inc.,C=US
                .build();

        builder.certificatePinner(certificatePinner);
        return builder.build();
    }

    public SignUpService SignUpService() {
        return select.create(SignUpService.class);
    }

    public class SEHttpInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Headers.Builder headerBuilder = new Headers.Builder();
            headerBuilder.add("uid", "");
            headerBuilder.add("token", "");
            headerBuilder.add("Lang", "zh_CN");
            headerBuilder.add("User-Agent", "Select-android-11200, native, xhdpi, H30__T00_HUAWEI, +0800");
            Headers headers = headerBuilder.build();
            if (headers != null) {
                Request newRequest = chain.request().newBuilder().headers(headers).build();
                return chain.proceed(newRequest);
            }
            return chain.proceed(chain.request());
        }
    }
}
