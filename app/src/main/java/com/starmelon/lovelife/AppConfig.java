package com.starmelon.lovelife;

import android.app.Application;
import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;

import com.starmelon.lovelife.util.SPutils;
import com.starmelon.lovelife.util.WinUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年9月25日 下午5:29:00
 */
public class AppConfig {

    public final static boolean NIGHT = true;
    public final static boolean DAY = false;

    private final static String APP_CONFIG = "config";

    public final static String CONF_COOKIE = "cookie";

    public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";

    public static final String KEY_LOAD_IMAGE = "KEY_LOAD_IMAGE";
    public static final String KEY_NOTIFICATION_ACCEPT = "KEY_NOTIFICATION_ACCEPT";
    public static final String KEY_NOTIFICATION_SOUND = "KEY_NOTIFICATION_SOUND";
    public static final String KEY_NOTIFICATION_VIBRATION = "KEY_NOTIFICATION_VIBRATION";
    public static final String KEY_NOTIFICATION_DISABLE_WHEN_EXIT = "KEY_NOTIFICATION_DISABLE_WHEN_EXIT";
    public static final String KEY_CHECK_UPDATE = "KEY_CHECK_UPDATE";
    public static final String KEY_DOUBLE_CLICK_EXIT = "KEY_DOUBLE_CLICK_EXIT";

    public static final String KEY_TWEET_DRAFT = "KEY_TWEET_DRAFT";
    public static final String KEY_NOTE_DRAFT = "KEY_NOTE_DRAFT";

    public static final String KEY_FRITST_START = "KEY_FRIST_START";

    public static final String KEY_NIGHT_MODE_SWITCH = "night_mode_switch";

    public static final String APP_QQ_KEY = "100942993";

    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "OSChina"
            + File.separator + "osc_img" + File.separator;

    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "OSChina"
            + File.separator + "download" + File.separator;

    private Context mContext;
    private static AppConfig INSTANCE = null;

    private boolean isNightMode;
    private boolean isNightModeChanging;


    public static AppConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppConfig();
            INSTANCE.mContext = MyApplication.getContext();
            INSTANCE.iniConfig();
        }
        return INSTANCE;
    }

    public boolean isNightMode() {
        return isNightMode;
    }

    public boolean isNightModeChanging() {
        return isNightModeChanging;
    }

    public void setNightModeChanging(boolean nightModeChanging) {
        isNightModeChanging = nightModeChanging;
    }

    private void iniConfig(){
        isNightMode = getNightModeSetting();
        isNightModeChanging = false;
    }



    /**
     * 获取夜间模式的保存状态
     * @return
     */
    private boolean getNightModeSetting(){
        return (boolean) SPutils.get(mContext,"NigthMode",false);
    }

    public Bitmap theLastViewCut = null;

    public void setNightMode(boolean isNight){

//        //setScreenshot();
//        UiModeManager mUiModeManager = (UiModeManager) ActivityManager.currentActivity().getSystemService(Context.UI_MODE_SERVICE);
//
//        if (isNight){
//            mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
//        }else{
//            mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
//        }
//        SPutils.put(MyApplication.getContext(),"NigthMode",isNight);
        isNightMode = isNight;
    }



    /**
     * 获取Preference设置
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String get(String key) {
        Properties props = get();
        return (props != null) ? props.getProperty(key) : null;
    }

    public Properties get() {
        FileInputStream fis = null;
        Properties props = new Properties();
        try {
            // 读取files目录下的config
            // fis = activity.openFileInput(APP_CONFIG);

            // 读取app_config目录下的config
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            fis = new FileInputStream(dirConf.getPath() + File.separator
                    + APP_CONFIG);

            props.load(fis);
        } catch (Exception e) {
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return props;
    }

    private void setProps(Properties p) {
        FileOutputStream fos = null;
        try {
            // 把config建在files目录下
            // fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

            // 把config建在(自定义)app_config的目录下
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            File conf = new File(dirConf, APP_CONFIG);
            fos = new FileOutputStream(conf);

            p.store(fos, null);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public void set(Properties ps) {
        Properties props = get();
        props.putAll(ps);
        setProps(props);
    }

    public void set(String key, String value) {
        Properties props = get();
        props.setProperty(key, value);
        setProps(props);
    }

    public void remove(String... key) {
        Properties props = get();
        for (String k : key)
            props.remove(k);
        setProps(props);
    }

    /**
     * 比较时间是否在1小时内，是则为有效期
     * @param time time
     * @return isExpiryDate true or false
     */
    public static boolean isExpiryDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = format.parse(time);
            long delta = new Date().getTime() - date.getTime();
            if (delta < 24L * 3600000L)
                return true;
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
