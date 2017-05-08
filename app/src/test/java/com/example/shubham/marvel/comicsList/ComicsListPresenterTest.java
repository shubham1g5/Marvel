package com.example.shubham.marvel.comicsList;

import android.view.MenuItem;

import com.example.shubham.marvel.common.EventBus;
import com.example.shubham.marvel.data.ComicsRepository;
import com.example.shubham.marvel.model.Comic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ComicsListPresenterTest {

    private static List<Comic> COMICS = new ArrayList<>();


    static {
        COMICS.add(Comic.builder()
                .id(1)
                .title("comic1")
                .price(1000)
                .pages(5)
                .build());

        COMICS.add(Comic.builder()
                .id(2)
                .title("comic2")
                .price(100)
                .pages(5)
                .build());

        COMICS.add(Comic.builder()
                .id(3)
                .title("comic3")
                .price(10)
                .pages(10)
                .build());
    }

    @Mock
    private ComicsRepository mComicsRepository;

    @Mock
    private EventBus mEventBus;

    @Mock
    private MenuItem menuItem;

    @Mock
    private ComicsListPresenter.View mView;

    private ComicsListPresenter comicsListPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        comicsListPresenter = new ComicsListPresenter(Schedulers.trampoline(), Schedulers.trampoline(), mComicsRepository, mEventBus);

        //Given
        mockObservablesRequiredToRegister();

        // When
        comicsListPresenter.onRegister(mView);
    }

    private void mockObservablesRequiredToRegister() {
        when(mView.onRefreshAction()).thenReturn(Observable.just(1));
        when(mComicsRepository.getComics(anyInt())).thenReturn(Observable.just(COMICS));
        when(mView.onComicClicked()).thenReturn(Observable.just(COMICS.get(0)));
        when(mView.onFilterAction()).thenReturn(Observable.just(menuItem));
        when(mEventBus.asObservable()).thenReturn(Observable.just(new BudgetChangedEvent("500")));
    }

    @Test
    public void loadComicsIntoView() {
        //  invoked twice, one by default and other one due to refresh
        verify(mView, times(2)).showComics(COMICS);
        verify(mView, times(2)).showRefreshing(false);
        verify(mView, times(3)).showEmptyView(false);
        verify(mView, times(2)).showTotalPages(20);
    }

    @Test
    public void budgetChangeEvent_FiltersListAndLoadIntoView() {
        verify(mView).showTotalPages(15);
    }

    @Test
    public void clickOnFilter_ShowsFilterslUi() {
        verify(mView).showFilters();
    }

    @Test
    public void clickOnComic_ShowsDetailUi() {
        verify(mView).openComicDetail(COMICS.get(0));
    }
}