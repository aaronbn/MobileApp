package umn.ac.id.tugasuts_32941;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    Button btnplay, btnnext, btnprev;
    TextView txtname;
    SeekBar mseekbar;
    String songname;

    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<String> mySongs;
    ImageView imageView;
    Thread updateseekbar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_player);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnprev = findViewById(R.id.prev);
        btnplay = findViewById(R.id.play);
        btnnext = findViewById(R.id.next);
        mseekbar = findViewById(R.id.seekbar);
        txtname = findViewById(R.id.judullagu);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = i.getStringExtra("songname");
        position = bundle.getInt("pos", 0);

        songname = songName;
        txtname.setSelected(true);
        txtname.setText(songname);

        Uri uri = Uri.parse(mySongs.get(position).toString());

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        updateseekbar = new Thread(){
            @Override
            public void run(){
                int totalDuration = mediaPlayer.getDuration();
                int currentposition = 0;

                while(currentposition<totalDuration){
                    try{
                        sleep(500);
                        currentposition = mediaPlayer.getCurrentPosition();
                        mseekbar.setProgress(currentposition);
                    }catch (InterruptedException | IllegalStateException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        mseekbar.setMax(mediaPlayer.getDuration());
        updateseekbar.start();
        mseekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        mseekbar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        mseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        String endTime = createTime(mediaPlayer.getDuration());

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, delay);
            }
        }, delay);

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    btnplay.setBackgroundResource(R.drawable.play);
                    mediaPlayer.pause();
                } else {
                    btnplay.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnnext.performClick();
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position + 1) % mySongs.size());
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                songname = mySongs.get(position);
                txtname.setText(songname);
                mediaPlayer.start();
                btnplay.setBackgroundResource(R.drawable.pause);
                startAnimation(imageView);
            }
        });

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position - 1) < 0) ? (mySongs.size() - 1) : (position - 1);
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                songname = mySongs.get(position);
                txtname.setText(songname);
                mediaPlayer.start();
                btnplay.setBackgroundResource(R.drawable.pause);
                startAnimation(imageView);
            }
        });
    }

        public void startAnimation(View view){
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
            animator.setDuration(1000);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator);
            animatorSet.start();
        }

        public String createTime ( int duration){
            String time = "";
            int min = duration / 1000 / 90;
            int sec = duration / 1000 % 90;

            time += min + ":";

            if (sec < 10) {
                time += "0";
            }
            time += sec;

            return time;
        }
    }
