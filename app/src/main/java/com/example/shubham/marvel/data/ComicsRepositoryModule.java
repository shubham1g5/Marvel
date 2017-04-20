package com.example.shubham.marvel.data;

import android.content.Context;
import android.content.res.Resources;

import com.example.shubham.marvel.R;
import com.example.shubham.marvel.common.HashUtils;
import com.example.shubham.marvel.data.local.ComicsDbHelper;
import com.example.shubham.marvel.data.local.LocalComicsDataSource;
import com.example.shubham.marvel.data.remote.ComicMapper;
import com.example.shubham.marvel.data.remote.MarvelService;
import com.example.shubham.marvel.data.remote.RemoteComicsDataSource;
import com.google.gson.Gson;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;

@Module
public class ComicsRepositoryModule {

    private static final String HEADER_API_KEY = "apikey";
    private static final String HEADER_TS = "ts";
    private static final String HEADER_HASH = "hash";
    private static final String BASE_URL = "https://gateway.marvel.com:443/v1/public/";


    @Singleton
    @Provides
    @Local
    ComicsDataSource provideLocalComicsDataSource(BriteDatabase briteDatabase, ComicMapper comicMapper) {
        return new LocalComicsDataSource(briteDatabase, comicMapper);
    }

    @Singleton
    @Provides
    @Remote
    ComicsDataSource provideRemoteComicsDataSource(MarvelService marvelService, ComicMapper comicMapper) {
        return new RemoteComicsDataSource(marvelService, comicMapper);
    }


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
            Request request = chain.request();

            try {
                String timeStamp = "" + new Date().getTime();
                String hashString = timeStamp + resources.getString(R.string.marvel_private_api_key)
                        + resources.getString(R.string.marvel_api_key);

                HttpUrl url = request.url().newBuilder()
                        .addQueryParameter(HEADER_TS, timeStamp)
                        .addQueryParameter(HEADER_API_KEY, resources.getString(R.string.marvel_api_key))
                        .addQueryParameter(HEADER_HASH, HashUtils.MD5(hashString))
                        .build();
                request = request.newBuilder().url(url).build();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return chain.proceed(request);
        };
    }


}
