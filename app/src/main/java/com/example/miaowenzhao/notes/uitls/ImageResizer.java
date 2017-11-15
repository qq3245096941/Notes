package com.example.miaowenzhao.notes.uitls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileDescriptor;

/**
 * 图片压缩
 */

public class ImageResizer {

    /**
     * @param
     * @param reqWidth  ImageView的宽度
     * @param reqHeight ImageView的高度
     * @return
     */
    public static Bitmap decodeSampledBitmapFileDescriptor(String imgPath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        /*并不真正的加载图片，只是拿到图片的宽高*/
        options.inJustDecodeBounds = true;
        /*拿到采样参数*/
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath,options);

        /*异步加载图片*/
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {

            }
        };
        return bitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        /*缩放图片大小*/
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize >= reqWidth)) {
                inSampleSize = inSampleSize * 2;
            }
        }
        return inSampleSize;
    }
}
