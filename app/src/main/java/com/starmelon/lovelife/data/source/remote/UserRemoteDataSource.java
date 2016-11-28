package com.starmelon.lovelife.data.source.remote;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.starmelon.lovelife.AppContext;
import com.starmelon.lovelife.MyApplication;
import com.starmelon.lovelife.data.User;
import com.starmelon.lovelife.data.source.UserDataSource;
import com.starmelon.lovelife.util.ToastUtils;
import com.starmelon.lovelife.view.activity.LoginActivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by starmelon on 2016/11/23 0023.
 */

public class UserRemoteDataSource implements UserDataSource{

    private static UserRemoteDataSource INSTANCE;

    public static UserRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRemoteDataSource();
        }
        return INSTANCE;
    }

    /**
     * 登录
     * @param platform
     * @param callback
     */
    @Override
    public void signIn(String platform, final SignInCallback callback) {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);

        if (qq.isAuthValid()){
            qq.removeAccount(true);
        }
        qq.SSOSetting(false);
        qq.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {

                callback.onError("授权错误");

            }

            @Override
            public void onComplete(final Platform qq, int arg1, HashMap<String, Object> arg2) {

                String accessToken = qq.getDb().getToken(); // 获取授权token
                String openId = qq.getDb().getUserId(); // 获取用户在此平台的ID
                String nickname = qq.getDb().getUserName();
                String headurl = qq.getDb().getUserIcon();

                if(TextUtils.isEmpty(openId)){
                    ToastUtils.show(AppContext.getInstance(),"获取授权失败");
                    //Toast.makeText(getApplication(),"获取授权失败",Toast.LENGTH_SHORT).show();
                    return;
                }

                //默认获取的为40*40的小头像
                if(!TextUtils.isEmpty(headurl) && !headurl.endsWith("/240")){
                    headurl = headurl.substring(0,headurl.length()-3) + "/240";
                }

                Log.v("getdb", qq.getDb().getUserId());
                Log.v("getdb", qq.getDb().getUserName());
                Log.v("getdb", qq.getDb().getUserIcon());
                Log.v("getdb", qq.getDb().exportData());
                //mEdt_id.setText(openId);

                User user = new User(nickname,openId,headurl);

                signIn2easemob(user,callback);

                //callback.onSuccess(user);


            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplication(),"授权取消",Toast.LENGTH_SHORT).show();
            }
        });

        qq.showUser(null);
    }

    /**
     * 登录方法
     */
    private void signIn2easemob(final User user,final SignInCallback callback) {

        if (TextUtils.isEmpty(user.getPwb()) || TextUtils.isEmpty(user.getPwb())) {
            callback.onError("登录信息获取失败");
            //Toast.makeText(LoginActivity.this, "登录信息获取失败", Toast.LENGTH_LONG).show();
            return;
        }
        EMClient.getInstance().login(user.getPwb(), user.getPwb(), new EMCallBack() {
            /**
             * 登陆成功的回调
             */
            @Override
            public void onSuccess() {

                EMClient.getInstance().chatManager().loadAllConversations();


                callback.onSuccess(user);

            }

            /**
             * 登陆错误的回调
             * @param i
             * @param s
             */
            @Override
            public void onError(final int i, final String s) {


                Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
                /**
                 * 关于错误码可以参考官方api详细说明
                 * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                 */
                switch (i) {
                    // 网络异常 2
                    case EMError.NETWORK_ERROR:
                        callback.onError("网络错误 code: " + i + ", message:" + s);
                        //Toast.makeText(LoginActivity.this, "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 无效的用户名 101
                    case EMError.INVALID_USER_NAME:
                        callback.onError("无效的用户名 code: " + i + ", message:" + s);
                        //Toast.makeText(LoginActivity.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 无效的密码 102
                    case EMError.INVALID_PASSWORD:
                        callback.onError("无效的密码 code: " + i + ", message:" + s);
                        //Toast.makeText(LoginActivity.this, "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 用户认证失败，用户名或密码错误 202
                    case EMError.USER_AUTHENTICATION_FAILED:
                        callback.onError("用户认证失败，用户名或密码错误 code: " + i + ", message:" + s);
                        //Toast.makeText(LoginActivity.this, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 用户不存在 204
                    case EMError.USER_NOT_FOUND:
                        callback.onError("用户不存在 code: " + i + ", message:" + s);
                        //signUp2easemob(user);
                        //Toast.makeText(LoginActivity.this, "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 无法访问到服务器 300
                    case EMError.SERVER_NOT_REACHABLE:
                        callback.onError("无法访问到服务器 code: " + i + ", message:" + s);
                        //Toast.makeText(LoginActivity.this, "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 等待服务器响应超时 301
                    case EMError.SERVER_TIMEOUT:
                        callback.onError("等待服务器响应超时 code: " + i + ", message:" + s);
                        //Toast.makeText(LoginActivity.this, "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 服务器繁忙 302
                    case EMError.SERVER_BUSY:
                        callback.onError("服务器繁忙 code: " + i + ", message:" + s);
                        //Toast.makeText(LoginActivity.this, "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 未知 Server 异常 303 一般断网会出现这个错误
                    case EMError.SERVER_UNKNOWN_ERROR:
                        callback.onError("未知的服务器异常 code: " + i + ", message:" + s);
                        //Toast.makeText(LoginActivity.this, "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        callback.onError("ml_sign_in_failed code: " + i + ", message:" + s);
                        //Toast.makeText(LoginActivity.this, "ml_sign_in_failed code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                }
            }


            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

//    /**
//     * 通过环信注册
//     * @param user
//     */
//    private String signUp2easemob(final User user){
//
//        final ProgressDialog mDialog = new ProgressDialog(this);
//        mDialog.setMessage("注册中，请稍后...");
//        mDialog.show();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    EMClient.getInstance().createAccount(user.getPwb(), user.getPwb());
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            if (!LoginActivity.this.isFinishing()) {
////                                mDialog.dismiss();
////                            }
////                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_LONG).show();
////                            //signIn2easemob(user);
////
////                        }
////                    });
//                } catch (final HyphenateException e) {
//                    e.printStackTrace();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//
//                            /**
//                             * 关于错误码可以参考官方api详细说明
//                             * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
//                             */
//                            int errorCode = e.getErrorCode();
//                            String message = e.getMessage();
//                            Log.d("lzan13", String.format("sign up - errorCode:%d, errorMsg:%s", errorCode, e.getMessage()));
//                            switch (errorCode) {
//                                // 网络错误
//                                case EMError.NETWORK_ERROR:
//                                    //Toast.makeText(LoginActivity.this, "网络错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
//                                    break;
//                                // 用户已存在
//                                case EMError.USER_ALREADY_EXIST:
//                                    //Toast.makeText(LoginActivity.this, "用户已存在 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
//                                    break;
//                                // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
//                                case EMError.USER_ILLEGAL_ARGUMENT:
//                                    //Toast.makeText(LoginActivity.this, "参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
//                                    break;
//                                // 服务器未知错误
//                                case EMError.SERVER_UNKNOWN_ERROR:
//                                    //Toast.makeText(LoginActivity.this, "服务器未知错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
//                                    break;
//                                case EMError.USER_REG_FAILED:
//                                    //Toast.makeText(LoginActivity.this, "账户注册失败 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
//                                    break;
//                                default:
//                                    //Toast.makeText(LoginActivity.this, "ml_sign_up_failed code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
//                                    break;
//                            }
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    @Override
    public void signOut() {

        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        if (qq.isAuthValid()){
            qq.removeAccount(true);
        }


    }
}
