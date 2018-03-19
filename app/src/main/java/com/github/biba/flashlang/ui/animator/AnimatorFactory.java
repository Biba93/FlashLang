package com.github.biba.flashlang.ui.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.github.biba.flashlang.utils.SystemConfigUtils;

public final class AnimatorFactory {

    public static Animator createFlyAwayAnimator(final View pTargetView) {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, (-1) * SystemConfigUtils.getScreenHeight());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                final float value = (float) animation.getAnimatedValue();
                pTargetView.setTranslationY(value);
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        return valueAnimator;
    }

}
