package com.vincent.hss.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * description ：
 * project name：MyAppProject
 * author : Vincent
 * creation date: 2017/2/24 0:17
 *
 * @version 1.0
 */

public class FileUtils {

    /**
     * 读取Assets下面的文件
     * @param fileName
     * @return
     */
    public static String readAssetsText(Context context,String fileName){
        try {
            InputStream is = context.getAssets().open(fileName+".txt");
            int size = is.available();
            byte[] b = new byte[size];
            is.read(b);
            is.close();
            String content = new String(b,"utf-8");
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTextReturnStr(Context context,String fileName){
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName+".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "";
    }

}
