package com.example.miaowenzhao.notes.uitls;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.bumptech.glide.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

/**
 * 创建缓存空间
 */

public class ImageLoader {
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    /*内存缓存*/
    private static LruCache<String, Bitmap> mMemoryCache;
    /*磁盘缓存*/
    private DiskLruCache mDisklruCache;
    private Context context;
    private Bitmap bitmap;

    private ImageLoader(Context context) {
        this.context = context.getApplicationContext();
        /*程序最大的缓存大小*/
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        /*内存缓存大小是程序缓存大小的八分之一*/
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };

        /*得到默认的sd卡缓存地址*/
        File disCacheDir = context.getCacheDir();
        if (!disCacheDir.exists()) {
            disCacheDir.mkdirs();
        }
        /*磁盘缓存*/
        try {
            mDisklruCache = DiskLruCache.open(disCacheDir,1,1,DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*判断内存缓存是否有文件*/
    public static void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if(getBitmapFromMemCache(key)==null){
            mMemoryCache.put(key,bitmap);
        }
    }

    private static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}
