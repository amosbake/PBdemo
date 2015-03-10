package com.yanhao.picReader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import com.yanhao.picReader.utils.FileUtil;

import java.io.File;
import java.util.List;

/**
 * Created by yons on 2015/3/10.
 */
public class FullSrceenImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<File> images;
    private LayoutInflater mInflater;
    private int reqWidth;
    private int reqHeight;

    public FullSrceenImageAdapter(Context mContext, int reqWidth, int reqHeight) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
    }

    public void setData(List<File> pics) {
        this.images = pics;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(FileUtil.decodeSampleBitmapFromFile(images.get(position), reqWidth, reqHeight));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new Gallery.LayoutParams(reqWidth, reqHeight));
        return imageView;
    }
}
