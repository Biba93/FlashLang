package com.github.biba.flashlang.ui.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.card.ICard;
import com.github.biba.flashlang.ui.domain.RecyclerClickListener;
import com.github.biba.lib.imageloader.ILouvre;
import com.github.biba.lib.logs.Log;

public class CardWithImageViewHolder extends BaseCardViewHolder {

    private static final String LOG_TAG = CardWithImageViewHolder.class.getSimpleName();

    private final ImageView mImageView;

    public CardWithImageViewHolder(final View pItemView, final RecyclerClickListener pClickListener) {
        super(pItemView, pClickListener);
        mImageView = pItemView.findViewById(R.id.card_image_view);
    }

    @Override
    public void setInfo(final ICard pCard) {
        super.setInfo(pCard);
        try {
            ILouvre.Impl.getInstance()
                    .newRequest()
                    .from(pCard.getPictureUrl())
                    .to(mImageView)
                    .load();
        } catch (final Exception pE) {
            Log.e(LOG_TAG, "setInfo: ", pE);
        }
    }
}
