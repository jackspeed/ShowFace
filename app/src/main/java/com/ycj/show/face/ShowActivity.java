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
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShowActivity extends AppCompatActivity
        implements Animation.AnimationListener, ImageAdapter.OnItemClickListener {

    private ImageAdapter imageAdapter;
    private ImageView    ivMove;
    private FrameLayout  frameView;
    private int          centerX;
    private int          centerY;
    private float        moveToX;
    private float        moveToY;
    private int currentIndex = 0;
    private RecyclerView recyclerView;
    private int          screenWidth;
    private ImageView    ivResult;

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
        int screenHeight = dm.heightPixels;       // 屏幕高度（像素）
        moveToX = screenWidth / 3;
        moveToY = screenHeight / 4;
        centerX = screenWidth / 2;
        centerY = screenHeight / 2;
        Log.i("*******", "screenHeight： " + screenHeight + "screenWidth:  " + screenWidth);
    }

    private void initView() {
        frameView = (FrameLayout) findViewById(R.id.frame_show);
        ivMove = (ImageView) findViewById(R.id.iv_dialog_show);
        ivResult = (ImageView) findViewById(R.id.iv_result_show);
        recyclerView = (RecyclerView) findViewById(R.id.list_image);
        imageAdapter = new ImageAdapter(null, this);
        imageAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(imageAdapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager layoutManager = new GridLayoutManager(this, getColums());
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,
        //        StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //recyclerView.addItemDecoration(new GridDivider(this, 10, R.color.gray));

    }

    public int getColums() {
        int pix = DisplayUtil.dip2px(this, 100);
        int colums = screenWidth / pix;
        return colums;
    }

    @Override protected void onResume() {
        super.onResume();
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 180; i++) {
            data.add("" + i);
        }
        imageAdapter.setNewData(data);
        imageAdapter.notifyDataSetChanged();

        //startShow();
    }

    private void startShow() {
        Message message = Message.obtain();
        mHandler.sendMessageDelayed(message, 3000);
    }

    private final android.os.Handler mHandler = new android.os.Handler() {
        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Random random = new Random();
            currentIndex = random.nextInt(180);
            final int position = currentIndex;
            Log.i("*******", "performClick(position)" + position);
            if (imageAdapter.getImgMap().containsKey(position)) {
                imageAdapter.performClick(position);
                Message message = Message.obtain();
                mHandler.sendMessageDelayed(message, 3500);
            } else {
                imageAdapter.clearImgMap();
                recyclerView.scrollToPosition(position);
                recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        imageAdapter.performClick(position);
                        Message message = Message.obtain();
                        mHandler.sendMessageDelayed(message, 3500);
                    }
                });
            }
        }
    };

    private void setAnimation(ImageView imageView, float startX, float startY) {
        Log.i("*******", "moveToX " + moveToX + "          " + "moveToY " + moveToY);
        imageView.setVisibility(View.VISIBLE);
        imageView.clearAnimation();
        //设置位移动画
        TranslateAnimation animation = new TranslateAnimation(startX, moveToX, startY, moveToY);
        //float fromXDelta:这个参数表示动画开始的点离当前View X坐标上的差值；
        //float toXDelta, 这个参数表示动画结束的点离当前View X坐标上的差值；
        //float fromYDelta, 这个参数表示动画开始的点离当前View Y坐标上的差值；
        //float toYDelta)这个参数表示动画开始的点离当前View Y坐标上的差值；

        //加速器越来越慢
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(2000);//设置动画持续时间
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        animation.setAnimationListener(this);
        imageView.setAnimation(animation);
        //开始动画 */
        animation.startNow();
    }

    @Override public void onAnimationStart(Animation animation) {

    }

    @Override public void onAnimationEnd(Animation animation) {
        ivMove.clearAnimation();
        ivMove.setVisibility(View.GONE);
        ivResult.setImageResource(R.mipmap.female);
        ivResult.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override public void run() {
                ivResult.setVisibility(View.GONE);
                imageAdapter.removeIndex(currentIndex);
            }
        }, 2000);
    }

    @Override public void onAnimationRepeat(Animation animation) {

    }

    @Override public void onItemClickListener(int position, ImageView imageView, View parentView) {
        float startX = parentView.getX();
        float startY = parentView.getY();
        ivMove.setX(startX);
        ivMove.setY(startY);
        ivMove.setImageDrawable(imageView.getDrawable());
        ivMove.setVisibility(View.VISIBLE);
        //float fromXDelta:这个参数表示动画开始的点离当前View X坐标上的差值；
        //float toXDelta, 这个参数表示动画结束的点离当前View X坐标上的差值；
        //float fromYDelta, 这个参数表示动画开始的点离当前View Y坐标上的差值；
        //float toYDelta)这个参数表示动画开始的点离当前View Y坐标上的差值；
        moveToX = centerX - startX - parentView.getHeight() / 2;
        moveToY = centerY - startY - parentView.getWidth() / 2;
        imageAdapter.notifyDataSetChanged();
        setAnimation(ivMove, 0, 0);
    }
}
