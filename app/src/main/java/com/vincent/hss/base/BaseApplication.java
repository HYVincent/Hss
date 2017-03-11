package com.vincent.hss.base;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.umeng.analytics.MobclickAgent;
import com.vincent.hss.bean.User;
import com.vincent.hss.bean.dao.DaoUtils;
import com.vincent.hss.config.Config;
import com.vincent.hss.netty.PushClient;
import com.vincent.hss.servoce.NettyPushService;
import com.vincent.hss.utils.SharePreferencesUtils;
import com.vise.log.ViseLog;
import com.vise.log.inner.DefaultTree;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * description ：
 * project name：CoolApp
 * author : Vincent
 * creation date: 2017/3/4 22:58
 *
 * @version 1.0
 */

public class BaseApplication extends MultiDexApplication {

    private static  BaseApplication application;
    private static SharePreferencesUtils shared;
    public static User user;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        DaoUtils.init(this,"Hss");
        initNetty();
        initUser();
        initViseLog();
        initUmeng();
        initOkhttpUtils();
    }

    private void initNetty() {
        try {
            PushClient.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUser() {
        String users = getShared().getString(Config.USER,"");
        if(!TextUtils.isEmpty(users)){
            user = JSON.parseObject(users,User.class);
        }else{
            user = new User();
        }
    }

    private void initOkhttpUtils() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    private void initUmeng() {
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }



    /**
     * 初始化日志工具
     */
    private void initViseLog() {
//        ViseLog.init("Supplier");
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("ViseLog")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new DefaultTree());//添加打印日志信息到Logcat的树
    }
    /**
     * 获取一个可编辑的Sharepreferences.edit对象
     * @return
     */
    public static SharePreferencesUtils getShared(){
        if(shared == null){
            shared = new SharePreferencesUtils(getApplication(), Config.CONFIG_NAME);
        }
        return shared;
    }

    /**
     * 获取全局的Application对象
     * @return
     */
    public static BaseApplication getApplication() {
        return application;
    }


}
