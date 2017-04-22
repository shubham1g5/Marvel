package com.example.shubham.marvel.comicdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shubham.marvel.R;
import com.example.shubham.marvel.model.Author;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AuthorsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_HEADER = 1;
    private static final int ITEM_VIEW_TYPE_NORMAL = 2;

    private final List<Author> authors = new ArrayList<>();
    private final Context mContext;


    public AuthorsListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return ITEM_VIEW_TYPE_HEADER;
            default:
                return ITEM_VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;
        switch (viewType) {
            case ITEM_VIEW_TYPE_HEADER:
                view = layoutInflater.inflate(R.layout.list_item_author_header, parent, false);
                return new AuthorViewHolder(view);
            case ITEM_VIEW_TYPE_NORMAL:
                view = layoutInflater.inflate(R.layout.list_item_author, parent, false);
                return new AuthorsListAdapter.AuthorViewHolder(view);
            default:
                throw new IllegalStateException("Invalid view type " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_NORMAL) {
            AuthorsListAdapter.AuthorViewHolder AuthorViewHolder = (AuthorsListAdapter.AuthorViewHolder) holder;
            Author Author = authors.get(position - 1);
            AuthorViewHolder.bind(Author);
        }
    }


    @Override
    public int getItemCount() {
        return authors.size() + 1;
    }

    void showAuthors(List<Author> Authors) {
        this.authors.clear();
        this.authors.addAll(Authors);
        notifyDataSetChanged();
    }

    static class AuthorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author_name_textview)
        TextView nameTextView;

        @BindView(R.id.author_role_textview)
        TextView roleTextView;

        AuthorViewHolder(View view) {
            super(view);
        }

        void bind(Author author) {
            ButterKnife.bind(this, itemView);
            nameTextView.setText(author.getName());
            roleTextView.setText(author.getRole());

        }
    }
}
