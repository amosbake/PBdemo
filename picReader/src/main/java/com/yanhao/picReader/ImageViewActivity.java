package com.yanhao.picReader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import com.yanhao.picReader.adapter.ImageAdapter;
import com.yanhao.picReader.adapter.ImagerPageAdapter;
import com.yanhao.picReader.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;


public class ImageViewActivity extends Activity {
    private static final String TAG="ImageViewActivity";
//    private ViewPager mViewPager;
//    private ArrayList<ImageView> images;
//    private ArrayList<File> pics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grallery_view);
//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        int screenWidth=getWindowManager().getDefaultDisplay().getWidth();
//        int screenHeight=getWindowManager().getDefaultDisplay().getHeight();
//        Intent intent=getIntent();
//        Bundle bundle=intent.getExtras();
//        Set<String> keySet=bundle.keySet();
//        for (String key:keySet){
//            pics.add(new File(bundle.getString(key)));
//            Log.i(TAG,bundle.getString(key));
//        }
//        for (File f:pics){
//            ImageView imageView=new ImageView(this);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
//            images.add(imageView);
//        }
//        ViewPagerAdapter adapter=new ViewPagerAdapter();
//        mViewPager.setAdapter(adapter);
    }
//    private class ViewPagerAdapter extends PagerAdapter{
//
//        @Override
//        public int getCount() {
//            return images.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view==object;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(images.get(position));
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            container.addView(images.get(position),0);
//            return images.get(position);
//        }
//    }

}
