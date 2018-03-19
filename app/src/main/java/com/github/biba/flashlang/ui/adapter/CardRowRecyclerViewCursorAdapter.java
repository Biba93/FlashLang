package com.github.biba.flashlang.ui.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.domain.models.card.ICard;
import com.github.biba.flashlang.ui.domain.IRecycleClickCallback;
import com.github.biba.flashlang.ui.domain.RecyclerClickListener;
import com.github.biba.flashlang.ui.viewholder.BaseCardViewHolder;
import com.github.biba.lib.utils.IOUtils;

public class CardRowRecyclerViewCursorAdapter extends RecyclerView.Adapter<BaseCardViewHolder> implements RecyclerClickListener {

    private Cursor mCursor;
    private final IRecycleClickCallback<ICard> mCallback;
    private final Card.CursorConverter mConverter;

    public CardRowRecyclerViewCursorAdapter(final IRecycleClickCallback<ICard> pCallback) {
        mCallback = pCallback;
        mConverter = new Card.CursorConverter();
    }

    public void setCursor(final Cursor pCursor) {
        mCursor = pCursor;
        notifyDataSetChanged();
    }

    @Override
    public BaseCardViewHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final int viewId = R.layout.card_row;
        final View view = LayoutInflater.from(pParent.getContext()).inflate(viewId, pParent, false);
        return new BaseCardViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(final BaseCardViewHolder pHolder, final int pPosition) {
        if (mCursor == null) {
            return;
        }
        mCursor.moveToPosition(pPosition);
        pHolder.setInfo(mConverter.convert(mCursor));
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        } else {
            return mCursor.getCount();
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return 0;
    }

    @Override
    public void onClick(final int pPosition) {
        if (mCursor == null) {
            return;
        }
        mCursor.moveToPosition(pPosition);
        mCallback.onClick(mConverter.convert(mCursor));
    }

    public void release() {
        IOUtils.close(mCursor);
        if (mCursor != null) {
            mCursor = null;
        }
    }
}
