package com.func.box.tool

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class TopItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    //间隔高度
    private val mHeight = 100

    //矩形画笔
    private val mPaint: Paint = Paint()

    //标签画笔
    private val mTextPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mContext: Context = context
    private val mRound: Rect = Rect()

    init {
        mPaint.apply {
            color = Color.parseColor("#F1F1F1")
        }

        mTextPaint.apply {
            textSize = 40f
            color = ContextCompat.getColor(mContext, com.ktx.lib.R.color.colorAccent)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val bottom = childView.top.toFloat()
            val top = bottom - mHeight
            //回执矩形间隔
            c.drawRect(left, top, right, bottom, mPaint)
            //根据位置获取当前item的标签
            val tag = typeListener(parent.getChildAdapterPosition(childView))
            mTextPaint.getTextBounds(tag, 0, tag.length, mRound)
            c.drawText(tag, left + mTextPaint.textSize, bottom - mHeight / 2 + mRound.height() / 2, mTextPaint)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()
        val manager = parent.layoutManager as LinearLayoutManager
        //第一个可见item位置
        val index = manager.findFirstVisibleItemPosition()

        if (index != -1) {
            val childView = parent.findViewHolderForLayoutPosition(index)!!.itemView
            val top = parent.paddingTop.toFloat()
            val tag = typeListener(index)
            var bottom = parent.paddingTop + mHeight.toFloat()
            //悬浮顶部判断, 其实也就是一直在绘制一个矩形加文本内容
            //上滑时取值bottom, 下滑时取值childView.bottom.toFloat()
            bottom = min(childView.bottom.toFloat(), bottom)
            c.drawRect(0f, top, right, bottom, mPaint)
            mTextPaint.getTextBounds(tag, 0, tag.length, mRound)
            c.drawText(tag, left + mTextPaint.textSize, bottom - mHeight / 2 + mRound.height() / 2, mTextPaint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = mHeight
    }

    //获取悬停标签
    lateinit var typeListener: (Int) -> String


    //    val top = TopItemDecoration(mContext).apply {
    //                typeListener = {
    //                    mViewModel.getMenu()[it].type
    //                }
    //            }
    //    recyclerview.addItemDecoration(top)
}