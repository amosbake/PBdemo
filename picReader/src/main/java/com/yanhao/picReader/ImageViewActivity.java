package com.yanhao.picReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.yanhao.picReader.adapter.FullSrceenImageAdapter;
import com.yanhao.picReader.adapter.ImageAdapter;
import com.yanhao.picReader.adapter.ImagerPageAdapter;
import com.yanhao.picReader.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ImageViewActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG="ImageViewActivity";
    private static final int HIDE_MENU = 1;
    private Gallery mGallery;
    private ImageButton previous, next, deletePic, picDetails, more;
    private RelativeLayout headView, buttomView;
    private Handler handler;
    private List<File> pics;
    private int currentPosition;
    private FullSrceenImageAdapter adapter;
//    private ViewPager mViewPager;
//    private ArrayList<ImageView> images;
//    private ArrayList<File> pics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grallery_view);
        initView();
        //获取前一个页面的选取图片信息(文件路径)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Set<String> keySet = bundle.keySet();
        pics = new ArrayList<File>();
        for (String key : keySet) {
            File file = new File(bundle.getString(key));
            pics.add(file);
        }
        //获取屏幕大小
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenheight = getWindowManager().getDefaultDisplay().getHeight();
        //根据屏幕大小调整图片
        adapter = new FullSrceenImageAdapter(this, screenWidth, screenheight - (80));
        adapter.setData(pics);
        //全屏显示图片
        mGallery.setAdapter(adapter);
        mGallery.setOnItemClickListener(this);
        currentPosition = 0;
        mGallery.setSelection(currentPosition);
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

    /**
     * 初始化控件
     */
    private void initView() {
        mGallery = (Gallery) findViewById(R.id.id_gallery);
        headView = (RelativeLayout) findViewById(R.id.id_head_view);
        buttomView = (RelativeLayout) findViewById(R.id.id_bottom_view);
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
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HIDE_MENU:
                        headView.setVisibility(View.GONE);
                        buttomView.setVisibility(View.GONE);
                        break;
                }
                super.handleMessage(msg);
            }
        };

    }

    /**
     * 点击图片显示菜单栏
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        headView.setVisibility(View.VISIBLE);
        buttomView.setVisibility(View.VISIBLE);
        Message msg = handler.obtainMessage(HIDE_MENU);
        handler.sendMessageDelayed(msg, 5000);
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
        Log.i(TAG, "getMore");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[]{getResources().getString(R.string.more_dialog_go), getResources().getString(R.string.detail_dialog_cancel)}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        changeActivity();
                        dialog.dismiss();
                        break;
                    case 1:
                        dialog.dismiss();
                        break;
                }
            }
        }).show();
    }

    /**
     * 显示当前图片的详细信息
     */
    private void showDetails() {
        Log.i(TAG, "showDetails");
        currentPosition = mGallery.getSelectedItemPosition();
        File file = pics.get(currentPosition);
        if (file != null) {
            String title = String.format(getResources().getString(R.string.name), file.getName());
            String path = String.format(getResources().getString(R.string.path), file.getAbsoluteFile());
            String lastModified = String.format(getResources().getString(R.string.time), FileUtil.getFileModifiedTime(file));
            String size = String.format(getResources().getString(R.string.size), FileUtil.getFileSize(file));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.details))
                    .setItems(new String[]{title, path, lastModified, size}, null)
                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    /**
     * 删除当前显示图片
     */
    private void delete() {
        Log.i(TAG, "delete");
        currentPosition = mGallery.getSelectedItemPosition();
        File file = pics.get(currentPosition);
        file.delete();
        pics.remove(currentPosition);
        adapter.setData(pics);
        adapter.notifyDataSetChanged();
    }

    /**
     * 到下一张图片
     */
    private void getNext() {
        Log.i(TAG, "getNext");
        currentPosition = mGallery.getSelectedItemPosition();
        if (currentPosition < pics.size() - 1) {
            mGallery.setSelection(currentPosition + 1);
        } else {
            Toast.makeText(this, "已经是最后一张图片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 到前一张图片
     */
    private void getPrevious() {
        Log.i(TAG, "getPrevious");
        currentPosition = mGallery.getSelectedItemPosition();
        if (currentPosition > 0) {
            mGallery.setSelection(currentPosition - 1);
        } else {
            Toast.makeText(this, "已经是第一张图片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 放回前一个页面
     */
    private void changeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
