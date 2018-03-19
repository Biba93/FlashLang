package com.github.biba.flashlang.ui.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.domain.models.collection.ICollection;
import com.github.biba.flashlang.ui.domain.IRecycleClickCallback;
import com.github.biba.flashlang.ui.domain.RecyclerClickListener;
import com.github.biba.flashlang.ui.viewholder.BaseLanguageViewHolder;
import com.github.biba.flashlang.ui.viewholder.SourceLanguageViewHolder;
import com.github.biba.flashlang.ui.viewholder.TargetLanguageViewHolder;
import com.github.biba.lib.logs.Log;
import com.github.biba.lib.utils.IOUtils;

public class LanguagesRecyclerViewCursorAdapter extends RecyclerView.Adapter<BaseLanguageViewHolder> implements RecyclerClickListener {

    private static final String LOG_TAG = LanguagesRecyclerViewCursorAdapter.class.getSimpleName();

    private Cursor mCursor;
    private LanguageItemType mLanguageItemType;
    private final IRecycleClickCallback<ICollection> mCallback;
    private final Collection.CursorConverter mCursorConverter;

    public LanguagesRecyclerViewCursorAdapter(final IRecycleClickCallback<ICollection> pCallback) {
        mCallback = pCallback;
        mCursorConverter = new Collection.CursorConverter();
    }

    public void setCursor(final Cursor pCursor) {
        release();
        mCursor = pCursor;
        notifyDataSetChanged();
    }

    public void setLanguageItemType(final LanguageItemType pLanguageItemType) {
        mLanguageItemType = pLanguageItemType;
        notifyDataSetChanged();
    }

    @Override
    public BaseLanguageViewHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final int viewId = R.layout.language_item;
        final View view = LayoutInflater.from(pParent.getContext()).inflate(viewId, pParent, false);
        if (mLanguageItemType == LanguageItemType.SOURCE) {
            return new SourceLanguageViewHolder(view, this);
        } else if (mLanguageItemType == LanguageItemType.TARGET) {
            return new TargetLanguageViewHolder(view, this);
        } else {
            throw new IllegalStateException("No such item type");
        }
    }

    @Override
    public void onBindViewHolder(final BaseLanguageViewHolder pHolder, final int pPosition) {
        if (mCursor == null) {
            return;
        }
        mCursor.moveToPosition(pPosition);
        pHolder.setInfo(mCursorConverter.convert(mCursor));
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        } else {
            Log.d(LOG_TAG, "getItemCount: " + mCursor.getCount());
            return mCursor.getCount();
        }
    }

    @Override
    public void onClick(final int pPosition) {
        if (mCursor != null) {
            mCursor.moveToPosition(pPosition);
            mCallback.onClick(mCursorConverter.convert(mCursor));
        }
    }

    public void release() {
        IOUtils.close(mCursor);
        if (mCursor != null) {
            mCursor = null;
        }
    }
}
