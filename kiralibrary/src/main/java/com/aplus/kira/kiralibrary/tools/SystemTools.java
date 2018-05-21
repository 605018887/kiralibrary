package com.aplus.kira.kiralibrary.tools;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Base64;


import com.aplus.kira.kiralibrary.UpdateService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static com.aplus.kira.kiralibrary.tools.PhoneBroadUtil.SYS_EMUI;
import static com.aplus.kira.kiralibrary.tools.PhoneBroadUtil.SYS_MIUI;


public class SystemTools {
    public static final int TAKE_PICTURE_CODE = 1001;
    public static final int FROM_PHOTO_CODE = 1002;
    public static final int CROP_IMAGE_CODE = 1003;
    public static final int NOTIFICATION_CODE = 1004;
    public static String PICTURE_PATH;
    public static String HEAD_PATH;
    public static boolean TAKE_PICTURE = false;
    public static Uri imageUri;
    public static final String AutoStart = "AutoStart";

    /**
     * 拍照
     *
     * @param activity
     * @param picName  图片名字
     */
    public static void takePicture(Activity activity, String picName) {
        File DatalDir = Environment.getExternalStorageDirectory();
        File myDir = new File(DatalDir, "mAppExternalPath");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String SDPATH = DatalDir.toString() + "/mAppExternalPath";
        File f = new File(SDPATH, picName + ".jpeg");
        if (f.exists()) {
            f.delete();
        }
        PICTURE_PATH = SDPATH + "/" + picName + ".jpeg";
        Intent cameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            imageUri = FileProvider.getUriForFile(activity, activity.getApplication().getPackageName() + ".fileprovider", f);
        } else {
            imageUri = Uri.fromFile(f);
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraIntent.putExtra(picName, f);
        activity.startActivityForResult(cameraIntent, TAKE_PICTURE_CODE);

    }

    /**
     * 从手机获取图片的url
     *
     * @param activity
     */
    public static void getImageUrlFromPhone(Activity activity) {
        Intent picture = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(picture, FROM_PHOTO_CODE);
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 裁切图片
     *
     * @param activity
     * @param uri      手机的uri
     * @param cropX    裁切的初始宽度
     * @param cropY    裁切的初始高度
     */
    public static void cropImageUri(Activity activity, Uri uri, int cropX,
                                    int cropY, String picName) {
        File DatalDir = Environment.getExternalStorageDirectory();
        File myDir = new File(DatalDir + "/DCIM/Camera");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String SDPATH = DatalDir.toString() + "/DCIM/Camera";
        File f = new File(SDPATH, picName);
        if (f.exists()) {
            f.delete();
        }
        HEAD_PATH = SDPATH + "/" + picName;
        imageUri = Uri.fromFile(f);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", cropX);
        intent.putExtra("aspectY", cropY);
        intent.putExtra("outputX", cropX);
        intent.putExtra("outputY", cropY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivityForResult(intent, CROP_IMAGE_CODE);
    }

    /**
     * 发送通知到标题栏
     *
     * @param context
     * @param intent
     * @param icon      图标rid
     * @param ticket    提示
     * @param title     标题
     * @param content   内容
     * @param isSound   是否发出声音
     * @param isVibrate 是否震动
     */
    public static void sendNotification(Context context, Intent intent,
                                        int icon, String ticket, String title, String content,
                                        boolean isSound, boolean isVibrate) {
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder1 = new Notification.Builder(context);
        builder1.setSmallIcon(icon); //设置图标
        builder1.setTicker(ticket);
        builder1.setContentTitle(title); //设置标题
        builder1.setContentText(content); //消息内容
        builder1.setWhen(System.currentTimeMillis()); //发送时间
        if (isSound) {
            builder1.setDefaults(Notification.DEFAULT_SOUND);
        }
        if (isVibrate) {
            builder1.setDefaults(Notification.DEFAULT_VIBRATE);
        }
        //设置默认的提示音，振动方式，灯光
        builder1.setAutoCancel(false);//打开程序后图标消失

        // n.flags=Notification.FLAG_ONGOING_EVENT;
        PendingIntent pi = PendingIntent.getActivity(context,
                (int) (Math.random() * 1000) + 1, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        clearNotification(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            nm.notify(NOTIFICATION_CODE, builder1.build());
        }
    }

    /**
     * 发送通知到标题栏
     *
     * @param context
     * @param intent
     * @param icon      图标rid
     * @param ticket    提示
     * @param title     标题
     * @param content   内容
     * @param isSound   是否发出声音
     * @param isVibrate 是否震动
     * @param notiCode  通知code
     */
    public static void sendNotification(Context context, Intent intent,
                                        int icon, String ticket, String title, String content,
                                        boolean isSound, boolean isVibrate, int notiCode) {
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder1 = new Notification.Builder(context);
        builder1.setSmallIcon(icon); //设置图标
        builder1.setTicker(ticket);
        builder1.setContentTitle(title); //设置标题
        builder1.setContentText(content); //消息内容
        builder1.setWhen(System.currentTimeMillis()); //发送时间
        if (isSound) {
            builder1.setDefaults(Notification.DEFAULT_SOUND);
        }
        if (isVibrate) {
            builder1.setDefaults(Notification.DEFAULT_VIBRATE);
        }
        //设置默认的提示音，振动方式，灯光
        builder1.setAutoCancel(false);//打开程序后图标消失
        // n.flags=Notification.FLAG_ONGOING_EVENT;
        PendingIntent pi = PendingIntent.getActivity(context,
                (int) (Math.random() * 1000) + 1, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        clearNotification(context, notiCode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            nm.notify(notiCode, builder1.build()
            );
        }
    }

    /**
     * 删除通知
     *
     * @param context
     */
    public static void clearNotification(Context context) {
        // 启动后删除之前我们定义的通知
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_CODE);
    }

    /**
     * 删除指定通知
     *
     * @param context
     * @param notiCode 通知code
     */
    public static void clearNotification(Context context, int notiCode) {
        // 启动后删除之前我们定义的通知
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notiCode);
    }

    /**
     * 删除所有通知
     *
     * @param context
     */
    public static void clearAllNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    /**
     * 更新app
     *
     * @param url 网址
     */
    public static void updateApp(Context context, String url) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);// 打开浏览器更新
    }

