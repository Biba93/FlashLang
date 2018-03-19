package com.github.biba.flashlang.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.biba.flashlang.R;

public class ChoosePhotoDialogBuilder extends AlertDialog.Builder {

    private final Context mContext;
    private IDialogCallback<String> mCallback;
    private View mView;

    public ChoosePhotoDialogBuilder(final Context pContext) {
        super(pContext);
        mContext = pContext;
        init();
    }

    public void setCallback(final IDialogCallback<String> pCallback) {
        mCallback = pCallback;
    }

    private void init() {
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if (layoutInflater != null) {
            mView = layoutInflater.inflate(R.layout.dialog_photo, null);
            mView.findViewById(R.id.take_photo_container).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    mCallback.onChoice(v.getTag().toString());
                }
            });
            mView.findViewById(R.id.choose_photo_container).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    mCallback.onChoice(v.getTag().toString());
                }
            });
        }
    }

    @Override
    public AlertDialog create() {
        this.setView(mView);
        return super.create();
    }
}
