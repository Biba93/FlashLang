package com.github.biba.flashlang.domain.models.card;

public class CardBuilder {

    private String mId;
    private String mOwnerId;
    private String mReferredCollectionId;
    private String mSourceLanguageKey;
    private String mSourceText;
    private String mTargetLanguageKey;
    private String mTranslatedText;
    private String mPictureUrl;

    public String getId() {
        return mId;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public String getReferredCollectionId() {
        return mReferredCollectionId;
    }

    public String getSourceLanguageKey() {
        return mSourceLanguageKey;
    }

    public String getSourceText() {
        return mSourceText;
    }

    public String getTargetLanguageKey() {
        return mTargetLanguageKey;
    }

    public String getTranslatedText() {
        return mTranslatedText;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public CardBuilder setId(final String pId) {
        mId = pId;
        return this;
    }

    public CardBuilder setOwnerId(final String pOwnerId) {
        mOwnerId = pOwnerId;
        return this;
    }

    public CardBuilder setReferredCollectionId(final String pReferredCollectionId) {
        mReferredCollectionId = pReferredCollectionId;
        return this;
    }

    public CardBuilder setSourceLanguageKey(final String pSourceLanguageKey) {
        mSourceLanguageKey = pSourceLanguageKey;
        return this;
    }

    public CardBuilder setSourceText(final String pSourceText) {
        mSourceText = pSourceText;
        return this;
    }

    public CardBuilder setTargetLanguageKey(final String pTargetLanguage) {
        mTargetLanguageKey = pTargetLanguage;
        return this;
    }

    public CardBuilder setTranslatedText(final String pTranslatedText) {
        mTranslatedText = pTranslatedText;
        return this;
    }

    public CardBuilder setPictureUrl(final String pPictureUrl) {
        mPictureUrl = pPictureUrl;
        return this;
    }

    public Card createCard() {
        return new Card(this);
    }
}
