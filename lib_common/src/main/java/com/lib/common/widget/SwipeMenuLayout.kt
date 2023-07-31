package com.lib.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.Scroller
import kotlin.math.abs

/**
 * 使用案例
 *
 * RecyclerView布局
 * <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvAll"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />
 *
 * 创建Adapter
 * class SwipeAdapter(
        val context: Context,
        val mData: List<WeatherBean>,
        val onClick: (WeatherBean) -> Unit,
        val onDelete: (Int) -> Unit) :
        RecyclerView.Adapter<SwipeAdapter.ViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_swipe, parent, false)
                )
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val item = mData[position]
                holder.tvWeather.text = item.name
                holder.icon.setImageResource(IconUtils.getDayIconDark(context, item.code.toString()))
                holder.itemContent.setOnClickListener {
                onClick(item)
            }

            holder.tvDelete.setOnClickListener {
                onDelete(holder.adapterPosition)
                }
            }

            override fun getItemCount(): Int {
                return mData.size
            }

            class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val tvWeather = itemView.findViewById<TextView>(R.id.tvWeather)
                val itemContent = itemView.findViewById<ViewGroup>(R.id.itemContent)
                val icon = itemView.findViewById<ImageView>(R.id.icon)
                val tvDelete = itemView.findViewById<TextView>(R.id.tvDelete)
            }
        }
 *
 * 初始化Adapter
  adapter = SwipeAdapter(this, mData, { }) {
        mData.removeAt(it)
        adapter.notifyItemRemoved(it)
    }
   mBinding.rvAll.adapter = adapter
 *
 * adapter item布局
 * <com.lib.common.widget.SwipeMenuLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:id="@+id/itemContent"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="?android:attr/selectableItemBackground"
            android:paddingLeft="12dp"
            android:paddingTop="8dp"
            android:paddingRight="12dp">

        <ImageView
            android:id="@+id/icon"
            android:layout_width="45dp"
            android:layout_height="45dp"
            android:layout_marginBottom="8dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tvWeather"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="12dp"
            android:textSize="16sp"
            app:layout_constraintBottom_toBottomOf="@+id/icon"
            app:layout_constraintLeft_toRightOf="@+id/icon"
            app:layout_constraintTop_toTopOf="@+id/icon"
            tools:text="Sunny" />

        <View
            android:layout_width="match_parent"
            android:layout_height="1px"
            android:background="#cccccc"
            app:layout_constraintBottom_toBottomOf="parent" />

        </androidx.constraintlayout.widget.ConstraintLayout>

        <TextView
            android:id="@+id/rvMenu"
            android:layout_width="80dp"
            android:layout_height="match_parent"
            android:background="#FF0000"
            android:gravity="center"
            android:paddingStart="16dp"
            android:paddingEnd="16dp"
            android:text="menu"
            android:textColor="#FFFFFFFF"
            android:textSize="14sp" />

        <TextView
            android:id="@+id/tvDelete"
            android:layout_width="80dp"
            android:layout_height="match_parent"
            android:background="#FFF55030"
            android:gravity="center"
            android:paddingStart="16dp"
            android:paddingEnd="16dp"
            android:text="delete"
            android:textColor="#FFFFFFFF"
            android:textSize="14sp" />

    </com.lib.common.widget.swipemenu.SwipeMenuLayout>
 */

class SwipeMenuLayout(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0) : ViewGroup(context!!, attrs, defStyle) {

    private var mTouchSlop = 0

    // content部分高度，也是整个itemview的高度
    private var mHeight = 0

    // 右侧菜单宽度总和(最大滑动距离)
    private var mMenuViewWidth = 0

    private var mScroller: Scroller? = null

    // 滑动速度变量
    private var mVelocityTracker: VelocityTracker? = null

    private val mFirstP = PointF()


    // 滑动过程中记录上次触碰点X
    private var mLastX = 0f

