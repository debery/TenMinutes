package com.example.tenminutestest.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import androidx.exifinterface.media.ExifInterface;

import com.example.tenminutestest.MyApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yemao on 2017/3/15.
 * 关于图片的工具类
 */

public class BitmapUtil {

    private static String PHOTO_FILE_NAME = "PMSManagerPhoto";

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    public static int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap setRotateAngle(int angle, Bitmap bitmap) {

        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;

    }
    //错误：open failed: EISDIR (Is a directory)
//    public static File saveFile(Bitmap bm,String filename) throws IOException {
//        String path = MyApplication.context.getExternalCacheDir()+"/"+filename;
//        File dirFile = new File(path);
//        dirFile.delete();
//        if(!dirFile.exists()){
//            dirFile.mkdir();
//        }
//        File newFile = new File(path);
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//        bos.flush();
//        bos.close();
//        return newFile;
//    }


    //转换为圆形状的bitmap
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }



    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    public static String compressImage(String filePath) {

        File oldFile=new File(filePath);

        //压缩文件路径 照片路径/
        int quality = 80;//压缩比例0-100
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = getRotateAngle(filePath);//获取相片拍摄角度

        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = setRotateAngle(degree,bm);
        }
        File outputFile;
        outputFile=new File(MyApplication.context.getExternalCacheDir()+"/"+oldFile.getPath());
//        switch (number){
//            case 1: outputFile = new File(MyApplication.context.getExternalCacheDir()+"/picture1.jpeg");
//                break;
//            case 2: outputFile = new File(MyApplication.context.getExternalCacheDir()+"/picture2.jpeg");
//                break;
//            case 3: outputFile = new File(MyApplication.context.getExternalCacheDir()+"/picture3.jpeg");
//                break;
//            case 4: outputFile = new File(MyApplication.context.getExternalCacheDir()+"/picture4.jpeg");
//                break;
//            case 5: outputFile = new File(MyApplication.context.getExternalCacheDir()+"/picture5.jpeg");
//                break;
//            case 6: outputFile = new File(MyApplication.context.getExternalCacheDir()+"/picture6.jpeg");
//                break;
//            case 7: outputFile = new File(MyApplication.context.getExternalCacheDir()+"/avatar.jpeg");
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + number);
//        }

        try {
//            if (!outputFile.exists()) {
//                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
//            }
            outputFile.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


}