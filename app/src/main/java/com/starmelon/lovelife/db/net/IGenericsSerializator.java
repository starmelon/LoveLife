package com.starmelon.lovelife.db.net;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by JimGong on 2016/6/23.
 */
public interface IGenericsSerializator {
    <T> T transform(String response,Type type);
    <T> T transform(String response,Class<T> T);
}
