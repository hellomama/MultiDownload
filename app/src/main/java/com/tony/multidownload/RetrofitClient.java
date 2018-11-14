package com.tony.multidownload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance;

    private Retrofit.Builder mRetrofitBuilder;

    private OkHttpClient okHttpClient;

    public RetrofitClient() {

        initDefaultOkHttpClient();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        mRetrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
    }

    private void initDefaultOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.connectTimeout(20, TimeUnit.SECONDS);

//        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory();
//        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        SSLUtils.setCertificates(builder);
//        Interceptor addQueryParameterInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request originalRequest = chain.request();
//                Request request;
//                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
//                        // Provide your custom parameter here
//                        .addQueryParameter("platform", "android")
//                        .addQueryParameter("version", Util.getVersionName())
//                        .addQueryParameter("model", android.os.Build.MODEL)
//                        .build();
//                request = originalRequest.newBuilder().url(modifiedUrl).build();
//                return chain.proceed(request);
//            }
//        };
//        builder.addInterceptor(addQueryParameterInterceptor);
        okHttpClient = builder.build();

    }


    public static RetrofitClient getInstance() {

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }

        }
        return instance;
    }


    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }

    public Retrofit getRetrofit(String url) {
        mRetrofitBuilder.baseUrl(url);
        return mRetrofitBuilder.client(okHttpClient).build();
    }
}
