package com.ktx.lib.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 自定义倒计时控件
 * 作者: 寇清华
 * 时间: 2019-06-06 14:13
 * 邮箱:252706952@qq.com
 */
public class TimeCount extends CountDownTimer {

    private TextView b;

    private String repetColor = "#4e6cff";
    private String enableColor = "#aaaaaa";

    public TimeCount(long millisInFuture, long countDownInterval, TextView b) {
        super(millisInFuture, countDownInterval);
        this.b = b;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        b.setEnabled(false);
        b.setTextColor(Color.parseColor(enableColor));
        b.setText("重新发送(" + millisUntilFinished / 1000 + ")");
    }

    @Override
    public void onFinish() {
        b.setEnabled(true);
        b.setText("获取验证码");
        b.setTextColor(Color.parseColor(repetColor));
    }

    public void setRepetColor(String repetColor){
        this.repetColor = repetColor;
    }

    public void setEnableColor(String enableColor){
        this.enableColor = enableColor;
    }
}
