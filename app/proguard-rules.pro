# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android_SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }
 -dontwarn okhttp3.**
 # glide 的混淆代码
 -keep public class * implements com.bumptech.glide.module.GlideModule
 -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
   **[] $VALUES;
   public *;
 }
 # banner 的混淆代码
 -keep class com.youth.banner.** {
     *;
  }
  -dontwarn javax.swing.**
  -dontwarn java.rmi.**
  -dontwarn org.apache.**
  -dontwarn org.zeroturnaround.javarebel.**
  -dontwarn org.jaxen.**
  -dontwarn com.sun.**
  -dontwarn org.jdom.**
  -dontwarn javax.servlet.**
  -dontwarn javax.el.**
  -dontwarn org.python.**
  -dontwarn net.sqlcipher.**
  -dontwarn org.greenrobot.**
  -dontwarn java.beans.**
  -dontwarn freemarker.**
  -dontwarn org.codehaus.**
  -dontwarn java.nio.file.**
  -dontwarn java.lang.**

