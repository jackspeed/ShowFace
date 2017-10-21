package com.ycj.show.face;

import android.content.Context;

/**
 * 用途：
 *
 * @version V1.0
 * @FileName: com.ycj.show.face.DisplayUtil.java
 * @author: ycj
 * @date: 2017-10-21 19:04
 */

public class DisplayUtil {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
