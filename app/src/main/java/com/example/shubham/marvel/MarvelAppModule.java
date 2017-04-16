package com.example.shubham.marvel;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class MarvelAppModule {
    private final Context mContext;

    public MarvelAppModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
