package com.popomusic.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.popo.popomusic.R;
import com.popomusic.MyApplication;
import com.popomusic.bean.MusicBean;
import com.popomusic.util.LogUtils;

import java.util.List;

/**
 * Created by popo on 2017/4/6 0006.
 */
public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.ViewHolder> {
    private static final String TAG = LocalMusicAdapter.class.getName();
    private List<MusicBean> mList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private MusicBean playingBean;//正在播放的歌曲
    public LocalMusicAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<MusicBean> list) {
        this.mList = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public void showPlaying(MusicBean bean){
        this.playingBean = bean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.local_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindData(mList.get(position),playingBean);
        holder.relateive_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                {
                    onItemClickListener.onItemClickListener(holder.relateive_local,position);
                    LogUtils.d("LocalMusicAdapter","position="+position+" song name:"+mList.get(position).getSongname());
                }
            }
            });
        }

    //清除
    public void remove(int position) {
        mList.remove(position);
        this.notifyDataSetChanged();
    }
    //全部清除
    public void removeAll() {
        if (mList.size() != 0) {
            mList.clear();
        }
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mList==null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout relateive_local;
        TextView songName;
        TextView singer;
        ImageView fab_isPlay;
        public ViewHolder(View itemView) {
            super(itemView);
           relateive_local = (LinearLayout) itemView.findViewById(R.id.line);
            songName = (TextView) itemView.findViewById(R.id.local_music_name);
            singer = (TextView) itemView.findViewById(R.id.local_music_singer);
          //  fab_isPlay = (ImageView) itemView.findViewById(R.id.fab_isPlay);
        }

        private void bindData(MusicBean bean,MusicBean playingBean){
            LogUtils.d(TAG,"bindData");
            if (null != bean){

                songName.setText(bean.getSongname());
                singer.setText(bean.getSingername());

            }
            if (null != bean && null != playingBean) {
                if (!bean.getSongname().equals(playingBean.getSongname()) && bean.getSongid() !=playingBean.getSongid()) {//先设置没有播放的歌曲的样式，随后设置正在播放歌曲的样式，有点覆盖的意思
                 //   fab_isPlay.setVisibility(View.GONE);
                }else if (bean.getSongname().equals(playingBean.getSongname())
                        && bean.getSingername().equals(playingBean.getSingername())){
                 //   fab_isPlay.setVisibility(View.VISIBLE);
                  //  Glide.with(MyApplication.mContext).load(R.mipmap.playing).asGif().diskCacheStrategy(DiskCacheStrategy.NONE).into(fab_isPlay);
                }
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(View view,int position);
    }
}
