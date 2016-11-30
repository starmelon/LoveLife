package com.starmelon.lovelife.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.starmelon.lovelife.data.Collection;
import com.starmelon.lovelife.data.source.CollectionDataSource;
import com.starmelon.lovelife.data.source.local.gen.CollectionDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by starmelon on 2016/11/30 0030.
 */

public class CollectionLocalDataSource implements CollectionDataSource{

    private static CollectionLocalDataSource INSTANCE;

    private CollectionDao mDbHelper;

    // Prevent direct instantiation.
    private CollectionLocalDataSource() {
        mDbHelper = GreenDaoManager.getInstance().getSession().getCollectionDao();
    }

    public static CollectionLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CollectionLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getCollections(@NonNull LoadCollectionsCallback callback) {
        List<Collection> collections = new ArrayList<>();
        collections.addAll(GreenDaoManager
                .getInstance().getSession().getCollectionDao()
                .queryBuilder().build().list());

        if (collections.isEmpty()){
            callback.onDataNotAvailable();
        }else {
            callback.onCollectionsLoaded(collections);
        }
    }

    @Override
    public void getCollectionsByTime(@NonNull long time,@NonNull int limit,@NonNull LoadCollectionsCallback callback) {
        QueryBuilder qb = mDbHelper.queryBuilder();

        qb.where(CollectionDao.Properties.Time.lt(time));
        qb.orderAsc(CollectionDao.Properties.Time);//降序排列
        qb.limit(limit);

        List<Collection> collections = qb.build().list();

        if (collections == null){
            callback.onDataNotAvailable();
        }else {
            callback.onCollectionsLoaded(collections);
        }

    }

    @Override
    public void getCollectionById(@NonNull String collectionId, @NonNull GetCollectionCallBack callBack) {
        Collection collection = mDbHelper
                .queryBuilder()
                .where(CollectionDao.Properties.Newsid.eq(collectionId))
                .build()
                .unique();

        if (collection == null){
            callBack.onDataNotAvailable();
        }else {
            callBack.onCollectionLoaded(collection);
        }
    }


    @Override
    public void addCollection(@NonNull Collection collection) {

        long writeTime = new java.util.Date().getTime();
        collection.setTime(writeTime);
        mDbHelper.insert(collection);

    }

    @Override
    public void deleteAllCollections() {

    }

    @Override
    public void deleteCollection(@NonNull String collectionId) {

        Collection findCollection = mDbHelper.queryBuilder().where(CollectionDao.Properties.Newsid.eq(collectionId)).build().unique();
        if(findCollection != null){
            mDbHelper.deleteByKey(findCollection.getId());
        }
    }
}
