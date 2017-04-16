package com.example.shubham.marvel.data;

import android.content.Context;
import android.content.res.Resources;

import com.example.shubham.marvel.R;
import com.example.shubham.marvel.data.local.ComicsDbHelper;
import com.example.shubham.marvel.data.remote.ComicMapper;
import com.example.shubham.marvel.data.remote.MarvelService;
import com.google.gson.Gson;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;

@Module
public class ComicsRepositoryModule {

    private static final String HEADER_API_KEY = "api-key";
    private static final String BASE_URL = "https://gateway.marvel.com:443/v1/public";


    @Singleton
    @Provides
    MarvelService provideMarvelService(Context context) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(createOkHttpClient(context.getResources()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MarvelService.class);
    }

    @Singleton
    @Provides
    ComicMapper provideComicMapper() {
        return new ComicMapper();
    }

    @Singleton
    @Provides
    BriteDatabase provideBriteDatabase(Context context, Scheduler ioScheduler) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        return sqlBrite.wrapDatabaseHelper(new ComicsDbHelper(context), ioScheduler);
    }

    private OkHttpClient createOkHttpClient(Resources resources) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(getAuthInterceptor(resources));
        return clientBuilder.build();
    }

    private Interceptor getAuthInterceptor(Resources resources) {
        return chain -> {
            Request original = chain.request();
            Headers.Builder hb = original.headers().newBuilder();
            hb.add(HEADER_API_KEY, resources.getString(R.string.marvel_api_key));
            return chain.proceed(original.newBuilder().headers(hb.build()).build());
        };
    }
}
