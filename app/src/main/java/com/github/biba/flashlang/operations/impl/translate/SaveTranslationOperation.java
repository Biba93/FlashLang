package com.github.biba.flashlang.operations.impl.translate;

import com.github.biba.flashlang.api.models.translation.ITranslation;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.domain.models.card.CardBuilder;
import com.github.biba.flashlang.domain.models.user.IUser;
import com.github.biba.flashlang.operations.Operations;
import com.github.biba.flashlang.utils.OperationUtils;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.threading.ExecutorType;
import com.github.biba.lib.threading.IThreadingManager;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.executors.IExecutor;
import com.github.biba.lib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SaveTranslationOperation implements IOperation<Void> {

    public static final int WORDS_INCREMENT = 2;
    public static final int CONNECTIONS_INCREMENT = 1;
    private final ITranslation mTranslation;
    private final IUser mUser;
    private final IExecutor mExecutor;

    public SaveTranslationOperation(final IUser pOwner, final ITranslation pTranslation) {
        mTranslation = pTranslation;
        mUser = pOwner;
        mExecutor = IThreadingManager.Imlp.getThreadingManager().getExecutor(ExecutorType.EXECUTOR_SERVICE);
    }

    @Override
    public Void perform() {
        final String sourceLanguageCode = mTranslation.getSourceLanguage().getLanguageCode();
        final String targetLanguageCode = mTranslation.getTargetLanguage().getLanguageCode();
        final String sourceText = mTranslation.getSourceText();
        final String translatedText = mTranslation.getTranslatedText();

        final CardBuilder cardBuilder = new CardBuilder()
                .setId(OperationUtils.getIdForCard())
                .setOwnerId(mUser.getId())
                .setSourceLanguageKey(sourceLanguageCode)
                .setSourceText(sourceText)
                .setTargetLanguageKey(targetLanguageCode)
                .setTranslatedText(translatedText);

        final String collectionId = OperationUtils.getCollectionId(mUser.getId(),
                sourceLanguageCode,
                targetLanguageCode);

        if (StringUtils.isNullOrEmpty(collectionId)) {

            final IOperation<String> createCollectionOperation = Operations.newOperation()
                    .common()
                    .create()
                    .collection(mUser.getId(),
                            sourceLanguageCode,
                            targetLanguageCode);

            final ICallback<String> callback = new ICallback<String>() {

                @Override
                public void onSuccess(final String pCollectionId) {
                    cardBuilder.setReferredCollectionId(pCollectionId);
                    uploadCardToDb(cardBuilder.createCard());
                }

                @Override
                public void onError(final Throwable pThrowable) {
                }
            };

            final Command<String> createCollectionCommand = new Command<>(createCollectionOperation);
            createCollectionCommand.setCallback(callback);
            mExecutor.execute(createCollectionCommand);
            increaseAchievement(mUser.getId(), CONNECTIONS_INCREMENT, WORDS_INCREMENT);

        } else {
            cardBuilder.setReferredCollectionId(collectionId);
            uploadCardToDb(cardBuilder.createCard());
            increaseAchievement(mUser.getId(), 0, WORDS_INCREMENT);
        }

        return null;
    }

    private void uploadCardToDb(final Card pCard) {
        final IOperation<Boolean> uploadToLocalDb = Operations.newOperation()
                .info()
                .local()
                .card()
                .upload(pCard);
        final IOperation<Void> uploadToFirebase = Operations.newOperation()
                .info()
                .firebase()
                .card()
                .upload(pCard);
        final List<ICommand> commands = new ArrayList<>();
        commands.add(new Command<>(uploadToLocalDb));
        commands.add(new Command<>(uploadToFirebase));
        mExecutor.execute(commands);
    }

    private void increaseAchievement(final String pUserId, final int pConnectionsIncrement, final int pWordsIncrement) {
        final IOperation<Void> increaseUserAchievement = Operations.newOperation()
                .common()
                .update()
                .increaseUserAchievement(pUserId, pConnectionsIncrement, pWordsIncrement);
        final ICommand command = new Command<>(increaseUserAchievement);
        mExecutor.execute(command);
    }

}
