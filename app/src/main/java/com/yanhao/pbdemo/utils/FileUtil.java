package com.yanhao.pbdemo.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * Created by yons on 2015/3/8.
 */
public class FileUtil {

    /**
     * 计算压缩比例
     *
     * @return
     */
    public static int calulateInSampleSize(BitmapFactory.Options options,
                                           int reqWidth, int reqHeight) {
        // 图片原始尺寸
        final int rawWidth = options.outWidth;
        final int rawHeight = options.outHeight;
        int inSampleSize = 1;
        // 判断图片尺寸是否大于指定尺寸
        if (rawWidth > reqWidth || rawHeight > reqHeight) {
            final int halfHeight = rawHeight / 2;
            final int halfWidth = rawWidth / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    /**
     * 得到压缩图片
     * @param resources
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampleBitmapFromResouce(Resources resources,int resId, int reqWidth,int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);

        options.inSampleSize=calulateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return  BitmapFactory.decodeResource(resources,resId,options);
    }
    public static Bitmap decodeSampleBitmapFromFile(File file,int reqWidth,int reqHeight){
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(file.getAbsolutePath());
        options.inSampleSize=calulateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(),options);
    }
    /**
     * 得到SD卡根目录
     * @return
     */
    public static File getRootDirectory(){
        if (Environment.getExternalStorageState()==Environment.MEDIA_UNMOUNTED){
            Log.e("error","未插入SD卡");
            return null;
        }else{
            return Environment.getExternalStorageDirectory();
        }
    }
    /**
     * 获取表示大小的字符串
     * @param size 字节数
     * @return
     */
    private static String transSize(long size){
        DecimalFormat df=new DecimalFormat("0.00");
        float tempSize=(float)size/1024;
        if(tempSize>1024){
            tempSize /=1024;
            if(tempSize>1024){
                tempSize /=1024;
                return df.format(tempSize)+"G";
            }else{
                return df.format(tempSize)+"M";
            }
        }else{
            return df.format(tempSize)+"K";
        }
    }

    /**
     * 获取表示文件大小的字符串
     * @param file
     * @return
     */
    public static String getFileSize(File file){
        if(file.isFile()){
            return transSize(file.length());
        }else{
            Log.i("error",file.getName()+"不是文件");
            return null;
        }
    }
    /**
     * 判断文件是否是指定类型
     *
     * @param file
     *            文件
     * @param fileType
     *            文件类型 如(txt, jar,png 等)
     * @return
     */
    public static boolean isCertainType(File file, String fileType) {
        String extension = ".";
        extension += fileType;
        return file.getName().endsWith(extension);
    }
    /**
     * 判断文件是否为视频
     * @param file
     * @return  根据文件的后缀名判断("mp4", "3gp")
     */
    public static boolean isVideo(File file){
        String[] types = { "mp4", "3gp" };
        boolean flag = false;
        for (String type : types) {
            flag = flag || isCertainType(file, type);
            if (flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定目录下的所有文件(包括子文件夹的文件)
     *
     * @param file
     * @return 文件路径下的所有文件
     */
    public static List<File> listFiles(File file){
        List<File> files = new ArrayList<File>();
        if (file.isDirectory()) {
            File[] fileArray = file.listFiles();
            for (File f : fileArray) {
                files.addAll(listFiles(f));
            }
        } else {
            files.add(file);
        }
        return files;
    }
    /**
     * 得到根目录下的视频文件集合
     * @param rootFile
     * @return
     */
    public static List<File> getMediaFiles(File rootFile) {
        List<File>mediaFiles=FileUtil.listFiles(rootFile);
        List<File> delFiles=new ArrayList<File>();
        for(File f:mediaFiles){
            if(!FileUtil.isVideo(f)){
                delFiles.add(f);
            }
        }
        mediaFiles.removeAll(delFiles);
        return mediaFiles;
    }

}
