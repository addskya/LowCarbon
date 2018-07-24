
package com.mylowcarbon.app.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoUtil {
    private static final String TAG = "PhotoUtil";

    public static final int NONE = 0;
    public static final String IMAGE_UNSPECIFIED = "image/*";//随意图片类型
    public static final int PHOTOGRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final int PICTURE_HEIGHT = 500;
    public static final int PICTURE_WIDTH = 500;
    public static final int ID_PICTURE_HEIGHT = 540;
    public static final int ID_PICTURE_WIDTH = 856;
    public static final int TYPE_PHOTO = 0;//照片
    public static final int TYPE_ID = 1;//身份照照片
    public static String imageName;
    /**
     * [回调监听类]
     *
     * @author huxinwu
     * @version 1.0
     * @date 2015-1-7
     **/
    public interface OnPhotoResultListener {
        void onPhotoResult(String path);

     }





    /**
     * 从系统相冊中选取照片上传
     * @param activity
     */
    public static void selectPictureFromAlbum(Activity activity){
        if ((ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(activity, "请在应用管理中打开“读写存储”和“相机”访问权限！", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, }, 0);
            return;
        }

        // 调用系统的相冊
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_UNSPECIFIED);

        // 调用剪切功能
        activity.startActivityForResult(intent, PHOTOZOOM);
    }

    /**
     * 从系统相冊中选取照片上传
     * @param fragment
     */
    public static void selectPictureFromAlbum(Fragment fragment){
        // 调用系统的相冊
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);

        // 调用剪切功能
        fragment.startActivityForResult(intent, PHOTOZOOM);
    }

    /**
     * 拍照
     * @param activity
     */
    public static void photograph(Activity activity){


        if ((ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(activity, "请在应用管理中打开“读写存储”和“相机”访问权限！", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, }, 1);

            return;
        }

        imageName = File.separator + getStringToday() + ".jpg";

        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String status = Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED)){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, UriPathUtils.getUri(activity,new File(Environment.getExternalStorageDirectory(), imageName).getAbsolutePath()));
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, UriPathUtils.getUri(activity,new File(activity.getFilesDir(), imageName).getAbsolutePath()));
         }
        activity.startActivityForResult(intent, PHOTOGRAPH);
    }



    /**
     * 图片裁剪
     * @param activity
     * @param uri
     * @param height
     * @param width
     */
    public static void startPhotoZoom(Activity activity,Uri uri,int height,int width) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", height);
        intent.putExtra("outputY", width);
        intent.putExtra("noFaceDetection", true); //关闭人脸检測
        intent.putExtra("return-data", true);//假设设为true则返回bitmap
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//输出文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, PHOTORESOULT);
    }

    /**
     * 图片裁剪
     * @param activity
     * @param uri	  	原图的地址
     * @param height  	指定的剪辑图片的高
     * @param width	  	指定的剪辑图片的宽
     * @param destUri 	剪辑后的图片存放地址
     */
    public static void startPhotoZoom(Activity activity,Uri uri,int height,int width,Uri destUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", height);
        intent.putExtra("outputY", width);
        intent.putExtra("noFaceDetection", true); //关闭人脸检測
        intent.putExtra("return-data", false);//假设设为true则返回bitmap
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 7.0 添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
         }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, destUri);//输出文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, PHOTORESOULT);
    }

    /**
     * 图片裁剪
     * @param fragment
     * @param uri
     * @param height
     * @param width
     */
    public static void startPhotoZoom(Fragment fragment,Uri uri,int height,int width) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", height);
        intent.putExtra("outputY", width);
        intent.putExtra("return-data", true);
        fragment.startActivityForResult(intent, PHOTORESOULT);
    }

    /**
     * 获取当前系统时间并格式化
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 制作图片的路径地址
     * @param context
     * @return
     */
    public static String getPath(Context context){
        String path = null;
        File file = null;
        long tag = System.currentTimeMillis();
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //SDCard是否可用
            path = Environment.getExternalStorageDirectory() + File.separator +"myimages/";
            file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            path = Environment.getExternalStorageDirectory() + File.separator +"myimages/"+ tag + ".png";
        }else{
            path = context.getFilesDir() + File.separator +"myimages/";
            file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            path = context.getFilesDir() + File.separator +"myimages/"+ tag + ".png";
        }
        return path;
    }

    /**
     * 按比例获取bitmap
     * @param path
     * @param w
     * @param h
     * @return
     */
    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture仅仅获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * 获取原图bitmap
     * @param path
     * @return
     */
    public static Bitmap convertToBitmap2(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture仅仅获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        return  BitmapFactory.decodeFile(path, opts);
    }

    /**
     * 返回结果处理
     *
     * @param type 照片裁剪类型
     * @param resultCode
     * @param data
     * @param data
     */
    private static String path;

    public static void  onActivityResult(Activity activity, OnPhotoResultListener onPhotoResultListener ,int type ,int requestCode, int resultCode, Intent data) {


        switch (requestCode) {

            case  NONE:
                 break;
            //选择图片
            case PHOTOGRAPH:
                if (requestCode == PhotoUtil.PHOTOGRAPH) {
                    // 设置文件保存路径这里放在跟文件夹下
                    File picture = null;
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
                        if (!picture.exists()) {
                            picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
                        }
                    } else {
                        picture = new File(activity.getFilesDir() + PhotoUtil.imageName);
                        if (!picture.exists()) {
                            picture = new File(activity.getFilesDir() + PhotoUtil.imageName);
                        }
                    }

                    path = PhotoUtil.getPath(activity);// 生成一个地址用于存放剪辑后的图片
                    if (TextUtils.isEmpty(path)) {
                        Log.e(TAG, "随机生成的用于存放剪辑后的图片的地址失败");
                        return;
                    }
                    Uri imageUri = Uri.fromFile(new File(path));

                    PhotoUtil.startPhotoZoom(activity, UriPathUtils.getUri(activity, picture.getAbsolutePath()), type == TYPE_PHOTO ? PhotoUtil.PICTURE_HEIGHT : PhotoUtil.ID_PICTURE_HEIGHT, type == TYPE_PHOTO ? PhotoUtil.PICTURE_WIDTH : PhotoUtil.ID_PICTURE_WIDTH, imageUri);
                }
                break;

            // 读取相冊缩放图片
            case PHOTOZOOM:
                if (data == null)
                    return;
                path = PhotoUtil.getPath(activity);// 生成一个地址用于存放剪辑后的图片
                if (TextUtils.isEmpty(path)) {
                    Log.e(TAG, "随机生成的用于存放剪辑后的图片的地址失败");
                    return;
                }
                Uri imageUri = Uri.fromFile(new File(path));
                PhotoUtil.startPhotoZoom(activity, data.getData(), type == TYPE_PHOTO ? PhotoUtil.PICTURE_HEIGHT : PhotoUtil.ID_PICTURE_HEIGHT, type == TYPE_PHOTO ? PhotoUtil.PICTURE_WIDTH : PhotoUtil.ID_PICTURE_WIDTH, imageUri);
                break;
            case PHOTORESOULT:
                if (data == null)
                    return;
                /**
                 * 在这里处理剪辑结果。能够获取缩略图，获取剪辑图片的地址。得到这些信息能够选则用于上传图片等等操作
                 * */

                /**
                 * 如。依据path获取剪辑后的图片
                 */
                if (onPhotoResultListener!=null)
                    onPhotoResultListener.onPhotoResult(path);
                break;
        }
    }

}