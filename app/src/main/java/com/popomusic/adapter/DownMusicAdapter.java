package com.popomusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.model.FileDownloadStatus;

import com.popo.popomusic.R;

import com.popomusic.bean.MusicBean;

import java.io.File;
import java.util.List;

/**
 * Created by popo on 2017/5/4 0004.
 */
public class DownMusicAdapter extends RecyclerView.Adapter<DownMusicAdapter.ViewHolder> {
    private Context mContext;
private List<MusicBean> list;
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView taskNameTv;
        private TextView taskStatusTv;
        private ProgressBar taskPb;
        private Button taskActionBtn;
        /**
         * viewHolder position
         */
        private int position;
        /**
         * download id
         */
        private int id;

        public ViewHolder(View itemView) {
            super(itemView);
            taskNameTv = (TextView) itemView.findViewById(R.id.task_name_tv);
            taskStatusTv = (TextView)itemView. findViewById(R.id.task_status_tv);
            taskPb = (ProgressBar) itemView.findViewById(R.id.task_pb);
            taskActionBtn = (Button) itemView.findViewById(R.id.task_action_btn);
        }
        public void updateDownloading(final int status, final long sofar, final long total) {
            final float percent = sofar
                    / (float) total;
            taskPb.setMax(100);
            taskPb.setProgress((int) (percent * 100));

            switch (status) {
                case FileDownloadStatus.pending:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
                    break;
                case FileDownloadStatus.started:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
                    break;
                case FileDownloadStatus.connected:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
                    break;
                case FileDownloadStatus.progress:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_progress);
                    break;
                default:
              //      taskStatusTv.setText(MyApplication.CONTEXT.getString(
               //             R.string.tasks_manager_demo_status_downloading, status));
                    break;
            }

          //  taskActionBtn.setText(R.string.pause);
        }
        public void updateNotDownloaded(final int status, final long sofar, final long total) {
            if (sofar > 0 && total > 0) {
                final float percent = sofar
                        / (float) total;
                taskPb.setMax(100);
                taskPb.setProgress((int) (percent * 100));
            } else {
                taskPb.setMax(1);
                taskPb.setProgress(0);
            }

            switch (status) {
                case FileDownloadStatus.error:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_error);
                    break;
                case FileDownloadStatus.paused:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
                    break;
                default:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_not_downloaded);
                    break;
            }
         //   taskActionBtn.setText(R.string.start);
        }
        public void updateDownloaded() {
            taskPb.setMax(1);
            taskPb.setProgress(1);

            taskStatusTv.setText(R.string.tasks_manager_demo_status_completed);
           // taskActionBtn.setText(R.string.delete);
        }

        public void update(final int id, final int position) {
            this.id = id;
            this.position = position;
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_down,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MusicBean musicBean=list.get(position);

      //  holder.update(musicBean.getSongname(), position);
        holder.taskActionBtn.setTag(holder);
        holder.taskNameTv.setText(musicBean.getSongname());

        holder.taskActionBtn.setEnabled(true);

    }
    @Override
    public int getItemCount() {
        return 0;
    }
}







