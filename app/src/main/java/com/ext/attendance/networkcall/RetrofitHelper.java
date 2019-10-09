package com.ext.attendance.networkcall;


import com.ext.attendance.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    MyService getMyService() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(MyService.class);
    }

    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request original = chain.request();

                // Request customization: add request headers
                final Request.Builder requestBuilder = original.newBuilder();
                // TODO: Add token header here

                final Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        //Enable log in debug mode
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.addInterceptor(logInterceptor);
        }

        return httpClient.build();
    }

    /**
     * Creates a pre configured Retrofit instance
     */
    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
    }


}
