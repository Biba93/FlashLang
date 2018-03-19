package com.github.biba.flashlang.domain.models.user;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.ICursorConverter;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.firebase.db.annotations.FirebaseDbElement;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.lib.db.annotations.dbPrimaryKey;
import com.github.biba.lib.db.annotations.dbTable;
import com.github.biba.lib.db.annotations.dbTableElement;
import com.github.biba.lib.db.annotations.type.dbString;
import com.github.biba.lib.utils.StringUtils;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.PropertyName;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.github.biba.flashlang.domain.models.user.User.DbKeys.ID;
import static com.github.biba.flashlang.domain.models.user.User.DbKeys.NAME;
import static com.github.biba.flashlang.domain.models.user.User.DbKeys.PICTURE;
import static com.github.biba.flashlang.domain.models.user.User.DbKeys.TABLE_NAME;

@dbTable(name = TABLE_NAME)
@dbTableElement(targetTableName = TABLE_NAME)
@FirebaseDbElement(targetTableName = TABLE_NAME)
public class User implements IUser, IDbModel<String> {

    @dbPrimaryKey(isNull = false)
    @dbString(name = ID)
    @PropertyName(ID)
    private final String mId;

    @dbString(name = NAME)
    @PropertyName(NAME)
    private String mName;

    @dbString(name = PICTURE)
    @PropertyName(PICTURE)
    private String mPictureUrl;

    public User(final UserInfo pUser) {
        mId = pUser.getUid();
        mName = pUser.getEmail();
        if (StringUtils.isNullOrEmpty(mName)) {
            mName = StringUtils.extractNameFromEmail(pUser.getEmail());
        }
        mPictureUrl = String.valueOf(pUser.getPhotoUrl());
    }

    public User(final UserBuilder pUserBuilder) {
        mId = pUserBuilder.getId();
        mName = pUserBuilder.getName();
        mPictureUrl = pUserBuilder.getPictureUrl();
    }

    public void setName(final String pName) {
        mName = pName;
    }

    public void setPictureUrl(final String pPictureUrl) {
        mPictureUrl = pPictureUrl;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getPictureUrl() {
        return mPictureUrl;
    }

    @Override
    public HashMap<String, Object> convertToInsert() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(ID, this.getId());
        map.put(NAME, this.getName());
        map.put(PICTURE, this.getPictureUrl());
        return map;
    }

    @Override
    public HashMap<String, Object> convertToUpdate() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(NAME, this.getName());
        map.put(PICTURE, this.getPictureUrl());
        return map;
    }

    public static final class DbKeys {

        public static final String TABLE_NAME = "users";
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PICTURE = "picture";
    }

    public static class CursorConverter implements ICursorConverter<User> {

        @Override
        public User convert(@NonNull final Cursor pCursor) {
            final String id = pCursor.getString(pCursor.getColumnIndex(User.DbKeys.ID));
            final String name = pCursor.getString(pCursor.getColumnIndex(User.DbKeys.NAME));
            final String picture = pCursor.getString(pCursor.getColumnIndex(User.DbKeys.PICTURE));

            return new UserBuilder().setId(id)
                    .setName(name)
                    .setPictureUrl(picture)
                    .createUser();
        }
    }

    public static class DataSnapshotConverter implements IDataSnapshotConverter<User> {

        @Override
        @Nullable
        public User convert(@NotNull final DataSnapshot pSnapshot) {
            final String id = (String) pSnapshot.child(ID).getValue();
            final String name = (String) pSnapshot.child(NAME).getValue();
            final String pic = (String) pSnapshot.child(PICTURE).getValue();
            return new UserBuilder().setId(id)
                    .setName(name)
                    .setPictureUrl(pic)
                    .createUser();
        }
    }

    public static class ByIdSelector extends Selector.ByFieldSelector {

        public ByIdSelector(final String pFiledValue) {
            super(ID, pFiledValue);
        }
    }

    public static class ByNameSelector extends Selector.ByFieldSelector {

        public ByNameSelector(@NotNull final String fieldValue) {
            super(NAME, fieldValue);
        }
    }

}
