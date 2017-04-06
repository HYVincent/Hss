package com.vincent.hss.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;


import com.vise.log.ViseLog;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.List;



/**
 * Created by Administrator on 2016/7/15.
 */
public class SystemUtilts {
    private static final String TAG=SystemUtilts.class.getSimpleName();


    /**
     * 获取手机型号
     * @return
     */
    public static String getPhoneMdel(){
        String s=android.os.Build.MODEL;
        return s;

    }
    /**
     * 获取手机厂商
     * @return
     */
    public static String getPhoneManufacturer(){
        String phoneManufacturer=android.os.Build.MANUFACTURER;
        return phoneManufacturer;
    }
    /**
     * 返回系统版本号
     * @return
     */
    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.parseInt(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            ViseLog.e(e.toString(), "");

        }
        return version;
    }
    /**
     * 获取手机的IMEI号码
     * @param context
     * @return
     */
    public static String getIMEINumber(Context context){
        String imei =((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        ViseLog.d(TAG+imei);
//        不过纯APP开发SystemProperties，TelephonyProperties汇报错误，因为android.os.SystemProperties在SDK的库中是没有的，
//        需要把Android SDK 目录下data下的layoutlib.jar文件加到当前工程的附加库路径中，就可以Import。
//        如果Android Pad没有IMEI,用此方法获取设备ANDROID_ID：
//        String IMEI =SystemProperties.get(android.telephony.TelephonyProperties.PROPERTY_IMEI);
        return imei;
    }
    /**
     * 判断应用是在前台还是后台
     */

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    ViseLog.e("后台", appProcess.processName);
                    return true;
                } else {
                    ViseLog.e("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }
    /**
     * 判断某个服务是否正在运行的Method
     *
     * @param mContext
     * @param serviceName
     * 是包�?+服务的类名（例如：net.loonggg.testbackstage.TestService�?
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 开启权限
     * @param context
     */
    public static void goSetting(Context context){
        try {
            Intent intent = new Intent("com.shangyi.sayimo");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.coloros.safecenter",
                    "com.coloros.safecenter.permission.floatwindow.FloatWindowListActivity");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            //抛出异常时提示信息
            Toast.makeText(context, "进入失败手动进入", Toast.LENGTH_LONG).show();
        }
    }
    public static boolean isAppInstalled(Context context,String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean installed =false;
        try {
            pm.getPackageInfo(packageName,PackageManager.GET_ACTIVITIES);
            installed =true;
        } catch(PackageManager.NameNotFoundException e) {
            installed =false;
        }
        return installed;
    }
    public static File getSystemDir(Context context, String name){
        return context.getDir(name, Context.MODE_PRIVATE);
    }

    public static File getSystemFile(File dir, String fileName) throws IllegalArgumentException {
        if(dir == null || !dir.exists()){
            throw new IllegalArgumentException(" dir no exists!");
        }
        File file = new File(dir, fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                file = null;
                ViseLog.e(TAG, " create new file error! "+ e);
            }
        }
        return file;
    }

    public static boolean saveSystemFile(String content, File file){
        if(file != null && file.exists() && file.isFile()){
            if(content == null){
                content = "";
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(content.getBytes("UTF-8"));
                return true;
            } catch (FileNotFoundException e) {
                ViseLog.e(TAG, " no found error! "+ e);
            } catch (UnsupportedEncodingException e) {
                ViseLog.e(TAG, " encoding error! "+ e);
            } catch (IOException e) {
                ViseLog.e(TAG, " io error! "+ e);
            } catch (Exception e) {
                ViseLog.e(TAG, " other error! "+ e);
            }finally {
                closeCloseable(fos);
            }
        }
        return false;
    }

    public static String readSystemFile(File file){
        String result = null;
        if(file != null && file.exists() && file.isFile()){
            FileInputStream fis = null;
            ByteArrayOutputStream bos = null;
            try {
                fis = new FileInputStream(file);
                bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = fis.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                result = bos.toString("UTF-8");
            } catch (FileNotFoundException e) {
                ViseLog.e(TAG, " no found error! "+e);
            } catch (IOException e) {
                ViseLog.e(TAG, " io error! "+ e);
            } catch (Exception e) {
                ViseLog.e(TAG, " other error! "+ e);
            } finally {
                closeCloseable(bos);
                closeCloseable(fis);
            }
        }
        return result;
    }

    public static void deleteSystemDir(Context context, String dirName){
        File dir = getSystemDir(context, dirName);
        deleteDir(dir);
    }

    public static void deleteSystemFile(Context context, String dirName, String fileName){
        File dir = getSystemDir(context, dirName);
        if(dir != null){
            File file = new File(dir, fileName);
            deleteFile(file);
        }
    }

    public static boolean deleteDir(File dir){
        if(dir == null || !dir.exists()){
            return false ;
        }
        try {
            deleteAllFile(dir);
            dir.delete();
            return true;
        } catch (Exception e) {
            ViseLog.e(TAG, " delete dir error! "+ e);
            return false;
        }
    }

    public static void deleteAllFile(File dir){
        if (dir != null && dir.exists() && dir.isDirectory()) {
            String[] filesList = dir.list();
            File tempFile = null;
            int length = filesList.length;
            try {
                for (int i = 0; i < length; i++) {
                    tempFile = new File(dir, filesList[i]);
                    if (tempFile != null) {
                        if (tempFile.isFile()) {
                            tempFile.delete();
                        } else if (tempFile.isDirectory()) {
                            deleteDir(tempFile);
                        }
                    }
                }
            } catch (Exception e) {
                ViseLog.e(TAG, " delete all file error! "+ e);
            }
        }
    }

    public static void deleteFile(File file){
        if (file != null && file.exists() && file.isFile()) {
            file.delete();
        }
    }

    private static void closeCloseable(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
    /**
     * 通过反射获取类对象
     * @param context
     * @param className 包含包名
     * @return
     */
    public static Object getReflectInstance(Context context, String className){
        try {
            //获取Student的Class对象
            Class<?> clazz = Class.forName(className);
            //获取该类中所有的属性
            Field[] fields = clazz.getDeclaredFields();
            //遍历所有的属性
            for (Field field : fields) {
                //打印属性信息，包括访问控制修饰符，类型及属性名
                System.out.println(field);
//                System.out.println("修饰符：" + Modifier.toString(field.getModifiers()));
                ViseLog.d(SystemUtilts.class.getSimpleName(),"修饰符：" + Modifier.toString(field.getModifiers()));
                ViseLog.d(SystemUtilts.class.getSimpleName(),"类型：" + field.getType());
                ViseLog.d(SystemUtilts.class.getSimpleName(),"属性名：" + field.getName());
            }
            //获取该类中的所有方法
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                //打印方法签名
                System.out.println(method);
                ViseLog.d(SystemUtilts.class.getSimpleName(),method.toString());
                ViseLog.d(SystemUtilts.class.getSimpleName(),"修饰符：" + Modifier.toString(method.getModifiers()));
                ViseLog.d(SystemUtilts.class.getSimpleName(),"方法名：" + method.getName());
                ViseLog.d(SystemUtilts.class.getSimpleName(),"返回类型：" + method.getReturnType());
                //获取方法的参数对象
                Class<?>[] clazzes = method.getParameterTypes();
                for (Class<?> class1 : clazzes) {
                    ViseLog.d(SystemUtilts.class.getSimpleName(),"参数类型：" + class1);
                }
            }
            //通过Class对象创建实例
//            Student student = (Student)clazz.newInstance();
//            //获取属性名为studentName的字段(Field)对象，以便下边重新设置它的值
//            Field studentName = clazz.getField("studentName");
//            //设置studentName的值为”张三“
//            studentName.set(student, "张三");
//
//            //通过Class对象获取名为”finishTask“，参数类型为String的方法(Method)对象
//            Method finishTask = clazz.getMethod("finishTask", String.class);
//            //调用finishTask方法
//            finishTask.invoke(student, "数学");
            return clazz.newInstance();
        } catch (RuntimeException e) {
            throw e;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 对网络连接状态进行判断
     *
     * @return true, 可用； false， 不可用
     */
    public static boolean isOpenNetwork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            //2.获取当前网络连接的类型信息
            int networkType = networkInfo.getType();
            if (ConnectivityManager.TYPE_WIFI == networkType) {
                //当前为wifi网络
                return true;
            } else if (ConnectivityManager.TYPE_MOBILE == networkType) {
                //当前为mobile网络
                return true;
            }
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }
}
