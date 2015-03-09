package com.yanhao.picReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.yanhao.picReader.adapter.ImageAdapter;
import com.yanhao.picReader.utils.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemLongClickListener ,AdapterView.OnItemClickListener{
    private LinearLayout headLayout;
    private LinearLayout buttom;
    private GridView gridView;
    private ImageButton deletePic;
    private ImageButton moreOperation;
    private ImageButton details;
    private ImageButton back;
    private TextView selectInfo;
    private TextView sdPicInfo;
    private int[] imgState;
    private boolean isSelect;
    private ImageAdapter adapter;
    private List<File> pics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pics = FileUtil.getPicFiles(FileUtil.getRootDirectory());
        initState(pics.size());
        gridView = (GridView) findViewById(R.id.id_gridview);
        adapter = new ImageAdapter(this);
        adapter.setData(pics);
        gridView.setAdapter(adapter);
        gridView.setOnItemLongClickListener(this);
        gridView.setOnItemClickListener(this);
        headLayout = (LinearLayout) findViewById(R.id.id_layout_headmenu);
        buttom = (LinearLayout) findViewById(R.id.id_layout_bottom);
        initButtons();
        selectInfo= (TextView) findViewById(R.id.id_headtext);
        sdPicInfo= (TextView) findViewById(R.id.id_img_count);
        String imgCount=String.format(getResources().getString(R.string.sd_pic_num),pics.size());
        sdPicInfo.setText(imgCount);
        Log.i("error", imgCount);

    }

    @Override
    protected void onResume() {
        if (pics!=null&&pics.size()>0){
            resetData();
        }
        super.onResume();
    }


    /**
     * 初始化按键,添加按键的点击事件
     */
    private void initButtons() {
        details= (ImageButton) findViewById(R.id.id_ib_details_menu);
        deletePic= (ImageButton) findViewById(R.id.id_ib_delete_menu);
        moreOperation= (ImageButton) findViewById(R.id.id_ib_more_menu);
        back= (ImageButton) findViewById(R.id.id_ib_back);
        details.setOnClickListener(this);
        deletePic.setOnClickListener(this);
        moreOperation.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initState(int len) {
        imgState = new int[len];
        for(int i=0;i<imgState.length;i++){
            imgState[i]=0;
        }
    }

    /**
     * 确定有多少图片被选中
     * @return
     */
    private int selectCount(){
        int count=0;
        for (int i:imgState){
            if (1==i){
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        headLayout.setVisibility(View.VISIBLE);
        buttom.setVisibility(View.VISIBLE);
        imgState[position]=1;
        isSelect=true;
        adapter.setData(pics,imgState,isSelect);
        adapter.notifyDataSetChanged();
        int count=selectCount();
        String selected=String.format(getResources().getString(R.string.selected),count);
        Log.i("error",selected);
        selectInfo.setText(selected);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isSelect){
            imgState[position]=1;
            adapter.setData(pics,imgState,isSelect);
            adapter.notifyDataSetChanged();
            int count=selectCount();
            if(count>1){
                details.setVisibility(View.GONE);
            }
        }else{
            Intent intent=new Intent(this,ImageViewActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("0",pics.get(position).getAbsolutePath());
            intent.putExtras(bundle);
            startActivity(intent);
            this.finish();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_ib_delete_menu:
                delete();
                break;
            case R.id.id_ib_more_menu:
                showMore();
                break;
            case R.id.id_ib_details_menu:
                showDetails();
                break;
            case R.id.id_ib_back:
                resetData();
                break;
        }
    }

    /**
     * 显示更多功能
     */
    private void showMore() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(new String[]{getResources().getString(R.string.more_dialog_back),getResources().getString(R.string.more_dialog_go)},new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        dialog.dismiss();
                        resetData();
                        break;
                    case 1:
                        changeActivity();
                        dialog.dismiss();
                        break;
                }
            }
        }).show();
    }

    /**
     * 将选中文件信息传递给显示页面
     */
    private void changeActivity() {
        if (isSelect){
            Bundle bundle=new Bundle();
            for(int i=0;i<imgState.length;i++) {
                if (1 == imgState[i]) {
                    String filePath = pics.get(i).getAbsolutePath();
                    bundle.putString(i + "", filePath);
                }
            }
            Intent intent=new Intent(this,ImageViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(this,"请选择照片",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示图片详情
     */
    private void showDetails() {
        File file=null;
        if (isSelect) {
            for (int i = 0; i < imgState.length; i++) {
                if (1 == imgState[i]) {
                   file = pics.get(i);
                }
            }
        }
        if (file!=null){
            String title=String.format(getResources().getString(R.string.name),file.getName());
            String path=String.format(getResources().getString(R.string.path),file.getAbsoluteFile());
            String lastModified=String.format(getResources().getString(R.string.time),FileUtil.getFileModifiedTime(file));
            String size=String.format(getResources().getString(R.string.size),FileUtil.getFileSize(file));
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.details))
                    .setItems(new String[]{title,path,lastModified,size},null)
                    .setNegativeButton("返回",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        resetData();
    }

    /**
     * 删除选中图片
     */
    private void delete() {
        if (isSelect){
            for (int i=0;i<imgState.length;i++){
                if(1==imgState[i]){
                    File file=pics.get(i);
                    file.delete();
                    Log.i("error","delete");
                }
            }
           resetData();
        }
    }

    /**
     * 把页面重置到初始状态
     */
    private void resetData(){
        pics.clear();
        pics=FileUtil.getPicFiles(FileUtil.getRootDirectory());
        imgState=new int[pics.size()];
        for (int i=0;i<imgState.length;i++){
            imgState[i]=0;
        }
        isSelect=false;
        adapter.setData(pics,imgState,isSelect);
        String imgCount=String.format(getResources().getString(R.string.sd_pic_num),pics.size());
        sdPicInfo.setText(imgCount);
        adapter.notifyDataSetChanged();
        headLayout.setVisibility(View.GONE);
        buttom.setVisibility(View.GONE);
    }
}
