/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package com.starmelon.lovelife.util.volley;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
	private final Gson mGson = new Gson();//new GsonBuilder().registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private final Class<T> mClazz;//
	private final Type mType;
	private final Listener<T> mListener;
	private final Map<String, String> mHeaders;
	private final Map<String, String> mParams;

	public GsonRequest(int method , String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
		this(method, url , clazz , null, null, listener, errorListener);		
	}
	
	public GsonRequest(int method , String url , Type type, Listener<T> listener, ErrorListener errorListener) {
		this( method , url  , null ,type, null, listener, errorListener);
	}

	public GsonRequest(int method, String url , Class<T> clazz, Type type, Map<String, String> headers,
					   Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		
		this.mClazz = clazz;
		this.mHeaders = headers;
		this.mListener = listener;
		this.mType = type;
		this.mParams = new HashMap<String, String>();
		
	}
	
	public GsonRequest<T> setParam(String key, String value){
		
		if(!TextUtils.isEmpty(key) && value != null){
			mParams.put(key, value);
		}
		return this;
	}
	
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mParams.isEmpty() != true ? mParams :super.getParams() ;
	}
	
	
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return mHeaders != null ? mHeaders : super.getHeaders();
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			if(mType != null)
			{
				return Response.success((T)mGson.fromJson(json, mType),
						HttpHeaderParser.parseCacheHeaders(response));
			}
			return Response.success(mGson.fromJson(json, mClazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
	
	class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {
	    public JsonElement serialize(Timestamp src, Type arg1, JsonSerializationContext arg2) {
	        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	        String dateFormatAsString = format.format(new Date(src.getTime()));
	        return new JsonPrimitive(dateFormatAsString);
	    }
	 
	    public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	        if (!(json instanceof JsonPrimitive)) {
	            throw new JsonParseException("The date should be a string value");
	        }
	 
	        try {
	            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	            Date date = (Date) format.parse(json.getAsString());
	            return new Timestamp(date.getTime());
	        } catch (Exception e) {
	            throw new JsonParseException(e);
	        }
	    }
	 
	}
}
