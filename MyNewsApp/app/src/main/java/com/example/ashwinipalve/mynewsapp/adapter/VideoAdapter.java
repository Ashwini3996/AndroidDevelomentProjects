package com.example.ashwinipalve.mynewsapp.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.VideoView;

import com.example.ashwinipalve.mynewsapp.R;
import com.example.ashwinipalve.mynewsapp.pojo.Video;

import java.util.ArrayList;

/**
 * Created by Ashwini Palve on 22/05/2018.
 */

public class VideoAdapter extends BaseAdapter
{
    ArrayList<Video> mVideoList;
    Context mContext;
    LayoutInflater inflate;
    VideoView videoView;

    public VideoAdapter(ArrayList<Video> mVideoList, Context mContext) {
        this.mVideoList = mVideoList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mVideoList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final  int position , View convertView, ViewGroup parent) {

        final ViewHolder holder;

           if (convertView == null) {

                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.vidio_url_list, null);

                holder = new ViewHolder();

                holder.videoView = convertView
                        .findViewById(R.id.videoView);

                convertView.setTag(holder);
            }
            else {

                holder = (ViewHolder) convertView.getTag();

            }


            /***get clicked view and play video url at this position**/

            try {
                Video video = mVideoList.get(position);
                //play video using android api, when video view is clicked.
                String url = video.getVideoUrl(); // your URL here
                Uri videoUri = Uri.parse(url);
                holder.videoView.setVideoURI(videoUri);
                holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        holder.videoView.start();
                    }
                });



            }
            catch (Exception e)
            {
                e.printStackTrace();
            }




        return convertView;
    }



    public static class ViewHolder {
        VideoView videoView;

    }
}
