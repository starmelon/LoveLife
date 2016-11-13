package com.starmelon.lovelife.db.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.starmelon.lovelife.bean.enties.Collection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by starmelon on 2016/11/13 0013.
 */

public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add collection
     * @param collections
     */
    public void add(List<Collection> collections) {
        db.beginTransaction();  //开始事务
        try {
            for (Collection collection : collections) {
                db.execSQL("INSERT INTO collection VALUES(null, ?, ?, ?)", new Object[]{collection.id, collection.title, collection.time});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * update collection's time
     * @param collection
     */
    public void updateTime(Collection collection) {
        ContentValues cv = new ContentValues();
        //cv.put("age", collection.age);
        cv.put("time", collection.time);
        db.update("collection", cv, "title = ?", new String[]{collection.title});
    }

    /**
     * delete old collection
     * @param collection
     */
    public void deleteCollectionById(Collection collection) {
        db.delete("collection", "age = ?", new String[]{String.valueOf(collection.id)});
    }

    /**
     * query all collections, return list
     * @return List<collection>
     */
    public List<Collection> query() {
        ArrayList<Collection> collections = new ArrayList<>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Collection collection = new Collection();
            collection.id = c.getInt(c.getColumnIndex("_newsid"));
            collection.time = c.getString(c.getColumnIndex("time"));
            collection.title = c.getString(c.getColumnIndex("title"));
            collections.add(collection);
        }
        c.close();
        return collections;
    }

    /**
     * query all persons, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }

}
