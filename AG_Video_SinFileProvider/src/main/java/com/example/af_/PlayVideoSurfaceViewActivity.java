package com.example.af_;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;



// MOSTRAR UN VIDEO YA EXISTENTE, ELEGIDO DEL DISPOSITIVO
// NO GRABA, SOLO MUESTRA




@SuppressWarnings("ConstantConditions")
public class PlayVideoSurfaceViewActivity extends AppCompatActivity {

    private static final int PICK_VIDEO_REQUEST = 1001;
    private static final String TAG = "SurfaceSwitch";
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mFirstSurface;
    private SurfaceHolder mSecondSurface;
    private SurfaceHolder mActiveSurface;
    private Uri mVideoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video_surface_view);
        SurfaceView first = (SurfaceView) findViewById(R.id.firstSurface);
        first.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "First surface created!");
                mFirstSurface = surfaceHolder;
                if (mVideoUri != null) {
                    mMediaPlayer = MediaPlayer.create(getApplicationContext(),
                            mVideoUri, mFirstSurface);
                    mActiveSurface = mFirstSurface;
                    mMediaPlayer.start();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "First surface destroyed!");
            }
        });
        SurfaceView second = (SurfaceView) findViewById(R.id.secondSurface);
        second.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "Second surface created!");
                mSecondSurface = surfaceHolder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "Second surface destroyed!");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoUri = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK) {
            Log.d(TAG, "Got video " + data.getData());
            mVideoUri = data.getData();
        }
    }

    public void doStartStop(View view) {
        if (mMediaPlayer == null) {
            Intent pickVideo = new Intent(Intent.ACTION_PICK);
            pickVideo.setTypeAndNormalize("video/*");
            startActivityForResult(pickVideo, PICK_VIDEO_REQUEST);
        } else {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void doSwitchSurface(View view) {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mActiveSurface = mFirstSurface == mActiveSurface ? mSecondSurface : mFirstSurface;
            mMediaPlayer.setDisplay(mActiveSurface);
        }
    }
}