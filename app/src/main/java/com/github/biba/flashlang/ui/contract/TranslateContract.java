package com.github.biba.flashlang.ui.contract;

import com.github.biba.flashlang.api.models.languages.ILanguage;
import com.github.biba.flashlang.ui.presenter.BasePresenter;

import java.util.List;

public interface TranslateContract {

    interface View {

        void onTranslationSuccess(String pTranslatedText);

        void onTranslateError(String pErrorMessage);

        String getSourceLanguage();

        String getSourceText();

        String getTargetLanguage();

    }

    interface Presenter extends BasePresenter<TranslateContract.View> {

        void translate();

        List<ILanguage> loadSupportedLanguages();
    }

}
