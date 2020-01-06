package com.dan.yousuanshi.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.dan.yousuanshi.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FileUtils {
    /**文档类型*/
    public static final int TYPE_DOC = 0;
    /**apk类型*/
    public static final int TYPE_APK = 1;
    /**压缩包类型*/
    public static final int TYPE_ZIP = 2;


    /**
     * 判断文件是否存在
     * @param path 文件的路径
     * @return
     */
    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static int getFileType(String path) {
        path = path.toLowerCase();
        if (path.endsWith(".doc") || path.endsWith(".docx") || path.endsWith(".xls") || path.endsWith(".xlsx")
                || path.endsWith(".ppt") || path.endsWith(".pptx")|| path.endsWith(".txt")) {
            return TYPE_DOC;
        }else if (path.endsWith(".apk")) {
            return TYPE_APK;
        }else if (path.endsWith(".zip") || path.endsWith(".rar") || path.endsWith(".tar") || path.endsWith(".gz")) {
            return TYPE_ZIP;
        }else{
            return -1;
        }
    }

    public static int getDocType(String path){
        if(path.endsWith(".txt")){
            return 1;
        }else if(path.endsWith(".doc") || path.endsWith(".docx")){
            return 2;
        }else if(path.endsWith(".xls") || path.endsWith(".xlsx")){
            return 3;
        }else if(path.endsWith(".ppt") || path.endsWith(".pptx")){
            return 4;
        }else{
            return 0;
        }
    }

    /**通过文件名获取文件图标*/
    public static int getFileIconByPath(String path){
        path = path.toLowerCase();
        int iconId = R.mipmap.icon_unknow_file;
        if (path.endsWith(".txt")){
            iconId = R.mipmap.icon_txt;
        }else if(path.endsWith(".doc") || path.endsWith(".docx")){
            iconId = R.mipmap.icon_doc_docx;
        }else if(path.endsWith(".xls") || path.endsWith(".xlsx")){
            iconId = R.mipmap.icon_xls;
        }else if(path.endsWith(".ppt") || path.endsWith(".pptx")){
            iconId = R.mipmap.icon_ppt;
        }
        return iconId;
    }

    /**是否是图片文件*/
    public static boolean isPicFile(String path){
        path = path.toLowerCase();
        if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png")){
            return true;
        }
        return false;
    }


    /** 判断SD卡是否挂载 */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从文件的全名得到文件的拓展名
     *
     * @param filename
     * @return
     */
    public static String getExtFromFilename(String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(dotPosition + 1, filename.length());
        }
        return "";
    }
    /**
     * 读取文件的修改时间
     *
     * @param f
     * @return
     */
    public static String getModifiedTime(File f) {
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        // System.out.println("修改时间[2] " + formatter.format(cal.getTime()));
        // 输出：修改时间[2] 2009-08-17 10:32:38
        return formatter.format(cal.getTime());
    }

    /**
     * 转换文件大小
     * @param fileS
     * @return
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize="0B";
        if(fileS==0){
            return wrongSize;
        }
        if (fileS < 1024){
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576){
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        }
        else if (fileS < 1073741824){
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        }
        else{
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    // Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {
        Intent intent = null;
        try {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(Utils.getAppContext(), Utils.getAppContext().getApplicationContext().getPackageName() + ".provider", new File(param));
            } else {
                uri = Uri.fromFile(new File(param));
            }
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    // Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {
        Intent intent = null;
        try {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(Utils.getAppContext(), Utils.getAppContext().getApplicationContext().getPackageName() + ".provider", new File(param));
            } else {
                uri = Uri.fromFile(new File(param));
            }
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    // Android获取一个用于打开doc,Word文件的intent
    public static Intent getWordFileIntent(String param) {
        Intent intent = null;
        try {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(Utils.getAppContext(), Utils.getAppContext().getApplicationContext().getPackageName() + ".provider", new File(param));
            } else {
                uri = Uri.fromFile(new File(param));
            }
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/msword");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    // Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param) {
        Intent intent = null;
        try {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(Utils.getAppContext(), Utils.getAppContext().getApplicationContext().getPackageName() + ".provider", new File(param));
            } else {
                uri = Uri.fromFile(new File(param));
            }
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "text/plain");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    // Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider.getUriForFile(Utils.getAppContext(), Utils.getAppContext().getApplicationContext().getPackageName() + ".provider", new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    public static String saveFile(Context c, String fileName, Bitmap bitmap) {
        return saveFile(c, "", fileName, bitmap);
    }

    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        String dateFolder = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
                .format(new Date());
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = Environment.getExternalStorageDirectory() + "/cxs/" + dateFolder + "/";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /*
     * Java文件操作 获取文件扩展名
     * */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 判断文件夹存不存在 不存在则创建
     * @param path
     * @return
     */
    public static boolean isFolderExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static String getMp4TimeString(String path){
        File file = new File(path);

        MediaPlayer meidaPlayer = new MediaPlayer();

        try {
            meidaPlayer.setDataSource(file.getPath());
            meidaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = meidaPlayer.getDuration();//获得了视频的时长（以毫秒为单位）
        if(time / 1000 > 10){
            return "0:"+time/1000;
        }else {
            return "0:0"+time/1000;
        }
    }

    public static long getMp4Time(String path){
        File file = new File(path);

        MediaPlayer meidaPlayer = new MediaPlayer();

        try {
            meidaPlayer.setDataSource(file.getPath());
            meidaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return meidaPlayer.getDuration()/1000;//获得了视频的时长
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }
}
