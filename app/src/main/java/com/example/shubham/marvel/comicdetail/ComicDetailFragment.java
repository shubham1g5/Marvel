package com.example.shubham.marvel.comicdetail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.shubham.marvel.MarvelApp;
import com.example.shubham.marvel.R;
import com.example.shubham.marvel.model.Author;
import com.example.shubham.marvel.model.Comic;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.shubham.marvel.comicsList.ComicsListFragment.EXTRA_COMIC;

public class ComicDetailFragment extends Fragment implements ComicDetailPresenter.View {

    @Inject
    ComicDetailPresenter mPresenter;

    @BindView(R.id.comic_title)
    TextView comicTitleTv;

    @BindView(R.id.comic_body)
    TextView comicBodyTv;

    @BindView(R.id.comic_price)
    TextView comicPriceTv;

    @BindView(R.id.comic_pages)
    TextView comicPagesTv;

    @BindView(R.id.comic_image)
    ImageView comicImageView;

    @BindView(R.id.app_bar_detail)
    Toolbar toolbar;

    @BindView(R.id.authors_recyclerview)
    RecyclerView authorsRecyclerView;

    private Unbinder unbinder;
    private AuthorsListAdapter mAuthorsListAdapter;

    public static ComicDetailFragment newInstance(Comic comic) {
        ComicDetailFragment comicDetailFragment = new ComicDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_COMIC, comic);
        comicDetailFragment.setArguments(args);
        return comicDetailFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Comic comic = getArguments().getParcelable(EXTRA_COMIC);

        // Inject Presenter
        DaggerComicDetailComponent.builder()
                .appComponent(((MarvelApp) getActivity().getApplication()).getAppComponent())
                .comicDetailPresenterModule(new ComicDetailPresenterModule(comic))
                .build()
                .inject(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_comic_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPresenter.register(this);
        setUpToolBar();
        setUpAuthorsListView();
        return view;
    }

    private void setUpAuthorsListView() {
        mAuthorsListAdapter = new AuthorsListAdapter(getActivity());
        authorsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        authorsRecyclerView.setAdapter(mAuthorsListAdapter);
    }

    private void setUpToolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.unregister();
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void displayTitle(String title) {
        comicTitleTv.setText(title);
    }

    @Override
    public void displayDescription(Spanned description) {
        comicBodyTv.setText(description);
    }

    @Override
    public void displayImage(String imageUrl) {
        Glide.with(getActivity())
                .load(imageUrl)
                .asBitmap()
                .into(new BitmapImageViewTarget(comicImageView) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(bitmap, glideAnimation);
                        setStatusBarColorFromBitmap(bitmap);
                    }
                });
    }

    @Override
    public void showAuthors(List<Author> authors) {
        mAuthorsListAdapter.showAuthors(authors);
    }

    @Override
    public void displayPrice(double price) {
        comicPriceTv.setText(getString(R.string.price_format, price));
    }

    @Override
    public void displayPageCount(int pages) {
        comicPagesTv.setText(getResources().getQuantityString(R.plurals.pages, pages, pages));
    }

    private void setStatusBarColorFromBitmap(Bitmap bitmap) {
        Palette.from(bitmap).generate(p -> {
            Palette.Swatch dominantSwatch = p.getDominantSwatch();
            if (dominantSwatch != null && getActivity() != null) {
                getActivity().getWindow().setStatusBarColor(dominantSwatch.getRgb());
                getActivity().supportStartPostponedEnterTransition();
            }
        });
    }

    @Override
    public void showMessage(int error) {
        Snackbar.make(getView(), getString(error), Snackbar.LENGTH_LONG).show();
    }
}
