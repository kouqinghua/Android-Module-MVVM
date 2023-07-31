package com.ktx.lib.component;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView 间距
 * 作者: 寇清华
 * 时间: 2020-03-09 17:51
 * 邮箱:252706952@qq.com
 */
public class RecyclerDecoration extends RecyclerView.ItemDecoration {

    private int t;
    private int l;
    private int b;
    private int r;

    public RecyclerDecoration(int t, int l, int b, int r) {
        this.t = t;
        this.l = l;
        this.b = b;
        this.r = r;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = t;
        outRect.left = l;
        outRect.right = r;
        outRect.bottom = b;
    }
}
