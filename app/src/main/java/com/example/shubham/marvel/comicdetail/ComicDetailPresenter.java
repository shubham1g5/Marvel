package com.example.shubham.marvel.comicdetail;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import com.example.shubham.marvel.common.BasePresenter;
import com.example.shubham.marvel.common.BasePresenterView;
import com.example.shubham.marvel.common.IO;
import com.example.shubham.marvel.common.UI;
import com.example.shubham.marvel.data.ComicsRepository;
import com.example.shubham.marvel.model.Author;
import com.example.shubham.marvel.model.Comic;

import java.util.List;

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
        addToUnsubscribe(mComicsRepository.getAuthors(mComic.getId()).subscribeOn(mIoScheduler)
                .observeOn(mUiScheduler)
                .subscribe(authors -> view.showAuthors(authors),
                        throwable -> throwable.printStackTrace()));

        displayComic(view);
    }

    private void displayComic(View view) {
        view.displayTitle(mComic.getTitle());

        if (mComic.getDescription() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.displayDescription(Html.fromHtml(mComic.getDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                view.displayDescription(Html.fromHtml(mComic.getDescription()));
            }
        }

        view.displayImage(mComic.getImage());

        view.displayPrice(mComic.getPrice());

        view.displayPageCount(mComic.getPages());
    }

    interface View extends BasePresenterView {
        void displayTitle(String title);

        void displayDescription(Spanned description);

        void displayImage(String imageUrl);

        void showAuthors(List<Author> authors);

        void displayPrice(double price);

        void displayPageCount(int pages);
    }
}