    /**
     * 后台更新
     *
     * @param context
     * @param title       标题
     * @param updateUrl   域名
     * @param iconId      图片id
     * @param packageName 包名
     */
    public static void updateInBackground(Context context, String title,
                                          String updateUrl, int iconId, String packageName) {
        Intent updateIntent = new Intent(context, UpdateService.class);
        updateIntent.putExtra("title", title);
        updateIntent.putExtra("updateUrl", updateUrl);
        updateIntent.putExtra("iconId", iconId);
        updateIntent.putExtra("serviceName", "com.UpdateService");
        updateIntent.putExtra("packageName", packageName);
        context.startService(updateIntent);
    }

    /**
     * 设置顶部栏的颜色
     *
     * @param activity
     */
    public static void initSystemBar(Activity activity, int color,
                                     int headViewId) {

        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // setTranslucentStatus(activity, true);
        // ViewTools.setVisible(activity, headViewId);
        // }
        //
        // SystemBarTintManager tintManager = new
        // SystemBarTintManager(activity);
        // tintManager.setStatusBarTintEnabled(true);
        // // 使用颜色资源
        // tintManager.setStatusBarTintResource(color);
        // }

        // private static void setTranslucentStatus(Activity activity, boolean
        // on) {
        //
        // Window win = activity.getWindow();
        //
        // WindowManager.LayoutParams winParams = win.getAttributes();
        //
        // final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        //
        // if (on) {
        //
        // winParams.flags |= bits;
        //
        // } else {
        //
        // winParams.flags &= ~bits;
        //
        // }
        //
        // win.setAttributes(winParams);
        //
    }

    /**
     * 获取手机序列号
     *
     * @return
     */
    public static String getPhoneId() {
        String id = Build.SERIAL;
        return id;
    }

    /**
     * 把文件转为64位字符串
     *
     * @param path 文件路径
     * @return 64位字符串
     */
    public static String encodeBase64File(String path) {
        try {
            File file = new File(path);
            UsualTools.showPrintMsg("file=" + file.length());
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.NO_WRAP);
        } catch (Exception e) {
            UsualTools.showPrintMsg(e.toString());
            return "";
        }
    }

    /*打开自启动管理页*/
    public static void openStart(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(AutoStart,
                        Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT < 23 || sharedPreferences.getBoolean("is_received", false) || sharedPreferences.getBoolean("is_first", true) == false) {
            return;
        }
        PhoneBroadUtil broadUtil = new PhoneBroadUtil();
        String system = broadUtil.getSystem();
        if (system == null) {
            return;
        }
        Intent intent = new Intent();
        if (system.equals(SYS_EMUI)) {//华为
            ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
            intent.setComponent(componentName);
        } else if (system.equals(SYS_MIUI)) {//小米
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
            intent.setComponent(componentName);
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {//抛出异常就直接打开设置页面
            intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);

        } finally {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_first", false);
            editor.apply();
        }
    }

    //这个是获取SHA1的方法
    public static String getCertificateSHA1Fingerprint(Context context) {
        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取当前要获取SHA1值的包名，也可以用其他的包名，但需要注意，
        //在用其他包名的前提是，此方法传递的参数Context应该是对应包的上下文。
        String packageName = context.getPackageName();
        //返回包括在包中的签名信息
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            //获得包的所有内容信息类
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //签名信息
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        //将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(cert);
        //证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        //X509证书，X.509是一种非常通用的证书格式
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            //加密算法的类，这里的参数可以使MD4,MD5等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");
            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());
            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }
    //这里是将获取到得编码进行16进制转换
    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }


    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                        new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

}