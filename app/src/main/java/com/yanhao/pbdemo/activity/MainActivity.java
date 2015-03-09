package com.yanhao.pbdemo.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import com.yanhao.pbdemo.R;
import com.yanhao.pbdemo.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private VideoView videoView;
    MediaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) findViewById(R.id.video);
        controller = new MediaController(this);
//        List<File> videos = FileUtil.getMediaFiles(FileUtil.getRootDirectory());

       File[] files=FileUtil.getRootDirectory().listFiles();
        Log.i("error",files.length+"");
        List<File> medias=new ArrayList<File>();
        for (File f:files){
            if(FileUtil.isVideo(f)){
                medias.add(f);
            }
        }
        File media1=medias.get(0);
        videoView.setVideoPath(media1.getAbsolutePath());
        videoView.setMediaController(controller);
            controller.setMediaPlayer(videoView);
        videoView.requestFocus();
        Log.i("error","medias="+medias.size());
//        if (videos != null && videos.size() > 0) {
//            File video = videos.get(0);
//            videoView.setVideoPath(video.getAbsolutePath());
//            videoView.setMediaController(controller);
//            controller.setMediaPlayer(videoView);
//            videoView.requestFocus();
//        }
//        else{
//            Log.e("error","SD中没有视频文件");
//            Toast.makeText(this,"SD中没有视频文件",Toast.LENGTH_SHORT).show();
//        }
    }


}
