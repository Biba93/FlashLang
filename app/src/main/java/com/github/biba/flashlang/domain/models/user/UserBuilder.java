package com.github.biba.flashlang.domain.models.user;

public class UserBuilder {

    private String mId;
    private String mName;
    private String mPictureUrl;

    public UserBuilder setId(final String pId) {
        mId = pId;
        return this;
    }

    public UserBuilder setName(final String pName) {
        mName = pName;
        return this;
    }

    public UserBuilder setPictureUrl(final String pPictureUrl) {
        mPictureUrl = pPictureUrl;
        return this;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public User createUser() {
        return new User(this);
    }
}
