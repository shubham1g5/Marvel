package com.example.shubham.marvel.comicsList;

import com.example.shubham.marvel.common.BasePresenter;
import com.example.shubham.marvel.common.BasePresenterView;
import com.example.shubham.marvel.common.IO;
import com.example.shubham.marvel.common.UI;
import com.example.shubham.marvel.data.ComicsRepository;
import com.example.shubham.marvel.model.Comic;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class ComicsListPresenter extends BasePresenter<ComicsListPresenter.View> {
    private static final int CAPACITY = 100;

    private final Scheduler mUiScheduler;
    private final Scheduler mIoScheduler;
    private final ComicsRepository mComicsRepository;

    @Inject
    ComicsListPresenter(@UI Scheduler uiScheduler, @IO Scheduler ioScheduler, ComicsRepository comicsRepository) {
        mUiScheduler = uiScheduler;
        mIoScheduler = ioScheduler;
        mComicsRepository = comicsRepository;
    }

    @Override
    protected void onRegister(View view) {

        addToUnsubscribe(view.onRefreshAction()
                .doOnNext(ignored -> view.showRefreshing(true))
                .doOnNext(ignored -> mComicsRepository.refreshComics(true))
                .switchMap(ignored -> mComicsRepository.getComics(CAPACITY).subscribeOn(mIoScheduler))
                .observeOn(mUiScheduler)
                .subscribe(comics -> {
                            view.showRefreshing(false);
                            view.showComics(comics);
                            view.showEmptyView(comics.size() == 0);
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            view.showRefreshing(false);
//                            view.showMessage(networkUp(view) ? R.string.network_error : R.string.error);
                        }));

        addToUnsubscribe(view.onComicClicked()
                .subscribe(comic -> view.openComicDetail(comic)));
    }

    interface View extends BasePresenterView {
        void showRefreshing(boolean isRefreshing);

        void showComics(List<Comic> comics);

        Observable<Comic> onComicClicked();

        Observable<Object> onRefreshAction();

        void openComicDetail(Comic comic);

        void showEmptyView(boolean visible);
    }
}
