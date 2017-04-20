package com.example.shubham.marvel.comicdetail;

import android.text.Spanned;

import com.example.shubham.marvel.common.BasePresenter;
import com.example.shubham.marvel.common.BasePresenterView;
import com.example.shubham.marvel.common.IO;
import com.example.shubham.marvel.common.UI;
import com.example.shubham.marvel.data.ComicsRepository;
import com.example.shubham.marvel.model.Comic;

import javax.inject.Inject;

import io.reactivex.Scheduler;

public class ComicDetailPresenter extends BasePresenter<ComicDetailPresenter.View> {

    private final Scheduler mUiScheduler;
    private final Scheduler mIoScheduler;
    private final ComicsRepository mComicsRepository;
    private Comic mComic;

    @Inject
    ComicDetailPresenter(@UI Scheduler uiScheduler, @IO Scheduler ioScheduler, ComicsRepository comicsRepository, Comic comic) {
        mUiScheduler = uiScheduler;
        mIoScheduler = ioScheduler;
        mComicsRepository = comicsRepository;
        mComic = comic;
    }

    @Override
    protected void onRegister(View view) {
        view.displayTitle(mComic.getTitle());
        view.displayBody(mComic.getDescription());
        view.displayImage(mComic.getImage());
    }

    interface View extends BasePresenterView {
        void displayTitle(String title);

        void displayBody(String description);

        void displayImage(String imageUrl);

    }
}
