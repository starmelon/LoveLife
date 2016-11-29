package com.starmelon.lovelife.data.ifeng.news;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.shizhefei.mvc.http.okhttp.GetMethod;
import com.shizhefei.mvc.http.okhttp.ResponseParser;
import com.starmelon.lovelife.data.ifeng.IfengAPI;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


/**
 * 这是没有封装OKHttp请求的演示代码
 */
public class IfengNews_DataSource implements IAsyncDataSource<List<Item>> {
    private int classify;
    private int mPage = 1;
    private int mMaxPage = 0;

    public IfengNews_DataSource()
    {
        super();
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<Item>> sender) throws Exception {
        return loadBooks(sender, 1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<Item>> sender) throws Exception {
        return loadBooks(sender, mPage + 1);
    }

    @Override
    public boolean hasMore() {
        return mPage < mMaxPage;
    }

    private RequestHandle loadBooks(final ResponseSender<List<Item>> sender, final int page) throws Exception {


        GetMethod method = new GetMethod(IfengAPI.NEWS_LIST);

        method.addParam("id", "SYLB10")
                .addParam("page",page);

        method.executeAsync(sender, new ResponseParser<List<Item>>() {
            @Override
            public List<Item> parse(Response response) throws Exception {

                if (response.isSuccessful()) {
                    String json = response.body().string();
                    TypeToken<List<News>> typeToken = new TypeToken<List<News>>(){};


                    //News[] news_ = new Gson().fromJson(json,News[].class);

                    News news = null;
                    List<News> dataJson = new Gson().fromJson(json, typeToken.getType());
                    if (dataJson.size() == 1){
                        news = dataJson.get(0);
                    }
                    if(news == null){
                        return null;
                    }
                    List<Item> items = new ArrayList<Item>(news.getItem());

                    //items.addAll(news.);
//                    for (HotNews hotNews : hotNewses){
//                        hotNews.setImg(API.API_IMAGE + hotNews.getImg());
//                        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//                        //hotNews.setTime(sdf.format(new Timestamp(Long.parseLong(hotNews.getTime()))));
//                    }

                    mPage = page;
                    mMaxPage = news.getTotalPage();
                    Log.v("Net",items.size() + "");
                    return items;
                }

                throw new Exception("fail httpcode:" + response.code());


            }
        });

        return  method;
        //return new OKHttpRequestHandle(call);
    }
}
