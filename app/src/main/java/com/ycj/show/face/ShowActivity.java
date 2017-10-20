package com.ycj.show.face;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity implements Animation.AnimationListener {

    private ImageAdapter imageAdapter;
    private ImageView    ivShow;
    private FrameLayout  frameView;
    private int          screenWidth;
    private int          screenHeight;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initScreenSize();
        initView();
    }

    private void initScreenSize() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;         // 屏幕宽度（像素）
        screenHeight = dm.heightPixels;       // 屏幕高度（像素）
        //float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        //int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        //// 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        //int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        //int screenHeight = (int) (height / density);// 屏幕高度(dp)
    }

    private void initView() {
        frameView = (FrameLayout) findViewById(R.id.frame_show);
        ivShow = (ImageView) findViewById(R.id.iv_dialog_show);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_image);
        imageAdapter = new ImageAdapter(null, this);
        recyclerView.setAdapter(imageAdapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 8);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,
        //        StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //recyclerView.addItemDecoration(new GridDivider(this, 10, R.color.gray));
    }

    @Override protected void onResume() {
        super.onResume();
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 1800; i++) {
            data.add("" + i);
        }
        imageAdapter.setNewData(data);
        imageAdapter.notifyDataSetChanged();
        startShow();
    }

    private void startShow() {
        Message msg = Message.obtain();
        msg.arg1 = 1000;
        mHandler.sendMessageDelayed(msg, 3000);
    }

    private final android.os.Handler mHandler = new android.os.Handler() {
        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            frameView.setVisibility(View.VISIBLE);
            ivShow.setVisibility(View.VISIBLE);
            Log.i("*", "handleMessage开始动画 screenHeight： " + screenHeight + "screenWidth:  " +
                    screenWidth);
            /** 设置位移动画 向右位移150 */
            TranslateAnimation animation = new TranslateAnimation(0, screenWidth / 3, 0,
                    screenHeight / 4);
            animation.setDuration(1500);//设置动画持续时间
            animation.setRepeatCount(-1);//设置重复次数

            /** 设置缩放动画 */
            final ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(1500);//设置动画持续时间

            AnimationSet mAnimationSet = new AnimationSet(false);
            mAnimationSet.addAnimation(animation);
            mAnimationSet.addAnimation(scaleAnimation);
            mAnimationSet.setAnimationListener(ShowActivity.this);
            ivShow.setAnimation(mAnimationSet);
            /** 开始动画 */
            animation.startNow();
            Message message = Message.obtain();
            mHandler.sendMessageDelayed(message, 3500);
        }
    };

    @Override public void onAnimationStart(Animation animation) {

    }

    @Override public void onAnimationEnd(Animation animation) {
        //动画结束之后，停留一秒再消失
        new android.os.Handler().postDelayed(new Runnable() {
            @Override public void run() {
                frameView.setVisibility(View.GONE);
                ivShow.clearAnimation();
            }
        }, 1500);
    }

    @Override public void onAnimationRepeat(Animation animation) {

    }
}
