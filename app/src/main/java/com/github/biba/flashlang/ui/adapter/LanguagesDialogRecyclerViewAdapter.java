package com.github.biba.flashlang.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.api.models.languages.ILanguage;
import com.github.biba.flashlang.ui.domain.IRecycleClickCallback;
import com.github.biba.flashlang.ui.domain.RecyclerClickListener;
import com.github.biba.flashlang.ui.viewholder.LanguageDialogRowViewHolder;

import java.util.List;

public class LanguagesDialogRecyclerViewAdapter extends RecyclerView.Adapter<LanguageDialogRowViewHolder> implements RecyclerClickListener {

    private final List<ILanguage> mLanguages;
    private final IRecycleClickCallback<ILanguage> mCallback;

    public LanguagesDialogRecyclerViewAdapter(final List<ILanguage> pLanguages, final IRecycleClickCallback<ILanguage> pCallback) {
        mLanguages = pLanguages;
        mCallback = pCallback;
    }

    @Override
    public LanguageDialogRowViewHolder onCreateViewHolder(final ViewGroup pParent, final int viewType) {
        final int viewId = R.layout.language_row;
        final View view = LayoutInflater.from(pParent.getContext()).inflate(viewId, pParent, false);
        return new LanguageDialogRowViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(final LanguageDialogRowViewHolder pHolder, final int pPosition) {
        final ILanguage language = mLanguages.get(pPosition);
        pHolder.setInfo(language);
    }

    @Override
    public int getItemCount() {
        return mLanguages.size();
    }

    @Override
    public void onClick(final int pPosition) {
        mCallback.onClick(mLanguages.get(pPosition));
    }
}
