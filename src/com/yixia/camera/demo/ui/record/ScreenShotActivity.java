package com.yixia.camera.demo.ui.record;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.util.Constant;

import static android.media.MediaMetadataRetriever.OPTION_CLOSEST;
import static android.media.MediaMetadataRetriever.OPTION_CLOSEST_SYNC;

public class ScreenShotActivity extends Activity {
    private String mPath;
    private String duration;
    private long videoDuration;
    private ImageView ivScreenShot1, ivScreenShot2, ivScreenShot3, ivScreenShot4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);

        mPath = getIntent().getStringExtra(Constant.RECORD_VIDEO_PATH);
        videoDuration = getIntent().getIntExtra("duration", 0);


        ivScreenShot1 = (ImageView) findViewById(R.id.iv_screenshot1);
        ivScreenShot2 = (ImageView) findViewById(R.id.iv_screenshot2);
        ivScreenShot3 = (ImageView) findViewById(R.id.iv_screenshot3);
        ivScreenShot4 = (ImageView) findViewById(R.id.iv_screenshot4);

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, Uri.parse(mPath));
        duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        Log.i("DURATION", "onCreate: " + duration);
        Log.i("DURATION _ from intent ", "onCreate: " + videoDuration);
//        long length = Long.parseLong(duration);
//        length = length * 1000;
        long length = videoDuration;
        length = length * 1000;
        Log.i("TIME", "onCreate: " + length);
        /**
         * todo:视频截图最后一张重复了
         * 需要注意的是，@MediaMetadataRetriever使用的时间是微秒，需要 millisecond * 1000
         */
        Bitmap bitmap1 = retriever.getFrameAtTime(1, OPTION_CLOSEST_SYNC);//第一帧
        Bitmap bitmap2 = retriever.getFrameAtTime(4000 * 1000);
        Bitmap bitmap3 = retriever.getFrameAtTime(7000 * 1000);
        Bitmap bitmap4 = retriever.getFrameAtTime(videoDuration - 2000 * 1000, OPTION_CLOSEST);//最后一帧
        ivScreenShot1.setImageBitmap(bitmap1);
        ivScreenShot2.setImageBitmap(bitmap2);
        ivScreenShot3.setImageBitmap(bitmap3);
        ivScreenShot4.setImageBitmap(bitmap4);
    }
}
