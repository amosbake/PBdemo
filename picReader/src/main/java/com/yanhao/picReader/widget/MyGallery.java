package com.yanhao.picReader.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * Created by yons on 2015/3/10.
 */
public class MyGallery extends Gallery {


    public MyGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 一次只显示一张图片
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
