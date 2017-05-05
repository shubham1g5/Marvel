package com.example.shubham.marvel.comicsList;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.example.shubham.marvel.MarvelApp;
import com.example.shubham.marvel.R;
import com.example.shubham.marvel.common.EventBus;

public class ComicsListFilterDialogFragment extends DialogFragment {

    private EventBus mEvenBus;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEvenBus = ((MarvelApp) getActivity().getApplication()).getAppComponent().getEventBus();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.comics_list_filter_dialogue_title)
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    String budget = ((EditText) getDialog().findViewById(R.id.budget_input)).getText().toString();
                    mEvenBus.send(new BudgetChangedEvent(budget));
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .setView(R.layout.frag_comic_list_filter)
                .create();
    }
}
