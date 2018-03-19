package com.github.biba.flashlang.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.firebase.auth.IFirebaseUserManager;
import com.github.biba.flashlang.ui.ViewConstants;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    View mFlImage;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mFlImage = findViewById(R.id.fl_image_view);
        //init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        final Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        mFlImage.setAnimation(fadeIn);
        fadeIn.setDuration(ViewConstants.Splash.ANIMATION_DURATION_MILLIS);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                final FirebaseUser currentUser = IFirebaseUserManager.Impl.Companion.getInstance().getCurrentUser();
                routeToPage(currentUser);
                finish();
            }
        }, ViewConstants.Splash.ANIMATION_DURATION_MILLIS);
    }

    private void routeToPage(final FirebaseUser pUser) {
        if (pUser == null) {
            startAuthActivity();
        } else {
            startMainActivity();
        }
    }

    private void startAuthActivity() {
        final Intent intent = new Intent(this, AuthActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            final ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, mFlImage, "icon");
            startActivity(intent, optionsCompat.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private void startMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
