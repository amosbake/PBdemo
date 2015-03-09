package com.yanhao.picReader.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yons on 2015/3/9.
 */
public class ImagerPageAdapter extends PagerAdapter {
    private List<String> mData;
    public void setData(List<String> datas){
        this.mData=datas;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
