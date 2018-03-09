package com.example.zidingyiview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MemoryActivity extends AppCompatActivity {
    private String url = "https://img6.bdstatic.com/img/image/xiaolu/wujingzhanlang2.jpg";
    private ImageView iv;
    private NeworkUtils networkUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        initView();
        networkUtils = new NeworkUtils(url, iv);
        if (networkUtils.getMemory(url) != null) {//首先加载内存缓存
            iv.setImageBitmap(networkUtils.getDiskMemory(url));
        } else if (networkUtils.getDiskMemory(url) != null) {//其次加载磁盘缓存
            iv.setImageBitmap(networkUtils.getDiskMemory(url));
        } else {//最后加载网络
            networkUtils.execute();
        }
    }
    private void initView() {
        iv = (ImageView) findViewById(R.id.iv);
    }
}
