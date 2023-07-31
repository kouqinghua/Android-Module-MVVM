package com.ktx.lib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.ktx.lib.R;


/**
 * 自定义圆形图像类
 */

public class CircleImageView extends ImageView {

    private static Xfermode MASK_XFERMODE = null;
    private Bitmap mask;
    private Paint paint;
    private int mBorderWidth = 1;
    private int mBorderColor = Color.parseColor("#f2f2f2");

    static {
        PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
        MASK_XFERMODE = new PorterDuffXfermode(localMode);
    }

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            return;
        }

        @SuppressLint("CustomViewStyleable")
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularImage);
        mBorderColor = a.getColor(R.styleable.CircularImage_border_color, mBorderColor);
        final int d = (int) (2 * context.getResources().getDisplayMetrics().density + 0.5f);
        mBorderWidth = a.getDimensionPixelOffset(R.styleable.CircularImage_border_width, d);
        a.recycle();
    }

    private boolean useDefaultStyle = false;

    public void setUseDefaultStyle(boolean useDefaultStyle) {
        this.useDefaultStyle = useDefaultStyle;
    }

    public void setBorderWidth(int width){
        this.mBorderWidth = width;
    }

    public void setmBorderColor(int color){
        this.mBorderColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (useDefaultStyle) {
            super.onDraw(canvas);
            return;
        }
        if (isInEditMode()) {
            return;
        }

        final Drawable localDrawable = getDrawable();

        if (localDrawable == null)
            return;
        if (localDrawable instanceof NinePatchDrawable) {
            return;
        }
        if (this.paint == null) {
            final Paint localPaint = new Paint();
            localPaint.setFilterBitmap(false);
            localPaint.setAntiAlias(true);
            localPaint.setXfermode(MASK_XFERMODE);
            this.paint = localPaint;
        }
        final int width = getWidth();
        final int height = getHeight();
        //保存layer
        @SuppressLint("WrongConstant")
        int layer = canvas.saveLayer(0.0F, 0.0F, width, height, null, 31);
        //设置drawable的大小
        localDrawable.setBounds(0, 0, width, height);
        //将drawable绑定到bitmap(this.mask)上面（drawable只能通过bitmap显示出来）
        localDrawable.draw(canvas);
        if ((this.mask == null) || (this.mask.isRecycled())) {
            //获取一个bitmap，目的是用来承载drawable;
            this.mask = createOvalBitmap(width, height);
        }
        // 将bitmap画到canvas上面
        canvas.drawBitmap(this.mask, 0.0F, 0.0F, this.paint);
        // 将画布复制到layer上
        canvas.restoreToCount(layer);
        //绘制最外面的边框
        drawBorder(canvas, width, height);
    }

    /**
     * 绘制最外面的边框
     *
     * @param canvas canvas
     * @param width  宽
     * @param height 高
     */
    private void drawBorder(Canvas canvas, final int width, final int height) {
        if (mBorderWidth == 0) {
            return;
        }
        final Paint mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        /**
         * 坐标x：view宽度的一般 坐标y：view高度的一般 半径r：因为是view的宽度-border的一半
         */
        canvas.drawCircle(width >> 1, height >> 1, (width - mBorderWidth) >> 1,
                mBorderPaint);
        canvas = null;
    }

    /**
     * 获取一个bitmap，目的是用来承载drawable;
     * 将这个bitmap放在canvas上面承载，并在其上面画一个椭圆(其实也是一个圆，因为width=height)来固定显示区域
     *
     * @param width  宽
     * @param height 高
     * @return res
     */
    public Bitmap createOvalBitmap(final int width, final int height) {
        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
        Bitmap localBitmap = Bitmap.createBitmap(width, height, localConfig);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        final int padding = (mBorderWidth - 3) > 0 ? mBorderWidth - 3 : 1;
        /**
         * 设置椭圆的大小(因为椭圆的最外边会和border的最外边重合的，如果图片最外边的颜色很深，有看出有棱边的效果，所以为了让体验更加好，
         * 让其缩进padding px)
         */
        RectF localRectF = new RectF(padding, padding, width - padding, height - padding);
        localCanvas.drawOval(localRectF, localPaint);
        return localBitmap;
    }
}