    companion object {
        // 最小滑动速度
        private const val SNAP_VELOCITY = 600

        // 存储的是当前正在展开的View
        private var mViewCache: SwipeMenuLayout? = null
    }


    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        //初始化滑动帮助类对象
        mScroller = Scroller(context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 由于ViewHolder的复用机制，每次这里要手动恢复初始值
        mMenuViewWidth = 0
        mHeight = 0

        // 适配GridLayoutManager，将以第一个子Item(即ContentItem)的宽度为控件宽度
        var contentWidth = 0

        val childCount = childCount

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility != GONE) {
                if (i == 0) {
                    // 测量ContentView
                    measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0)
                    contentWidth = childView.measuredWidth
                    mHeight = mHeight.coerceAtLeast(childView.measuredHeight)
                } else {
                    // 测量menu
                    val layoutParams = childView.layoutParams
                    val widthSpec = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY)
                    val heightSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY)
                    childView.measure(widthSpec, heightSpec)
                    mMenuViewWidth += childView.measuredWidth
                }
            }
        }
        //宽度取第一个Item(Content)的宽度
        setMeasuredDimension(
            paddingLeft + paddingRight + contentWidth,
            mHeight + paddingTop + paddingBottom
        )
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val childCount = childCount
        var left = paddingLeft
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility != GONE) {
                childView.layout(left, paddingTop, left + childView.measuredWidth, paddingTop + childView.measuredHeight)
                left += childView.measuredWidth
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        obtainVelocity(ev)
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                // 如果scroller还没有滑动结束 停止滑动动画
                if (!mScroller!!.isFinished) {
                    mScroller!!.abortAnimation()
                }
                mLastX = ev.x
                mFirstP[ev.x] = ev.y
                //如果down，view和cacheview不一样，则立马让它还原。且把它置为null
                if (mViewCache != null) {
                    if (mViewCache !== this) {
                        mViewCache!!.smoothClose()
                        return true
                    }
                    // 只要有一个侧滑菜单处于打开状态， 就不给外层布局上下滑动了
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                parent.requestDisallowInterceptTouchEvent(true)
                mVelocityTracker!!.computeCurrentVelocity(1000)
                val xVelocity = mVelocityTracker!!.xVelocity
                val yVelocity = mVelocityTracker!!.yVelocity
                val x = ev.x
                val y = ev.y
                if (abs(xVelocity) > SNAP_VELOCITY && abs(xVelocity) > abs(yVelocity)
                    || abs(x - mFirstP.x) >= mTouchSlop
                    && abs(x - mFirstP.x) > abs(y - mFirstP.y)
                ) {
                    return true
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
                releaseVelocity()
            }
            MotionEvent.ACTION_UP ->
                // 点击区域在展开的Itemview的contentView区域，则关闭Menu
                if (this === mViewCache) {
                    if (ev.x < width - scrollX) {
                        smoothClose()
                        return true //true表示拦截
                    }
                }
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val x: Float = ev!!.x
        obtainVelocity(ev)
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                val dx = mLastX - x
                if (scrollX + dx > 0 && scrollX + dx < mMenuViewWidth) {
                    scrollBy(dx.toInt(), 0)
                }
                mLastX = x
            }
            MotionEvent.ACTION_UP -> {
                mVelocityTracker!!.computeCurrentVelocity(1000)
                val scrollX = scrollX
                when {
                    // 向左侧滑达到侧滑最低速度，则打开
                    mVelocityTracker!!.xVelocity < -SNAP_VELOCITY -> {
                        val t = (abs(mMenuViewWidth - scrollX) / mVelocityTracker!!.xVelocity * 1000).toInt()
                        smoothExpand(t)
                    }
                    // 向右侧滑达到侧滑最低速度，则关闭
                    mVelocityTracker!!.xVelocity >= SNAP_VELOCITY -> {
                        smoothClose()
                    }
                    // 如果超过删除按钮一半，则打开
                    scrollX >= mMenuViewWidth / 2 -> {
                        smoothExpand(100)
                    }
                    // 其他情况则关闭
                    else -> {
                        smoothClose()
                    }
                }
                releaseVelocity()
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun smoothExpand(time: Int) {
        // 展开就加入ViewCache：
        mViewCache = this@SwipeMenuLayout
        mScroller!!.startScroll(scrollX, 0, mMenuViewWidth - scrollX, 0, time)
        invalidate()
    }


    /**
     * 平滑关闭
     */
    private fun smoothClose() {
        mViewCache = null
        mScroller!!.startScroll(scrollX, 0, -scrollX, 0, 100)
        invalidate()
    }

    //平滑滚动 弃用 改属性动画实现
    override fun computeScroll() {
        //判断Scroller是否执行完毕：
        if (mScroller!!.computeScrollOffset()) {
            scrollTo(mScroller!!.currX, mScroller!!.currY)
            //通知View重绘-invalidate()->onDraw()->computeScroll()
            invalidate()
        }
    }

    /**
     * @param event 向VelocityTracker添加MotionEvent
     * @see VelocityTracker.obtain
     * @see VelocityTracker.addMovement
     */
    private fun obtainVelocity(event: MotionEvent?) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker!!.addMovement(event)
    }

    /**
     * * 释放VelocityTracker
     *
     * @see VelocityTracker.clear
     * @see VelocityTracker.recycle
     */
    private fun releaseVelocity() {
        if (null != mVelocityTracker) {
            mVelocityTracker!!.clear()
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    //每次ViewDetach的时候，判断一下 ViewCache是不是自己，如果是自己，关闭侧滑菜单，且ViewCache设置为null，
    // 理由：1 防止内存泄漏(ViewCache是一个静态变量)
    // 2 侧滑删除后自己后，这个View被Recycler回收，复用，下一个进入屏幕的View的状态应该是普通状态，而不是展开状态。
    override fun onDetachedFromWindow() {
        if (this === mViewCache) {
            mViewCache!!.smoothClose()
            mViewCache = null
        }
        super.onDetachedFromWindow()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        // measureChildWithMargins()需要用到
        return MarginLayoutParams(context, attrs)
    }

}