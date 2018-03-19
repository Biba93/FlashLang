package com.github.biba.flashlang.domain.models.collection;

public class CollectionBuilder {

    private String mId;
    private String mOwnerId;
    private String mSourceLanguage;
    private String mTargetLanguage;
    private String mSourceLanguageCover;
    private String mTargetLanguageCover;

    public CollectionBuilder setId(final String pId) {
        mId = pId;
        return this;
    }

    public CollectionBuilder setOwnerId(final String pOwnerId) {
        mOwnerId = pOwnerId;
        return this;
    }

    public CollectionBuilder setSourceLanguage(final String pSourceLanguage) {
        mSourceLanguage = pSourceLanguage;
        return this;
    }

    public CollectionBuilder setTargetLanguage(final String pTargetLanguage) {
        mTargetLanguage = pTargetLanguage;
        return this;
    }

    public CollectionBuilder setSourceLanguageCover(final String pSourceLanguageCover) {
        mSourceLanguageCover = pSourceLanguageCover;
        return this;
    }

    public CollectionBuilder setTargetLanguageCover(final String pTargetLanguageCover) {
        mTargetLanguageCover = pTargetLanguageCover;
        return this;
    }

    public String getId() {
        return mId;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public String getSourceLanguage() {
        return mSourceLanguage;
    }

    public String getTargetLanguage() {
        return mTargetLanguage;
    }

    public String getSourceLanguageCover() {
        return mSourceLanguageCover;
    }

    public String getTargetLanguageCover() {
        return mTargetLanguageCover;
    }

    public Collection createCollection() {
        return new Collection(this);
    }
}
