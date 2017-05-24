package com.popomusic.adapter;

import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.LogTime;
import com.popo.popomusic.R;
import com.popomusic.bean.VideoConstant;
import com.popomusic.util.LogUtils;
import com.popomusic.util.UIcollector;
import com.popomusic.videoModel.Data;
import com.popomusic.videoModel.ItemList;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by popo on 2017/5/22 0022.
 */
public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemList> datas;
    static final int TYPE_ITEM = 0;
    static final int TYPE_FOOTER = 1;
    private boolean showFoot = false;

    public VideoAdapter(List<ItemList> lists){
        this.datas=lists;
    }

    //动态加载数据至配适器中
    public void addAll(List<ItemList> lists) {
        datas.addAll(lists);
    }
    public void removeAll() {
        if (datas.size() != 0) {
           datas.clear();
        }
        this.notifyDataSetChanged();
    }

    //得到item数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!showFoot) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    JCVideoPlayerStandard jcVideoPlayer;

    public ViewHolder(View view){
        super(view);
        jcVideoPlayer=(JCVideoPlayerStandard)view.findViewById(R.id.videoplayer);
        textView=(TextView)view.findViewById(R.id.video_name);
    }

}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType){
            case TYPE_ITEM:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videopager, parent, false));

            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videopager, parent, false));
        }
    }

@Override
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    LogUtils.d("VideoAdapter","视频网址="+datas.get(position).data.playUrl);
    ((ViewHolder) holder).jcVideoPlayer.setUp(
            datas.get(position).data.playUrl, JCVideoPlayer.SCREEN_LAYOUT_LIST,
            datas.get(position).data.title);
    Glide.with(UIcollector.getContext())
            .load(datas.get(position).data.image)
            .into(((ViewHolder) holder).jcVideoPlayer.thumbImageView);
    ((ViewHolder) holder).textView.setText(datas.get(position).data.title);
}


}

