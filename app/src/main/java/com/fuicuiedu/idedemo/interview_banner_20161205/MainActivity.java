package com.fuicuiedu.idedemo.interview_banner_20161205;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


//将轮播的图片，和文本放在不同集合中
//应用启动的时候默认显示一组图片和文本
//需要定时器，目的：每隔一段时间替换掉显示的图片和文本
//同时，实现手指滑动图片达到轮播的效果

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
