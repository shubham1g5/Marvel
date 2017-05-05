package com.example.shubham.marvel.comicsList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shubham.marvel.MarvelApp;
import com.example.shubham.marvel.R;
import com.example.shubham.marvel.comicdetail.ComicDetailActivity;
import com.example.shubham.marvel.model.Comic;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

public class ComicsListFragment extends Fragment implements ComicsListPresenter.View {

    public static final String EXTRA_COMIC = "com.example.shubham.marvel.extra_comic";
    private static final String FILTER_DIALOGUE = "filter_dialogue";

    @BindView(R.id.comics_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.comics_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.empty_tv)
    TextView emptyTv;

    @BindView(R.id.pages_tv)
    TextView pagesTv;

    @Inject
    ComicsListPresenter mPresenter;

    private ComicsListAdapter mAdapter;

    private Unbinder mUnbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject Presenter
        DaggerComicsListComponent.builder()
                .appComponent(((MarvelApp) getActivity().getApplication()).getAppComponent())
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comics_list, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        setUpToolBar();
        setUpListView();
        mPresenter.register(this);
        setHasOptionsMenu(true);
        return view;
    }

    private void setUpToolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
    }

    private void setUpListView() {
        mAdapter = new ComicsListAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showComics(List<Comic> comics) {
        mAdapter.showComics(comics);
    }

    @Override
    public Observable<Comic> onComicClicked() {
        return mAdapter.getComicClickSubject();
    }

    @Override
    public Observable<Object> onRefreshAction() {
        return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .startWith((Notification.INSTANCE));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.comics_list_frag_menu, menu);
    }

    @Override
    public Observable<MenuItem> onFilterAction() {
        return RxToolbar.itemClicks(toolbar)
                .filter(menuItem -> menuItem.getItemId() == R.id.action_filter);
    }

    @Override
    public void openComicDetail(Comic comic) {
        Intent intent = new Intent(getActivity(), ComicDetailActivity.class);
        intent.putExtra(EXTRA_COMIC, comic);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    @Override
    public void showEmptyView(boolean visible) {
        emptyTv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTotalPages(int pages) {
        pagesTv.setText(getResources().getString(R.string.total_pages_format, pages));
    }

    @Override
    public void showFilters() {
        ComicsListFilterDialogFragment comicsListFilterDialogFragment = new ComicsListFilterDialogFragment();
        comicsListFilterDialogFragment.setTargetFragment(this, 0);
        comicsListFilterDialogFragment.show(getFragmentManager(), FILTER_DIALOGUE);
    }

    @Override
    public void showRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void onDestroy() {
        mPresenter.unregister();
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showMessage(int error) {
        Snackbar.make(getView(), getString(error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
