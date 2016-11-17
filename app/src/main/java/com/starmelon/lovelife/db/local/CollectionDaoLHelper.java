package com.starmelon.lovelife.db.local;

import com.starmelon.lovelife.bean.enties.Collection;
import com.starmelon.lovelife.db.local.gen.CollectionDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by starmelon on 2016/11/17 0017.
 */

public class CollectionDaoLHelper {

    CollectionDao collectionDao;

    public CollectionDaoLHelper(){
        collectionDao = GreenDaoManager.getInstance().getSession().getCollectionDao();
    }

//    /**
//     * 根据名字更新某条数据的名字
//     * @param prevName  原名字
//     * @param newName  新名字
//     */
//    private void updateCollection(String prevName,String newName){
//        User findUser = GreenDaoManager.getInstance().getSession().getUserDao().queryBuilder()
//                .where(UserDao.Properties.Name.eq(prevName)).build().unique();
//        if(findUser != null) {
//            findUser.setName(newName);
//            GreenDaoManager.getInstance().getSession().getUserDao().update(findUser);
//            Toast.makeText(MyApplication.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(MyApplication.getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
//        }
//    }

    /**
     * 根据ID获取收藏
     * @param newsid
     * @return
     */
    public Collection getCollecionByID(int newsid){
        return collectionDao.queryBuilder().where(CollectionDao.Properties.Newsid.eq(newsid)).build().unique();
    }

    /**
     * 根据新闻ID删除某个收藏
     * @param newsid
     */
    public void deleteCollecion(int newsid){

        Collection findCollection = collectionDao.queryBuilder().where(CollectionDao.Properties.Newsid.eq(newsid)).build().unique();
        if(findCollection != null){
            collectionDao.deleteByKey(findCollection.getId());
        }
    }

    /**
     * 本地数据里添加一个收藏
     * @param id  id
     * @param newsid
     * @param title
     * @param time
     * @return
     */
    public List<Collection> addCollection(int newsid , String title) {

        java.util.Date writeTime = new java.util.Date();

        Collection collection = new Collection(null,newsid,title,writeTime.getTime());
        collectionDao.insert(collection);

        return collectionDao.queryBuilder().build().list();

//        mNameET.setText("");
//
//        mUserList.clear();
//        mUserList.addAll();
//        mUserAdapter.notifyDataSetChanged();
    }


    /**
     * 获取所有收藏
     * @return
     */
    public List<Collection> getAllCollections(){
        return GreenDaoManager
                .getInstance().getSession().getCollectionDao()
                .queryBuilder().build().list();
    }


    /**
     * 根据时间查询数据
     * @param time
     * @return
     */
    public List<Collection> getCollectionByTime(long time,int limit){

        QueryBuilder qb = collectionDao.queryBuilder();

        qb.where(CollectionDao.Properties.Time.lt(time));
        qb.orderAsc(CollectionDao.Properties.Time);//降序排列
        qb.limit(limit);
        return qb.build().list();
    }

}
