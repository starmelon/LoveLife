package com.starmelon.lovelife.util.net;

import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;


import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by JimGong on 2016/6/23.
 */

public abstract class GenericsCallback<T> extends Callback<T> {

    IGenericsSerializator mGenericsSerializator;

    public GenericsCallback(IGenericsSerializator serializator) {
        mGenericsSerializator = serializator;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {

        String string = response.body().string();

        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }

        T bean = mGenericsSerializator.transform(string, entityClass);

        if (bean == null){
            Type type = new TypeToken<T>(){}.getType();
            bean = mGenericsSerializator.transform(string,type);
        }


        return bean;
    }

}
