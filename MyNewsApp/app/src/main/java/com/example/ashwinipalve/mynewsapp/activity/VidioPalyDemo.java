package com.example.ashwinipalve.mynewsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ashwinipalve.mynewsapp.R;
import com.example.ashwinipalve.mynewsapp.adapter.VideoAdapter;
import com.example.ashwinipalve.mynewsapp.pojo.Video;

import java.util.ArrayList;

public class VidioPalyDemo extends AppCompatActivity {


    private ListView mVideoList;
    private ArrayList<Video> mVideoArray =new ArrayList<>();
    private VideoAdapter mVideoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vidio_paly_demo);
       mVideoList= findViewById(R.id.videoListView);

        //create videos
        Video riverVideo = new Video("https://s3.amazonaws.com/androidvideostutorial/862009639.mp4");
        Video carsVideo = new Video("https://s3.amazonaws.com/androidvideostutorial/862013714.mp4");
        Video townVideo = new Video("https://s3.amazonaws.com/androidvideostutorial/862014159.mp4");
        Video whiteCarVideo = new Video("https://s3.amazonaws.com/androidvideostutorial/862014159.mp4");
        Video parkVideo = new Video("https://s3.amazonaws.com/androidvideostutorial/862014834.mp4");
        Video busyCityVideo = new Video("https://s3.amazonaws.com/androidvideostutorial/862017385.mp4");

        mVideoArray.add(riverVideo);
        mVideoArray.add(carsVideo);
        mVideoArray.add(townVideo);
        mVideoArray.add(whiteCarVideo);
        mVideoArray.add(parkVideo);
        mVideoArray.add(busyCityVideo);

     mVideoAdapter=new VideoAdapter(mVideoArray,this);
     mVideoList.setAdapter(mVideoAdapter);
     mVideoAdapter.notifyDataSetChanged();





    }
}
