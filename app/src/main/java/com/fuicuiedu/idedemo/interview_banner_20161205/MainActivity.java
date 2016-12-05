package com.fuicuiedu.idedemo.interview_banner_20161205;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


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

    //更新小点
    private int oldPosition = 0;
    private int currentItem;

    //线程池，用来定时轮播
    private ScheduledExecutorService mSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启一个单个后台线程
        mSchedule = Executors.newSingleThreadScheduledExecutor();
        //给线程添加一个定时的调度任务
        //任务，时间（延迟多少时间后执行任务），时间（按照这个时间周期性重复执行任务）, TimeUnit.SECONDS
        mSchedule.scheduleWithFixedDelay(
                new ViewPagerTask(),2,2, TimeUnit.SECONDS
        );
    }

    private class ViewPagerTask implements Runnable{
        @Override
        public void run() {
            //用取余的方式来确认currentItem
            currentItem = (currentItem + 1) % imageIds.length;
            mhandler.sendEmptyMessage(0);//就是为了调动下handler，为了更新UI
        }
    }

    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //更新viewpager当前显示的pager
            mViewPager.setCurrentItem(currentItem);
        }
    };

    //初始化视图
    private void init(){
        mViewPager = (ViewPager) findViewById(R.id.vp);

        //显示图片的集合
        images = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }

        //显示小点点的集合
        dots = new ArrayList<>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        //显示图片标题
        title = (TextView) findViewById(R.id.tv);
        //刚进来显示第一个标题
        title.setText(titles[0]);

        mAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //标题的改变
                title.setText(titles[position]);
                //小点的改变
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);

                oldPosition = position;
                currentItem = position;//做轮播的时候会用到
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
