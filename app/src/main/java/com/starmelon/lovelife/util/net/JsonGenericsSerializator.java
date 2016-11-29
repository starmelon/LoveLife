package com.starmelon.lovelife.util.net;

import com.google.gson.Gson;

import java.lang.reflect.Type;


/**
 * Created by JimGong on 2016/6/23.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {

    Gson mGson = new Gson();

    @Override
    public <T> T transform(String response, Type type) {
        return mGson.fromJson(response,type);
    }

    @Override
    public <T> T transform(String response, Class<T> T) {
        return mGson.fromJson(response,T);
    }
}
