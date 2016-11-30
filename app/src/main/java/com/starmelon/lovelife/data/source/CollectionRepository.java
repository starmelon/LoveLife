package com.starmelon.lovelife.data.source;

import android.support.annotation.NonNull;

import com.starmelon.lovelife.data.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by starmelon on 2016/11/30 0030.
 */

public class CollectionRepository implements CollectionDataSource {

    private static CollectionRepository INSTANCE = null;

    private final CollectionDataSource mCollectionsLocalDataSource;


    private CollectionRepository(@NonNull CollectionDataSource collectionsLocalDataSource) {
        mCollectionsLocalDataSource = checkNotNull(collectionsLocalDataSource);
    }

    public static CollectionRepository getInstance(CollectionDataSource collectionsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CollectionRepository(collectionsLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getCollections(@NonNull LoadCollectionsCallback callback) {
        checkNotNull(callback);
        mCollectionsLocalDataSource.getCollections(callback);
    }

    @Override
    public void getCollectionsByTime(@NonNull long time, @NonNull int limit, @NonNull LoadCollectionsCallback callback) {
        mCollectionsLocalDataSource.getCollectionsByTime(time,limit,callback);
    }

    @Override
    public void getCollectionById(@NonNull String collectionId, @NonNull GetCollectionCallBack callBack) {
        mCollectionsLocalDataSource.getCollectionById(collectionId,callBack);
    }

    @Override
    public void addCollection(@NonNull Collection collection) {
        mCollectionsLocalDataSource.addCollection(collection);
    }

    @Override
    public void deleteAllCollections() {

    }

    @Override
    public void deleteCollection(@NonNull String collectionId) {
        mCollectionsLocalDataSource.deleteCollection(collectionId);
    }


}
