package com.vincent.hss.network.service;


import com.vincent.hss.bean.Weather;
import com.vincent.hss.bean.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * description ：
 * project name：CoolApp
 * author : Vincent
 * creation date: 2017/3/4 23:06
 *
 * @version 1.0
 */

public interface ApiService {
    /*个人信息模块*/
    /**
     * 用户注册
     * @param phone
     * @param password
     * @return
     */
    @POST("register")
    Call<Result> register(@Query("phone") String phone, @Query("password") String password);

    /**
     * 用户登录
     * @param phone
     * @param login
     * @return
     */
    @POST("login")
    Call<Result> login(@Query("phone") String phone, @Query("password") String login);

    /**
     * 修改用户个人资料
     * @param phone
     * @param sex
     * @param birthday
     * @param nickname
     * @param live_status
     * @return
     */
    @POST("updateUserInfo")
    Call<Result> updateUserInfo(@Query("phone") String phone, @Query("sex") String sex, @Query("birthday") String birthday,
                                @Query("nickname") String nickname, @Query("live_status") String live_status);

    /**
     * 忘记密码
     * @param phone
     * @param password
     * @return
     */
    @POST("resetPassword")
    Call<Result> resetPassword(@Query("phone")String phone,@Query("password")String password);


    /**
     * 修改用户密码
     * @param phone
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @POST("alterUserPassword")
    Call<Result> alterUserPassword(@Query("phone")String phone,@Query("old_password")String oldPassword,@Query("new_password")String newPassword);

    /*日记模块*/
    /**
     * 添加日记
     * @param user_id
     * @param diaryTitle
     * @param diaryContent
     * @return
     */
    @POST("insertDiary")
    Call<Result> insertDiary(@Query("user_id") String user_id, @Query("diaryTitle") String diaryTitle, @Query("diaryContent") String diaryContent);

    /**
     * 删除用户的所有日记
     * @param user_id
     * @return
     */
    @POST("deleteUserAllDiary")
    Call<Result> deleteUserAllDiary(@Query("user_id") String user_id);

    /**
     * 删除用户的某条日记记录
     * @param user_id
     * @param diaryTitle
     * @return
     */
    @POST("deleteOneDiary")
    Call<Result> deleteOneDiary(@Query("user_id") String user_id, @Query("diaryTitle") String diaryTitle);

    /**
     * 获取用户所有的日记
     * @param user_id
     * @return
     */
    @GET("getUserAllDiary")
    Call<Result> getUserAllDiary(@Query("user_id") String user_id);

    /**
     * 完整路径
     * @param city
     * @return
     */
    @GET("https://api.thinkpage.cn/v3/weather/now.json?key=cxb4vnpbr2autg9z&language=zh-Hans&unit=c")
    Call<String> cityWeather(@Path("location")String city);

    /*https://api.thinkpage.cn/v3/weather/now.json?key=cxb4vnpbr2autg9z&language=zh-Hans&unit=c&location="+city;*/
    @GET("now.json")
    Call<String> getCityWeather(@Query("key")String key,
                                @Query("language")String language,
                                @Query("unit")String unit,
                                @Query("location")String city);
    /**
     * 聚合天气api
     * @param cityname
     * @param key
     * @return
     */
    @GET("query")
    Call<Weather> getWeather(@Query("cityname")String cityname, @Query("key")String key);

    /**
     * 提交反馈
     * @param phone
     * @param title
     * @param content
     * @return
     */
    @POST("addFeedback")
    Call<Result> submitFeedback(@Query("phone")String phone,@Query("title")String title,@Query("content")String content);

    /**
     * 获取反馈历史记录
     * @param phone
     * @return
     */
    @GET("getAllFeedback")
    Call<Result> getFeedHistory(@Query("phone")String phone);

    /**
     * 消息推送调试接口
     * @param phone
     * @return
     */
    @POST("push")
    Call<Result> testPush(@Query("phone")String phone);

    /**
     * 把房间信息发送到服务器
     * @param phone
     * @param roomType
     * @param roomName
     * @param roomImg
     * @param roomBigImg
     * @return
     */
    @POST("addRoom")
    Call<Result> addRoom(@Query("phone")String phone,
                         @Query("roomType")String roomType,
                         @Query("roomName")String roomName,
                         @Query("roomImg")String roomImg,
                         @Query("roomBigImg")String roomBigImg);

    /**
     * 删除某个房间
     * @param phone
     * @param roomName
     * @return
     */
    @POST("deleteRoomItem")
    Call<Result> deleteRoomItem(@Query("phone")String phone,
                                @Query("roomName")String roomName);

    /**
     * 获取房间列表
     * @param phone
     * @return
     */
    @GET("getAllRoom")
    Call<Result> getAllRoom(@Query("phone")String phone);
}
