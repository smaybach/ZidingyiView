package com.example.zidingyiview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Banner banner;
    private List<String> urls;
    private String url = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=511209731,3971680570&fm=26&gp=0.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        banner = (Banner) findViewById(R.id.banner);
        urls = new ArrayList<>();
        urls.add("https://www.zhaoapi.cn/images/quarter/ad1.png");
        urls.add("https://www.zhaoapi.cn/images/quarter/ad2.png");
        urls.add("https://www.zhaoapi.cn/images/quarter/ad3.png");
        urls.add("https://www.zhaoapi.cn/images/quarter/ad4.png");
        //解耦
        banner.loadData(urls).display();//构建者模式返回对象本身
        banner.setBannerClicklistener(new Banner.BannerClicklistener() {
            @Override
            public void onClickListener(int pos) {
                Toast.makeText(MainActivity.this, "pos====" + pos, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
