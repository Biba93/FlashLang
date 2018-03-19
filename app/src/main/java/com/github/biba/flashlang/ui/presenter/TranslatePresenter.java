package com.github.biba.flashlang.ui.presenter;

import com.github.biba.flashlang.UserManager;
import com.github.biba.flashlang.api.ITranslator;
import com.github.biba.flashlang.api.models.languages.ILanguage;
import com.github.biba.flashlang.api.models.translation.ITranslation;
import com.github.biba.flashlang.api.request.ITranslationRequest;
import com.github.biba.flashlang.api.request.TranslationRequestBuilder;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.Operations;
import com.github.biba.flashlang.ui.contract.TranslateContract;
import com.github.biba.flashlang.utils.ConnectionManager;
import com.github.biba.lib.context.ContextHolder;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.logs.Log;
import com.github.biba.lib.threading.ExecutorType;
import com.github.biba.lib.threading.IThreadingManager;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;

import java.util.List;

public class TranslatePresenter implements TranslateContract.Presenter {

    private static final String LOG_TAG = TranslatePresenter.class.getSimpleName();

    private TranslateContract.View mView;
    private String mApiKey;
    private List<ILanguage> mSupportedLanguages;
    private final IThreadingManager mThreadingManager;

    public TranslatePresenter() {
        mThreadingManager = IThreadingManager.Imlp.getThreadingManager();
    }

    @Override
    public void attachView(final TranslateContract.View pView) {
        mView = pView;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void translate() {
        if (mView != null) {

            if (!ConnectionManager.isNetworkAvailable()) {
                publishTranslationError(new Exception("Network is not available"));
            }

            try {
                final TranslationRequestBuilder builder = new TranslationRequestBuilder();
                builder.setSourceLanguageKey(mView.getSourceLanguage());
                builder.setTargetLanguageKey(mView.getTargetLanguage());
                builder.setInputText(mView.getSourceText());

                if (mApiKey == null) {
                    final ICallback<String> callback = new ICallback<String>() {

                        @Override
                        public void onSuccess(final String pS) {
                            Log.d(LOG_TAG, "onSuccess() called with: pS = [" + pS + "]");
                            mApiKey = pS;
                            builder.setApiKey(pS);
                            performTranslation(builder.createTranslationRequest());
                        }

                        @Override
                        public void onError(final Throwable pThrowable) {
                            Log.d(LOG_TAG, "onError() called with: pThrowable = [" + pThrowable + "]");
                            publishTranslationError(pThrowable);
                        }
                    };

                    final IOperation<Void> getApiKey = Operations.newOperation()
                            .translate()
                            .getApiKey(callback);

                    final ICommand command = new Command<>(getApiKey);
                    mThreadingManager.getExecutor(ExecutorType.THREAD)
                            .execute(command);
                } else {
                    builder.setApiKey(mApiKey);
                    performTranslation(builder.createTranslationRequest());
                }
            } catch (final Exception pE) {
                publishTranslationError(pE);
            }
        }

    }

    @Override
    public List<ILanguage> loadSupportedLanguages() {
        if (mSupportedLanguages == null) {
            final List<ILanguage> supportedLanguages = ITranslator.Impl.getTranslator(ContextHolder.getContext())
                    .getSupportedLanguages();
            if (!supportedLanguages.isEmpty()) {
                mSupportedLanguages = supportedLanguages;
            }
        }
        return mSupportedLanguages;
    }

    private void publishTranslation(final ITranslation pTranslation) {
        if (mView != null) {
            mView.onTranslationSuccess(pTranslation.getTranslatedText());
        }
    }

    private void publishTranslationError(final Throwable pThrowable) {
        if (mView != null) {
            mView.onTranslateError(pThrowable.getMessage());
        }

    }

    private void performTranslation(final ITranslationRequest pRequest) {
        final IOperation<ITranslation> translate = Operations.newOperation()
                .translate()
                .translate(pRequest);
        final ICallback<ITranslation> callback = new ICallback<ITranslation>() {

            @Override
            public void onSuccess(final ITranslation pTranslation) {
                publishTranslation(pTranslation);
                saveTranslation(pTranslation);
            }

            @Override
            public void onError(final Throwable pThrowable) {
                publishTranslationError(pThrowable);
            }
        };
        final Command<ITranslation> command = new Command<>(translate);
        command.setCallback(callback);
        mThreadingManager.getExecutor(ExecutorType.THREAD).execute(command);
    }

    private void saveTranslation(final ITranslation pTranslation) {
        final User currentUser = UserManager.getCurrentUser();
        final IOperation<Void> saveTranslation = Operations.newOperation()
                .translate()
                .saveTranslation(currentUser, pTranslation);
        final ICommand command = new Command<>(saveTranslation);
        mThreadingManager.getExecutor(ExecutorType.THREAD).execute(command);
    }

}
