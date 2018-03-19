package com.github.biba.flashlang.operations.impl.common.impl;

import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.operations.Operations;
import com.github.biba.flashlang.utils.OperationUtils;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.logs.Log;

public class IncreaseUserAchievementOperation implements IOperation<Void> {

    private static final String LOG_TAG = IncreaseUserAchievementOperation.class.getSimpleName();

    private final int mConnectionsIncrement;
    private final int mWordsIncrement;
    private String mUserId;

    public IncreaseUserAchievementOperation(final String pUserId, final int pConnectionsIncrement, final int pWordsIncrement) {
        mUserId = pUserId;
        mConnectionsIncrement = pConnectionsIncrement;
        mWordsIncrement = pWordsIncrement;
    }

    @Override
    public Void perform() throws Exception {
        final IOperation<Achievement> getAchievement = Operations.newOperation()
                .info()
                .local()
                .achievement()
                .loadSingle(new Achievement.ByOwnerIdSelector(mUserId));
        Achievement achievement = null;
        try {
            achievement = getAchievement.perform();
        } catch (final Exception pE) {
            Log.e(LOG_TAG, "perform: ", pE);
        }
        if (achievement == null) {
            achievement = new Achievement(OperationUtils.getIdForAchievement(), mUserId);
        }
        final long totalConnections = achievement.getTotalConnections();
        final long totalWords = achievement.getTotalWords();
        achievement.setTotalConnections(totalConnections + mConnectionsIncrement);
        achievement.setTotalWords(totalWords + mWordsIncrement);
        saveAchievements(achievement);
        return null;
    }

    private void saveAchievements(final Achievement pAchievement) {
        final String achievementId = pAchievement.getId();
        final Achievement.ByIdSelector byIdSelector = new Achievement.ByIdSelector(achievementId);
        try {
            Operations.newOperation()
                    .info()
                    .firebase()
                    .achievement()
                    .upload(pAchievement)
                    .perform();
        } catch (final Exception pE) {
            Log.e(LOG_TAG, "saveAchievements: ", pE);
        }
        try {
            Operations.newOperation()
                    .info()
                    .local()
                    .achievement()
                    .upload(pAchievement)
                    .perform();
        } catch (final Exception pE) {
            Log.e(LOG_TAG, "saveAchievements: ", pE);
        }

    }
}
