package com.github.biba.flashlang.ui.viewholder;

import android.view.View;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.api.utils.LanguageUtils;
import com.github.biba.flashlang.domain.models.collection.ICollection;
import com.github.biba.flashlang.ui.domain.RecyclerClickListener;
import com.github.biba.flashlang.utils.DrawableUtils;
import com.github.biba.lib.imageloader.ILouvre;
import com.github.biba.lib.logs.Log;

public class TargetLanguageViewHolder extends BaseLanguageViewHolder {

    private static final String LOG_TAG = TargetLanguageViewHolder.class.getSimpleName();

    public TargetLanguageViewHolder(final View pItemView, final RecyclerClickListener pClickListener) {
        super(pItemView, pClickListener);
    }

    @Override
    public void setInfo(final ICollection pCollection) {
        mLanguageName.setText(LanguageUtils.getLanguageName(pCollection.getTargetLanguage()));
        try {
            ILouvre.Impl.getInstance()
                    .newRequest()
                    .to(mImage)
                    .from(pCollection.getTargetLanguageCover())
                    .setErrorImage(DrawableUtils.bitmapFromDrawable(R.drawable.moon))
                    .load();
        } catch (final Exception pE) {
            Log.e(LOG_TAG, "setInfo: ", pE);
        }

    }
}
