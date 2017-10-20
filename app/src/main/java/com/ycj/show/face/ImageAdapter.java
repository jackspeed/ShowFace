package com.ycj.show.face;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

/**
 * 用途：
 *
 * @version V1.0
 * @FileName: com.ycj.show.face.ImageAdapter.java
 * @author: ycj
 * @date: 2017-10-20 21:40
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final Context      context;
    private       List<String> data;

    public ImageAdapter(List<String> data, Context context) {
        this.context = context;
        this.data = data;
    }

    public void setNewData(List<String> list) {
        data = list;
        notifyDataSetChanged();
    }

    public void addData(List<String> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_item, null);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.mipmap.test);
    }

    @Override public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image_item);
        }
    }
}
