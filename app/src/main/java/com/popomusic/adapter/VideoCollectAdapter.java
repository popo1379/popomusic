package com.popomusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.popo.popomusic.R;
import com.popomusic.MyApplication;
import com.popomusic.util.LogUtils;
import com.popomusic.util.UIcollector;
import com.popomusic.videoModel.ItemList;
import com.popomusic.videoModel.VideoBean;
import com.popomusic.videoModel.VideoBeanDao;

import org.greenrobot.greendao.query.DeleteQuery;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2017/6/10 0010.
 */
public class VideoCollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VideoBean> datas;
    static final int TYPE_ITEM = 0;
    static final int TYPE_FOOTER = 1;
    private boolean showFoot = false;

    public  VideoCollectAdapter(List<VideoBean> lists){
        this.datas=lists;
    }

    //动态加载数据至配适器中
    public void addAll(List<VideoBean> lists) {
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
        ImageView deleteIV;
        LinearLayout linearLayout;
        public ViewHolder(View view){
            super(view);
            jcVideoPlayer=(JCVideoPlayerStandard)view.findViewById(R.id.videoplayer);
            textView=(TextView)view.findViewById(R.id.video_name);
            deleteIV=(ImageView)view.findViewById(R.id.video_delete);
            linearLayout=(LinearLayout)view.findViewById(R.id.linear);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType){
            case TYPE_ITEM:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collectvideo, parent, false));

            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collectvideo, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LogUtils.d("VideoAdapter","视频网址="+datas.get(position).getPlayUrl());
        ((ViewHolder) holder).jcVideoPlayer.setUp(
                datas.get(position).getPlayUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                datas.get(position).getTitle());
        Glide.with(UIcollector.getContext())
                .load(datas.get(position).getImage())
                .into(((ViewHolder) holder).jcVideoPlayer.thumbImageView);
        ((ViewHolder) holder).textView.setText(datas.get(position).getTitle());

        ((ViewHolder) holder).deleteIV.setOnClickListener(view -> {
            DeleteQuery<VideoBean> bd= MyApplication.getDaoSession().getVideoBeanDao().queryBuilder().where(VideoBeanDao.Properties.Title.eq(datas.get(position).title)).buildDelete();
            bd.executeDeleteWithoutDetachingEntities();
            datas= MyApplication.getVideoDaoSession().getVideoBeanDao().loadAll();
            if (datas.isEmpty()){
                ((ViewHolder) holder).linearLayout.setVisibility(View.INVISIBLE);
                LogUtils.d("VideoCollectAdapter","删除最后一个视频");
            }else {
                new VideoCollectAdapter(datas);
                notifyDataSetChanged();
                LogUtils.d("VideoCollectAdapter","删除"+datas.get(position).getTitle());
            }

        });

    }

}
