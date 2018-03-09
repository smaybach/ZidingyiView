package com.example.zidingyiview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by 家 on 2018/3/3.
 */

public class NeworkUtils {
    private String url;
    private ImageView imageView;
    private final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kson/";
    //实例化hashmap对象
private HashMap<String ,SoftReference<Bitmap>> hashMap=new HashMap<>();
    public NeworkUtils(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }
    public void execute(){
        new BitmapTask().execute(url);
    }

    class BitmapTask extends AsyncTask<String ,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                if (200==connection.getResponseCode()){
                    InputStream inputStream = connection.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds=true;
                    BitmapFactory.decodeStream(inputStream);
                    options.inSampleSize=4;
                    options.inJustDecodeBounds=false;
                    Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPreExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            memory(url,bitmap);
            diskMemory(url,bitmap);
        }
    }
    public void diskMemory(String url,Bitmap bitmap){
        try {
            File dir = new File(SDCARD_PATH);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File file = new File(SDCARD_PATH + new Md5FileNameGenerator().generate(url));
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Bitmap getDiskMemory(String url){
        File file=new File(SDCARD_PATH+new Md5FileNameGenerator().generate(url));
        if (file!=null && file.exists()){
            try {
                FileInputStream inputStream = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }}
        return null;
    }
    public void memory(String url,Bitmap bitmap){
        SoftReference<Bitmap> bmp=new SoftReference<Bitmap>(bitmap);
        hashMap.put(new Md5FileNameGenerator().generate(url),bmp);
    }
    public Bitmap getMemory(String url){
        Bitmap bitmap=null;
        SoftReference<Bitmap> bitmapSoftReference=hashMap.get(new Md5FileNameGenerator().generate(url));
        if (bitmapSoftReference!=null){
            bitmap=bitmapSoftReference.get();
        }
        return bitmap;
    }
}
