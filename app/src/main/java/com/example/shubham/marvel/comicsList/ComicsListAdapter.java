package com.example.shubham.marvel.comicsList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.shubham.marvel.R;
import com.example.shubham.marvel.model.Comic;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

public class ComicsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Comic> comics = new ArrayList<>();
    private static Context context;
    private PublishSubject<Comic> mComicClickSubject;


    public ComicsListAdapter(Context context) {
        this.context = context;
        mComicClickSubject = PublishSubject.create();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_comic, parent, false);
        return new ComicViewHolder(view, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicViewHolder comicViewHolder = (ComicViewHolder) holder;
        Comic comic = comics.get(position);
        comicViewHolder.bind(comic);

        RxView.clicks(holder.itemView)
                .takeUntil(RxView.detaches(((ComicViewHolder) holder).getParentView()))
                .map(aVoid -> comic)
                .subscribe(mComicClickSubject);
    }


    @Override
    public int getItemCount() {
        return comics.size();
    }

    void showComics(List<Comic> comics) {
        this.comics.clear();
        this.comics.addAll(comics);
        notifyDataSetChanged();
    }

    public PublishSubject<Comic> getComicClickSubject() {
        return mComicClickSubject;
    }

    static class ComicViewHolder extends RecyclerView.ViewHolder {

        private final ViewGroup parentView;

        @BindView(R.id.comic_title_textview)
        TextView titleTextView;

        @BindView(R.id.comic_thumbnail_imageview)
        ImageView thumbnailImageView;


        ComicViewHolder(View view, ViewGroup parent) {
            super(view);
            this.parentView = parent;
        }

        void bind(Comic comic) {
            ButterKnife.bind(this, itemView);
            titleTextView.setText(comic.getTitle());

            Glide.with(context).load(comic.getThumbnail()).asBitmap().into(new BitmapImageViewTarget(thumbnailImageView) {
                @Override
                protected void setResource(Bitmap bitmap) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                    circularBitmapDrawable.setCircular(true);
                    thumbnailImageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        }


        public ViewGroup getParentView() {
            return parentView;
        }
    }
}
