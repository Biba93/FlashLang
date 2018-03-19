package com.github.biba.flashlang.ui.contract;

import com.github.biba.flashlang.domain.models.card.ICard;
import com.github.biba.flashlang.ui.presenter.BasePresenter;

import java.util.List;

public interface GameContract {

    interface View {

        String getSourceLanguageKey();

        String getTargetLanguageKey();

        void onLoaded(List<? extends ICard> pCards);

        void onError(String pErrorMessage);

    }

    interface Presenter extends BasePresenter<GameContract.View> {

        void load();
    }

}
