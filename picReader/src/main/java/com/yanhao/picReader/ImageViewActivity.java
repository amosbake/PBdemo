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
import android.widget.ImageButton;
import android.widget.ImageView;
import com.yanhao.picReader.adapter.FullSrceenImageAdapter;
import com.yanhao.picReader.adapter.ImageAdapter;
import com.yanhao.picReader.adapter.ImagerPageAdapter;
import com.yanhao.picReader.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ImageViewActivity extends Activity implements View.OnClickListener {
    private static final String TAG="ImageViewActivity";
    private Gallery mGallery;
    private ImageButton previous, next, deletePic, picDetails, more;
//    private ViewPager mViewPager;
//    private ArrayList<ImageView> images;
//    private ArrayList<File> pics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grallery_view);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Set<String> keySet = bundle.keySet();
        List<File> pics = new ArrayList<File>();
        for (String key : keySet) {
            File file = new File(bundle.getString(key));
            pics.add(file);
            Log.i(TAG, file.getName());
        }
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenheight = getWindowManager().getDefaultDisplay().getHeight();
        FullSrceenImageAdapter adapter = new FullSrceenImageAdapter(this, screenWidth, screenheight);
        adapter.setData(pics);
        mGallery.setAdapter(adapter);
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

    private void initView() {
        mGallery = (Gallery) findViewById(R.id.id_gallery);
        previous = (ImageButton) findViewById(R.id.id_previous);
        next = (ImageButton) findViewById(R.id.id_next);
        deletePic = (ImageButton) findViewById(R.id.id_ib_delete);
        picDetails = (ImageButton) findViewById(R.id.id_details);
        more = (ImageButton) findViewById(R.id.id_more);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        deletePic.setOnClickListener(this);
        picDetails.setOnClickListener(this);
        more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_previous:
                getPrevious();
                break;
            case R.id.id_next:
                getNext();
                break;
            case R.id.id_ib_delete:
                delete();
                break;
            case R.id.id_details:
                showDetails();
                break;
            case R.id.id_more:
                getMore();
                break;
        }

    }

    /**
     * 获取更多功能
     */
    private void getMore() {

    }

    /**
     * 显示当前图片的详细信息
     */
    private void showDetails() {

    }

    /**
     * 删除当前显示图片
     */
    private void delete() {

    }

    private void getNext() {

    }

    private void getPrevious() {

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
