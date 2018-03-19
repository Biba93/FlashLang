package com.github.biba.flashlang.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.api.models.languages.ILanguage;
import com.github.biba.flashlang.ui.domain.RecyclerClickListener;

public class LanguageDialogRowViewHolder extends RecyclerView.ViewHolder {

    public final TextView mLanguageName;

    public LanguageDialogRowViewHolder(final View pItemView, final RecyclerClickListener pClickListener) {
        super(pItemView);
        mLanguageName = pItemView.findViewById(R.id.language_row_text_view);

        pItemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                final int position = getAdapterPosition();
                pClickListener.onClick(position);
            }
        });
    }

    public void setInfo(final ILanguage pLanguage) {
        mLanguageName.setText(pLanguage.getLanguageName());
    }

}
