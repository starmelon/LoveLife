package com.starmelon.lovelife.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.starmelon.lovelife.data.Collection;
import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.util.BaseView;

import java.util.List;

/**
 * Created by starmelon on 2016/11/24 0024.
 */

public class CollectionContact {

    public interface View extends BaseView<Presenter> {

        void showDeleteResult(String msg);
        void showEmpty();
        void showCollections(List<Collection> collections);
        void refreshCollection();
        void showNewsDetailUi(String newsid);
    }

    public interface Presenter {

        void deleteCollection(String newsid);
        void loadCollections();
        void openNewsDetail(@NonNull Collection requestCollection);
    }

}
