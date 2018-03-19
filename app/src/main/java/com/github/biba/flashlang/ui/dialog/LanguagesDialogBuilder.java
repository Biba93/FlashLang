package com.github.biba.flashlang.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.api.models.languages.ILanguage;
import com.github.biba.flashlang.ui.adapter.LanguagesDialogRecyclerViewAdapter;
import com.github.biba.flashlang.ui.domain.IRecycleClickCallback;

import java.util.List;

public class LanguagesDialogBuilder extends AlertDialog.Builder {

    private List<ILanguage> mLanguages;
    private IRecycleClickCallback<ILanguage> mCallback;
    private final Context mContext;
    private RecyclerView mRecyclerView;
    private View mView;

    public LanguagesDialogBuilder(final Context pContext) {
        super(pContext);
        mContext = pContext;
        init();
    }

    private void init() {
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if (layoutInflater != null) {
            mView = layoutInflater.inflate(R.layout.dialog_language_choose, null);
            mRecyclerView = mView.findViewById(R.id.choose_language_recycler_view);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.addItemDecoration(dividerItemDecoration);
        }

    }

    public void setLanguages(final List<ILanguage> pLanguages) {
        mLanguages = pLanguages;
    }

    public void setChoiceCallback(final IRecycleClickCallback<ILanguage> pCallback) {
        mCallback = pCallback;
    }

    @Override
    public AlertDialog create() {
        this.setView(mView);
        final LanguagesDialogRecyclerViewAdapter adapter = new LanguagesDialogRecyclerViewAdapter(mLanguages, mCallback);
        mRecyclerView.setAdapter(adapter);
        return super.create();
    }
}
