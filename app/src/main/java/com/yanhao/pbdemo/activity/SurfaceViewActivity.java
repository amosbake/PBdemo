package com.yanhao.pbdemo.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.yanhao.pbdemo.R;
import com.yanhao.pbdemo.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SurfaceViewActivity extends ActionBarActivity implements SurfaceHolder.Callback{

    private SurfaceView videoView;
    private SurfaceHolder holder;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        videoView= (SurfaceView) findViewById(R.id.id_sv_video);
        holder=videoView.getHolder();
        holder.addCallback(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(holder);

        File[] files= FileUtil.getRootDirectory().listFiles();
        Log.i("error", files.length + "");
        List<File> medias=new ArrayList<File>();
        for (File f:files){
            if(FileUtil.isVideo(f)){
                medias.add(f);
            }
        }
        File media1=medias.get(0);

        try {
            mediaPlayer.setDataSource(media1.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
