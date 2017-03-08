package com.vincent.hss.base;

/**
 * description ：
 * project name：CoolApp
 * author : Vincent
 * creation date: 2017/3/5 0:12
 *
 * @version 1.0
 */

public interface BaseController {


    interface IView{
        
        /**
         * @param code 表示改用正确的还是错误的
         * 消息提示
         * @param msg
         */
        void msg(int code, String msg);

        /**
         * 显示加载提示
         */
        void showDialog();

        /**
         * 关闭加载提示
         */
        void closeDialog();
    }

}
