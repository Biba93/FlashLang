package com.github.biba.flashlang.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.utils.DrawableUtils;
import com.github.biba.lib.imageloader.ILouvre;
import com.github.biba.lib.logs.Log;

public class ImageSampleViewHolder extends RecyclerView.ViewHolder {

    private static final String LOG_TAG = ImageSampleViewHolder.class.getSimpleName();

    private final ImageView mImageView;

    public ImageSampleViewHolder(final View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.sample_image_view);
    }

    public void setInfo(final String pImageUrl) {
        Log.d(LOG_TAG, "setInfo() called with: pImageUrl = [" + pImageUrl + "]");
        try {
            ILouvre.Impl.getInstance().newRequest()
                    .to(mImageView)
                    .from(pImageUrl)
                    .saved(true)
                    .isScaled(true)
                    .setErrorImage(DrawableUtils.bitmapFromDrawable(R.drawable.moon))
                    .load();
        } catch (final Exception pE) {
            Log.e(LOG_TAG, "setInfo: ", pE);
        }
    }
}
