package com.github.biba.flashlang.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.card.ICard;
import com.github.biba.flashlang.ui.domain.RecyclerClickListener;
import com.github.biba.flashlang.ui.viewholder.BaseCardViewHolder;
import com.github.biba.flashlang.ui.viewholder.CardWithImageViewHolder;
import com.github.biba.lib.logs.Log;
import com.github.biba.lib.utils.StringUtils;

import java.util.List;

public class CardsRecyclerViewAdapter extends RecyclerView.Adapter<BaseCardViewHolder> {

    private static final String LOG_TAG = CardsRecyclerViewAdapter.class.getSimpleName();

    private List<ICard> mCardList;
    private RecyclerClickListener mListener;

    public CardsRecyclerViewAdapter() {
    }

    public void setData(final List<ICard> pCardList) {
        mCardList = pCardList;
        notifyDataSetChanged();
    }

    public void setRecyclerClickListener(final RecyclerClickListener pListener) {
        mListener = pListener;
    }

    @Override
    public BaseCardViewHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        return getCardViewHolder(pParent, pViewType);
    }

    private BaseCardViewHolder getCardViewHolder(final ViewGroup pParent, final int pViewType) {
        final int viewId;
        switch (pViewType) {
            case CardType.NO_IMAGE:
                Log.d(LOG_TAG, "getCardViewHolder: NO IMAGE");
                viewId = R.layout.card_view_no_image;
                break;
            case CardType.WITH_IMAGE:
                Log.d(LOG_TAG, "getCardViewHolder: IMAGE");
                viewId = R.layout.card_view_with_image;
                final View view = LayoutInflater.from(pParent.getContext()).inflate(viewId, pParent, false);
                return new CardWithImageViewHolder(view, mListener);
            default:
                viewId = R.layout.card_view_no_image;
        }
        final View view = LayoutInflater.from(pParent.getContext()).inflate(viewId, pParent, false);
        return new BaseCardViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(final BaseCardViewHolder pHolder, final int pPosition) {
        if (mCardList != null) {
            pHolder.setInfo(mCardList.get(pPosition));
        }
    }

    @Override
    public int getItemCount() {
        if (mCardList != null) {
            return mCardList.size();
        }
        return 0;
    }

    public long getItemId(final ICard pCard) {
        if (mCardList != null) {
            final int position = mCardList.indexOf(pCard);
            return getItemId(position);
        }
        return -1;
    }

    @Override
    public int getItemViewType(final int position) {
        if (position >= 0 && position < mCardList.size()) {
            final ICard card = mCardList.get(position);
            Log.d(LOG_TAG, "getItemViewType: " + card.getPictureUrl());
            if (StringUtils.isNullOrEmpty(card.getPictureUrl())) {
                return CardType.NO_IMAGE;
            } else {
                return CardType.WITH_IMAGE;
            }
        }
        return 0;
    }

}
