package com.starmelon.lovelife.db.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.shizhefei.mvc.http.okhttp.GetMethod;
import com.shizhefei.mvc.http.okhttp.ResponseParser;
import com.starmelon.lovelife.bean.DataJson;
import com.starmelon.lovelife.bean.enties.HotNews;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 这是没有封装OKHttp请求的演示代码
 */
public class News_DataSource implements IAsyncDataSource<List<HotNews>> {
    private int classify;
    private int mPage = 1;
    private int mMaxPage = 5;

    public News_DataSource(int classify)
    {
        super();
        this.classify = classify + 1;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<HotNews>> sender) throws Exception {
        return loadBooks(sender, 1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<HotNews>> sender) throws Exception {
        return loadBooks(sender, mPage + 1);
    }

    @Override
    public boolean hasMore() {
        return mPage < mMaxPage;
    }

    private RequestHandle loadBooks(final ResponseSender<List<HotNews>> sender, final int page) throws Exception {
//        //这里只是简单的演示OKhttp，你可以再封装哦
//        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
//        formEncodingBuilder
//                .add("page", page + "")
//                .add("rows",15 + "")
//                .add("id",classify + "");
//        Request request = new Request.Builder()
//                .url(API.API_TOP_LIST)
//                .post(formEncodingBuilder.build())
//                .build();
//        Call call = new OkHttpClient().newCall(request);
//        call.enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                //send 要放在最后一句(请求结束)
//                sender.sendError(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final List<HotNews> books = new ArrayList<>();
//
////                for (int i = 0; i < 15; i++) {
////                    books.add(new Book("page" + page + "  Java编程思想 " + i, 108.00d));
////                }
//                mPage = page;
//
//                String string = response.body().string();
//                TypeToken<DataJson<HotNews>> typeToken = new TypeToken<DataJson<HotNews>>(){};
//                DataJson<HotNews> dataJson = new Gson().fromJson(string,typeToken.getType());
//
//                Log.v("Net",call.request().url().toString() );
//                Log.v("Net",dataJson.tngou.toString());
//
//                books.addAll(dataJson.tngou);
//
//                //send 要放在最后一句(请求结束)
//                sender.sendData(books);
//
//                Log.v("Net",books.size() + "");
//            }
//        });

        GetMethod method = new GetMethod(API.API_TOP_LIST);

        method.addParam("page", page + "")
                .addParam("rows",15 + "")
                .addParam("id",classify + "");
        method.executeAsync(sender, new ResponseParser<List<HotNews>>() {
            @Override
            public List<HotNews> parse(Response response) throws Exception {

                if (response.isSuccessful()) {
                    String json = response.body().string();
                    TypeToken<DataJson<HotNews>> typeToken = new TypeToken<DataJson<HotNews>>(){};

                    DataJson<HotNews> dataJson = new Gson().fromJson(json, typeToken.getType());
                    List<HotNews> hotNewses = new ArrayList<HotNews>();

                    hotNewses.addAll(dataJson.tngou);
                    for (HotNews hotNews : hotNewses){
                        hotNews.setImg(API.API_IMAGE + hotNews.getImg());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        hotNews.setTime(sdf.format(new Timestamp(Long.parseLong(hotNews.getTime()))));
                    }

                    mPage = page;
                    Log.v("Net",hotNewses.size() + "");
                    return hotNewses;
                }

                throw new Exception("fail httpcode:" + response.code());


            }
        });

        return  method;
        //return new OKHttpRequestHandle(call);
    }
}
