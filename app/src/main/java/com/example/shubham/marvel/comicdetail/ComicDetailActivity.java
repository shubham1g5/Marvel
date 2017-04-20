package com.example.shubham.marvel.comicdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.shubham.marvel.model.Comic;

import static com.example.shubham.marvel.comicsList.ComicsListFragment.EXTRA_COMIC;

public class ComicDetailActivity extends AppCompatActivity {

    private static final String DETAILFRAGMENT_TAG = "detail_frag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();
        Comic comic = getIntent().getParcelableExtra(EXTRA_COMIC);

        ComicDetailFragment detailFragment = ComicDetailFragment.newInstance(comic);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, detailFragment, DETAILFRAGMENT_TAG)
                .commit();
    }
}
