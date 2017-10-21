package com.ycj.show.face;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
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

    public HashMap<Integer, View> getImgMap() {
        return imgMap;
    }

    public void clearImgMap() {
        imgMap.clear();
    }

    private HashMap<Integer, View> imgMap = new HashMap<>();
    OnItemClickListener mOnItemClickListener;

    public ImageAdapter(List<String> data, Context context) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setNewData(List<String> list) {
        data = list;
        imgMap.clear();
        notifyDataSetChanged();
    }

    public void addData(List<String> list) {
        data.addAll(list);
        imgMap.clear();
        notifyDataSetChanged();
    }

    public void removeIndex(int index) {
        if (data.size() > index) {
            data.remove(index);
            notifyDataSetChanged();
        }
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        holder.tvText.setText("position: " + position);
        if (position % 2 == 0) {
            holder.imageView.setImageResource(R.mipmap.male);
        } else if (position % 2 == 1) {
            holder.imageView.setImageResource(R.mipmap.female);
        } else {
            holder.imageView.setImageResource(R.mipmap.test);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(position, holder.imageView,
                            holder.itemView);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(position, holder.imageView,
                            holder.itemView);
                }
            }
        });
        imgMap.put(position, holder.itemView);
    }

    public void performClick(int position) {
        if (imgMap.containsKey(position)) {
            imgMap.get(position).performClick();
        }
        notifyDataSetChanged();
    }

    @Override public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView  tvText;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image_item);
            tvText = itemView.findViewById(R.id.tv_item);
        }
    }

    interface OnItemClickListener {
        void onItemClickListener(int position, ImageView imageView, View parentView);
    }
}
