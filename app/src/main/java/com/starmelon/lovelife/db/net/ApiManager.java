package com.starmelon.lovelife.db.net;

import com.zhy.http.okhttp.OkHttpUtils;

//import com.zhy.http.okhttp.callback.Callback;
//import com.zhy.http.okhttp.callback.GenericsCallback;

/**
 *
 */
public class ApiManager {


//	public static abstract class Json2dataCallBack<T> extends Callback<T>{
//
//		@Override
//		public T parseNetworkResponse(Response response, int id) throws Exception {
//
//			String json = response.body().string();
//			T t = (T) new Object();
//			return new Gson().fromJson(json,T);
//		}
//	}

//	/**
//	 * 转化Json为DataJson<HotNews>的callback
//	 */
//	public static abstract class HotNewsesCallBack extends Callback<DataJson<HotNews>>{
//
//		@Override
//		public DataJson<HotNews> parseNetworkResponse(Response response, int id) throws Exception {
//
//			String json = response.body().string();
//			DataJson<HotNews> hotnewses = new Gson().fromJson(json,new  TypeToken<DataJson<HotNews>>(){}.getType());
//			return hotnewses;
//		}
//	}

	public static void getHotNewsesByClassfy(final GenericsCallback genericsCallback,int page,int Classify){

		OkHttpUtils
				.post()
				.url(API.API_TOP_LIST)
				.addParams("page",page)
				.addParams("rows", 15)
				.addParams("id", Classify)
				.build()
				.execute(genericsCallback);

	}


	/**
	 * 根据数据ID获取ID之后的数据
	 * @param hotNewsesCallBack
	 * @param id
	 * @param classify
	 */
	public static void getHotNewsesByID(GenericsCallback genericsCallback,int id, int classify){

//		OkHttpUtils
//				.post()
//				.url(API.API_TOP_NEWS)
//				.addParams("rows",15)
//				.addParams("classify", 15)
//				.addParams("id", id)
//				.build()
//				.execute(genericsCallback);

	}

	public static void getNewsDetailById(GenericsCallback json2dataCallBack, int id){

		OkHttpUtils
				.post()
				.url(API.API_TOP_SHOW)
				.addParams("id", id)
				.build()
				.execute(json2dataCallBack);

	}




	
//	/**
//	 * 根据数据ID获取详情
//	 * @param requestHandler
//	 * @param id
//	 */
//	public static void getNewsDetailById(final RequestHandler<NewsDetail> requestHandler, int id){
//
//		       //1.新建请求，指定返回结果类型
//				SimpleRequest<NewsDetail> request =  new SimpleRequest<NewsDetail>();
//
//				//2.设置请求参数
//				request.getRequestData()
//		        	.setRequestUrl(API.API_TOP_SHOW)
//					.addQueryData("id", id+"");
//
//				//3.设置请求结果处理方式
//				request.setRequestHandler(requestHandler);
//
//				//4.发送请求
//				request.send();
//
//	}
    
}
