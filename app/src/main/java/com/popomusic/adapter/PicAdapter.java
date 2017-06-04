package com.popomusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.popo.popomusic.R;
import com.popomusic.picBean.Contentlist;
import com.popomusic.util.UIcollector;
import com.popomusic.videoModel.ItemList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by popo on 2017/5/31 0031.
 */
public class PicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Contentlist> list;
    private List<Integer> mHeights;
    private OnItemClickListener onItemClickListener;


    public PicAdapter(List<Contentlist> list) {

        this.list = list;
        mHeights = new ArrayList<>();
    }

class ViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;

    public ViewHolder(View view){
        super(view);
        imageView=(ImageView)view.findViewById(R.id.pic);
    }

}
    //动态加载数据至配适器中
    public void addAll(List<Contentlist> lists) {
        list.addAll(lists);
    }
    public void removeAll() {
        if (list.size() != 0) {
            list.clear();
        }
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pic, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Contentlist contentlist= list.get(position);
        // 随机高度, 模拟瀑布效果.
        if (mHeights.size() <= position) {
            mHeights.add((int) (100 + Math.random() * 300));
        }
        ViewGroup.LayoutParams lp = ((ViewHolder) holder).imageView.getLayoutParams();
        lp.height = mHeights.get(position);
        ((ViewHolder) holder).imageView.setLayoutParams(lp);
        Glide.with(UIcollector.getContext())
                .load(contentlist.getList().get(0).getMiddle())
                .override(300, 400)
                .into(((ViewHolder) holder).imageView);
        ((ViewHolder) holder).imageView.setOnClickListener(view -> {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClickListener(((ViewHolder) holder).imageView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener{
        void onItemClickListener(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

}














