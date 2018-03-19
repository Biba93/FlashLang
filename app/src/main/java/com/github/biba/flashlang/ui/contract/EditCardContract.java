package com.github.biba.flashlang.ui.contract;

import android.graphics.drawable.Drawable;

import com.github.biba.flashlang.domain.models.card.ICard;
import com.github.biba.flashlang.ui.presenter.BasePresenter;

public interface EditCardContract {

    interface View {

        Drawable getCardPicture();

        String getSourceText();

        String getTranslatedText();

    }

    interface Presenter extends BasePresenter<EditCardContract.View> {

        ICard getCard();

        void edit();
    }

}
