package com.example.shubham.marvel.data;

import com.example.shubham.marvel.model.Comic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ComicsRepositoryTest {

    private static final int CAPACITY = 3;
    private ComicsRepository mComicsRepository;

    @Mock
    private ComicsDataSource mComicsRemoteDataSource;

    @Mock
    private ComicsDataSource mComicsLocalDataSource;

    private static List<Comic> COMICS = new ArrayList<>();

    static {
        COMICS.add(Comic.builder()
                .id(1)
                .title("comic1")
                .build());

        COMICS.add(Comic.builder()
                .id(2)
                .title("comic2")
                .build());

        COMICS.add(Comic.builder()
                .id(3)
                .title("comic3")
                .build());
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mComicsRepository = new ComicsRepository(mComicsLocalDataSource, mComicsRemoteDataSource);
    }

    @Test
    public void getComics_repositoryCachesAfterFirstSubscription_whenComicsAvailableInLocalStorage() {
        // Given that the local data source has data available
        setComicsAvailable(mComicsLocalDataSource, COMICS);
        // And the remote data source does not have any data available
        setComicsNotAvailable(mComicsRemoteDataSource);

        checkRepositoryCachesAfterFirstSubscription();
    }

    @Test
    public void getComics_repositoryCachesAfterFirstSubscription_whenComicsAvailableInRemoteStorage() {
        // Given that the remote data source has data available
        setComicsAvailable(mComicsRemoteDataSource, COMICS);
        // And the local data source does not have any data available
        setComicsNotAvailable(mComicsLocalDataSource);

        checkRepositoryCachesAfterFirstSubscription();

    }

    private void checkRepositoryCachesAfterFirstSubscription() {
        // When two subscriptions are set
        TestObserver<List<Comic>> testObserver1 = new TestObserver<>();
        mComicsRepository.getComics(CAPACITY).subscribe(testObserver1);

        TestObserver<List<Comic>> testObserver2 = new TestObserver<>();
        mComicsRepository.getComics(CAPACITY).subscribe(testObserver2);

        // Then comics were only requested once from remote and local sources
        verify(mComicsRemoteDataSource).getComics(CAPACITY);
        verify(mComicsLocalDataSource).getComics(CAPACITY);
        assertFalse(mComicsRepository.mCacheIsDirty);
        testObserver1.assertValue(COMICS);
        testObserver2.assertValue(COMICS);
    }

    @Test
    public void getComics_requestsAllComicsFromLocalDataSource() {
        // Given that the local data source has data available
        setComicsAvailable(mComicsLocalDataSource, COMICS);
        // And the remote data source does not have any data available
        setComicsNotAvailable(mComicsRemoteDataSource);

        // When comics are requested from the comics repository
        TestObserver<List<Comic>> comicsTestObserver = new TestObserver<>();
        mComicsRepository.getComics(CAPACITY).subscribe(comicsTestObserver);

        // Then comics are loaded from the local data source
        verify(mComicsLocalDataSource).getComics(CAPACITY);
        comicsTestObserver.assertValue(COMICS);
    }

    private void setComicsNotAvailable(ComicsDataSource dataSource) {
        when(dataSource.getComics(CAPACITY)).thenReturn(Observable.just(Collections.emptyList()));
    }

    private void setComicsAvailable(ComicsDataSource dataSource, List<Comic> comics) {
        // don't allow the data sources to complete.
        when(dataSource.getComics(CAPACITY)).thenReturn(Observable.just(comics));
    }

}