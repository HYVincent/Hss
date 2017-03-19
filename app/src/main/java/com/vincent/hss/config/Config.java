package com.vincent.hss.config;

/**
 * description ：
 * project name：Hss
 * author : Vincent
 * creation date: 2017/3/5 22:42
 *
 * @version 1.0
 */

public class Config {

    /**
     * 这是我的服务器地址 CoolWeb是我的Web项目名称
     */
    public static final String SERVICE_API_ADDRESS = "http://182.254.232.121:8080/CoolWeb/";

    public static final String SERVICE_API_WEATHER = "https://api.thinkpage.cn/v3/weather/";

    /**
     * QQ分享的标题
     */
    public static final String QQ_SHARE_TITLE = "智能家车系统";

    /**
     * QQ分享的内容
     */
    public static final String QQ_SHARE_CONTENT = "分享的内容";

    /**
     * logo地址
     */
    public static final String QQ_SHARE_LOGO = "http://182.254.232.121:8080/Image/app_logo.png";

    /**
     * 点击跳转的URL地址
     */
    public static final String QQ_SHARE_CLICK_GO_URL = "http://182.254.232.121:8080/apk/1.0.1/app-huawei-release-101.apk";
    /**
     * 聚合天气api
     */
    public static final String JUHE_WEATHER_API = "http://op.juhe.cn/onebox/weather/";

    public static final String JUHE_APP_KEY = "bcb6bfa67f8db11c66f43a92c00a6855";
    /**
     * 这是SharedPreferences配置名称
     */
    public static final String CONFIG_NAME = "config_name";

    /**
     * 头像后面的那一块背景
     */
    public static final String HEAD_BACKGROUND = "head_background";

    /**
     * 反馈失败的时候保存title
     */
    public static final String FEEDBACK_TITLE = "feedback_title";

    /**
     * 反馈提交失败的时候保存内容
     */
    public static final String FEEDBACK_CONTENT = "feedback_content";

    /**
     * 存储用户选择的头像 服务器接口没弄好暂时就这么整
     */
    public static final String USER_HEAD_IMG = "user_head_img";

    /**
     * 友盟APPKey
     */
    public static final String UMENG_APP_KEY = "58bccb28f43e48601a002764";

    /**
     * 是否登录成功
     */
    public static final String IS_LOGIN = "is_login";

    /**
     * 是否是第一次启动
     */
    public static final String IS_FIRST_START = "is_first_start";

    /**
     * 用户
     */
    public static final String USER = "user";

    /**
     * 高德地图APP key
     */
    public static final String LOCAL_GAODE_KEY = "0e2163b5ed5eb95337adb330f3e16333";

    /**
     * 底部第一个文字
     */
    public static final String APP_MAIN_TAB_TEXT_ONE = "首页";

    /**
     * 底部第二个文字
     */
    public static final String APP_MAIN_TAB_TEXT_TWO = "首页";

    /**
     * 底部第三个文字
     */
    public static final String APP_MAIN_TAB_TEXT_THREE = "首页";

    /**
     * 底部第四个文字
     */
    public static final String APP_MAIN_TAB_TEXT_FOUR = "设置";

    /**
     * QQ分享appid
     */
    public static final String QQ_SHARE_APP_ID = "1105781615";


}
