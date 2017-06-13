package com.popomusic.adapter;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.LogTime;
import com.popo.popomusic.R;

import com.popomusic.MyApplication;
import com.popomusic.util.LogUtils;
import com.popomusic.util.UIcollector;
import com.popomusic.videoModel.Data;
import com.popomusic.videoModel.ItemList;
import com.popomusic.videoModel.VideoBean;
import com.popomusic.videoModel.VideoBeanDao;

import org.greenrobot.greendao.query.DeleteQuery;

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
    ImageView loveIV;
    LinearLayout linearLayout;
    public ViewHolder(View view){
        super(view);
        jcVideoPlayer=(JCVideoPlayerStandard)view.findViewById(R.id.videoplayer);
        textView=(TextView)view.findViewById(R.id.video_name);
        loveIV=(ImageView)view.findViewById(R.id.video_collect);
        linearLayout=(LinearLayout)view.findViewById(R.id.linear);
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
    if (!MyApplication.getDaoSession().getVideoBeanDao().queryBuilder().where(VideoBeanDao.Properties.Title.eq(datas.get(position).data.title)).list().isEmpty()) {
        ((ViewHolder) holder).loveIV.setImageResource(R.mipmap.video_love_red);
        LogUtils.d("VideoAdapter",datas.get(position).data.title+"已被收藏");
    }

    ((ViewHolder) holder).loveIV.setOnClickListener(view -> {
        //收藏按钮逻辑：
        if (MyApplication.getDaoSession().getVideoBeanDao().queryBuilder().where(VideoBeanDao.Properties.Title.eq(datas.get(position).data.title)).list().isEmpty()) {
            VideoBean videoBean = new VideoBean();
            videoBean.setPlayUrl(datas.get(position).data.playUrl);
            videoBean.setTitle(datas.get(position).data.title);
            videoBean.setImage(datas.get(position).data.image);
            MyApplication.getDaoSession().getVideoBeanDao().insertOrReplace(videoBean);
            ((ViewHolder) holder).loveIV.setImageResource(R.mipmap.video_love_red);
            LogUtils.d("VideoAdapter","收藏视频");
        }else {
            DeleteQuery<VideoBean> bd= MyApplication.getDaoSession().getVideoBeanDao().queryBuilder().where(VideoBeanDao.Properties.Title.eq(datas.get(position).data.title)).buildDelete();
            bd.executeDeleteWithoutDetachingEntities();
            ((ViewHolder) holder).loveIV.setImageResource(R.mipmap.video_love);
            LogUtils.d("VideoAdapter","取消收藏视频");
        }
    });

}

}

