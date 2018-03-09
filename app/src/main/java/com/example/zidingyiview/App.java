package com.example.zidingyiview;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by 家 on 2018/3/3.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    private void init() {
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(this)
                .writeDebugLogs()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(build);
    }
}
