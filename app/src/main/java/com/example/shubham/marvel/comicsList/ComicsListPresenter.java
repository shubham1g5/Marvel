package com.example.shubham.marvel.comicsList;

import android.view.MenuItem;

import com.example.shubham.marvel.R;
import com.example.shubham.marvel.common.BasePresenter;
import com.example.shubham.marvel.common.BasePresenterView;
import com.example.shubham.marvel.common.EventBus;
import com.example.shubham.marvel.common.IO;
import com.example.shubham.marvel.common.UI;
import com.example.shubham.marvel.data.ComicsRepository;
import com.example.shubham.marvel.model.Comic;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class ComicsListPresenter extends BasePresenter<ComicsListPresenter.View> {
    private static final int CAPACITY = 100;

    private final Scheduler mUiScheduler;
    private final Scheduler mIoScheduler;
    private final ComicsRepository mComicsRepository;
    private final EventBus mEventBus;
    private List<Comic> mComics;

    @Inject
    ComicsListPresenter(@UI Scheduler uiScheduler, @IO Scheduler ioScheduler, ComicsRepository comicsRepository, EventBus eventBus) {
        mUiScheduler = uiScheduler;
        mIoScheduler = ioScheduler;
        mComicsRepository = comicsRepository;
        mEventBus = eventBus;
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
                            mComics = comics;
                            displayComics(view, mComics);
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            view.showRefreshing(false);
                            view.showTotalPages(0);
                            view.showMessage(R.string.error);
                        }));

        addToUnsubscribe(view.onComicClicked()
                .subscribe(comic -> view.openComicDetail(comic)));

        addToUnsubscribe(view.onFilterAction()
                .subscribe(menuItem -> view.showFilters()));

        addToUnsubscribe(mEventBus.asObservable()
                .filter(event ->  event instanceof BudgetChangedEvent)
                .subscribe(event -> filterComicsByBudget(view, ((BudgetChangedEvent) event).getBudget())));
    }

    private void displayComics(View view, List<Comic> comics) {
        if (comics != null) {
            view.showComics(comics);
            view.showTotalPages(getTotalPages(comics));
            view.showEmptyView(comics.size() == 0);
        }
    }

    private void filterComicsByBudget(View view, String budget) {
        if(budget!=null && !budget.isEmpty() && mComics!=null){
            int budgetVal = Integer.parseInt(budget);
            ArrayList<Comic> comicsInBudget = new ArrayList<>();
            for (Comic mComic : mComics) {
                if(mComic.getPrice() <= budgetVal){
                    comicsInBudget.add(mComic);
                }
            }
            displayComics(view,comicsInBudget);
        }
    }

    private int getTotalPages(List<Comic> comics) {
        int pages = 0;
        for (Comic comic : comics) {
            pages += comic.getPages();
        }
        return pages;
    }

    interface View extends BasePresenterView {
        void showRefreshing(boolean isRefreshing);

        void showComics(List<Comic> comics);

        Observable<Comic> onComicClicked();

        Observable<Object> onRefreshAction();

        Observable<MenuItem> onFilterAction();

        void openComicDetail(Comic comic);

        void showEmptyView(boolean visible);

        void showTotalPages(int pages);

        void showFilters();
    }
}
