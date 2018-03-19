package com.github.biba.flashlang.ui.contract;

import android.graphics.drawable.Drawable;

import com.github.biba.flashlang.domain.models.achievement.IAchievement;
import com.github.biba.flashlang.domain.models.user.IUser;
import com.github.biba.flashlang.ui.presenter.BasePresenter;

public interface UserInfoContract {

    interface View {

        Drawable getImage();

        void onUserLoaded(IUser pUser);

        void onUserLoadError(String pErrorMessage);

        void onAchievementLoaded(IAchievement pAchievement);

    }

    interface Presenter extends BasePresenter<UserInfoContract.View> {

        void getUser();

        void getAchievements();

        void logout();

    }

}
