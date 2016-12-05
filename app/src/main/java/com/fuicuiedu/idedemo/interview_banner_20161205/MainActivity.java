package com.fuicuiedu.idedemo.interview_banner_20161205;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


//将轮播的图片，和文本放在不同集合中
//应用启动的时候默认显示一组图片和文本
//需要定时器，目的：每隔一段时间替换掉显示的图片和文本
//同时，实现手指滑动图片达到轮播的效果

// TODO: 2016/12/5 0005 https://github.com/wxcican/InterView_Banner_20161205.git 

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private List<ImageView> images;//存放图片view的集合
    private List<View> dots;//存放小点点的集合
    private TextView title;//图片描述信息

    //存放图片描述信息
    private String[] titles = new String[]{
            "丁凡",
            "李佳佳",
            "程自丽",
            "郭力",
            "风语"
    };

    //存放图片id
    private int[] imageIds = new int[]{
            R.drawable.aa,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    private class ViewPagerAdapter extends PagerAdapter {

        //获取当前窗体界面数量
        @Override
        public int getCount() {
            return images.size();
        }

        //用于判断界面是否有对象生成
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //return一个对象，这个对象表明了PagerAdapter选择哪个对象放入当前的ViewPager中
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images.get(position));
            return images.get(position);
        }

        //是从viewgroup中移除当前的View
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }
    }
}
