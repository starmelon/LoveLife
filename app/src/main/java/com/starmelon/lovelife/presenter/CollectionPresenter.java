package com.starmelon.lovelife.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.starmelon.lovelife.data.Collection;
import com.starmelon.lovelife.data.source.CollectionDataSource;
import com.starmelon.lovelife.data.source.CollectionRepository;
import com.starmelon.lovelife.data.source.local.CollectionLocalDataSource;

import java.util.Date;
import java.util.List;


/**
 * Created by starmelon on 2016/11/29 0029.
 */

public class CollectionPresenter extends BasePresenter<CollectionContact.View> implements CollectionContact.Presenter {


    private final CollectionRepository mCollectionRepository;


    public CollectionPresenter() {
        mCollectionRepository = CollectionRepository.getInstance(CollectionLocalDataSource.getInstance());
    }

    @Override
    public void deleteCollection(String newsid) {
        mCollectionRepository.deleteCollection(newsid);
        getView().refreshCollection();
    }

    @Override
    public void loadCollections() {

        long time = new Date().getTime();
        mCollectionRepository.getCollectionsByTime(time, 10, new CollectionDataSource.LoadCollectionsCallback() {
            @Override
            public void onCollectionsLoaded(List<Collection> collections) {
                if (collections.size()>0){
                    getView().showCollections(collections);
                }else{
                    getView().showEmpty();
                }

            }

            @Override
            public void onDataNotAvailable() {
                Log.v("ljkl","djk");
                //getView().s
            }
        });

    }

    @Override
    public void openNewsDetail(@NonNull Collection requestCollection) {

        getView().showNewsDetailUi(requestCollection.getNewsid());

    }
}
