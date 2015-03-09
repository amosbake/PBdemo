package com.yanhao.picReader.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.yanhao.picReader.R;
import com.yanhao.picReader.utils.FileUtil;

import java.io.File;
import java.util.List;

/**
 * Created by yons on 2015/3/9.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<File> images;
    private LayoutInflater mInflater;
    private int[] imgState;
    private boolean isSelect;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater=LayoutInflater.from(mContext);
    }
    public void setData(List<File> images){
        this.images=images;
    }
    public void setData(List<File> images,int [] imgState,boolean isSelect){
        this.images=images;
        this.imgState=imgState;
        this.isSelect=isSelect;
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
        ViewHolder holder=new ViewHolder();
//        if (convertView==null){
            convertView= mInflater.inflate(R.layout.image_item,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.id_image_item);
            holder.select= (ImageView) convertView.findViewById(R.id.id_iv_checkbox);
//            convertView.setTag(holder);
//        }else{
//            holder= (ViewHolder) convertView.getTag();
//        }

        holder.imageView.setImageBitmap(FileUtil.decodeSampleBitmapFromFile(images.get(position),60,60));
        if(isSelect){
            if (1==imgState[position]){
                holder.select.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }
    class ViewHolder{
        private ImageView imageView;
        private ImageView select;
    }
}
