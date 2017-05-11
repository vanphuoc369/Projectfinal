package vanphuoc.com.projectfinal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class display extends AppCompatActivity{

    static int i;
    static boolean play= false;  //bai hat k duoc phat
    static int shuffleOrrepeat=0; // 0: binh thuong, 1: xao tron, 2: lap lai bai hat, 3: lap lai ds bai hat
    ArrayList<Integer> arrSong;
    ArrayList<String> arrNameSong;
    static MediaPlayer song;

    ImageButton btnMenu;
    ImageButton shuffle;
    ImageButton shuffleActive;
    ImageButton pause;
    ImageButton unpause;
    ImageButton repeat;
    ImageButton repeatOne;
    ImageButton repeatActive;
    ImageButton previous;
    ImageButton next;
    TextView tvNameOfSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        btnMenu= (ImageButton) findViewById(R.id.btnMenu);
        shuffle= (ImageButton) findViewById(R.id.shuffle);
        shuffleActive= (ImageButton) findViewById(R.id.shuffleActive);
        pause= (ImageButton) findViewById(R.id.pause);
        unpause= (ImageButton) findViewById(R.id.unpause);
        repeat= (ImageButton) findViewById(R.id.repeat);
        repeatOne= (ImageButton) findViewById(R.id.repeatOne);
        repeatActive= (ImageButton) findViewById(R.id.repeatActive);
        previous= (ImageButton) findViewById(R.id.previous);
        next= (ImageButton) findViewById(R.id.next);
        tvNameOfSong= (TextView) findViewById(R.id.tvNameOfSong);



        // nhan bai hat duoc chon o listview gui qua
        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("playSong");
        i = packageFromCaller.getInt("i");
        arrSong= packageFromCaller.getIntegerArrayList("arrSong");
        arrNameSong= packageFromCaller.getStringArrayList("arrNameSong");

        playSong();

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int milisecond=0;
                if (song!=null){
                    milisecond= song.getCurrentPosition();
                }
                Intent back= new Intent();
                back.putExtra("i", i);
                back.putExtra("shuffleOrrepeat",shuffleOrrepeat);
                back.putExtra("play",play);
                back.putExtra("miliSecond",milisecond);
                song.stop();
                setResult( 123,back);
                finish();
            }
        });
    }
    public void onClick(View v){
        Animation animationfornext= AnimationUtils.loadAnimation(this, R.anim.fadefornext);
        animationfornext.reset();
        switch (v.getId()){
            case R.id.pause: {
                findViewById(R.id.pause).setVisibility(View.GONE);
                findViewById(R.id.unpause).setVisibility(View.VISIBLE);
                if (song.isPlaying()) {
                    song.pause();
                }
                break;
            }
            case R.id.unpause: {
                if (song == null) {
                    playSong();
                } else {
                    findViewById(R.id.unpause).setVisibility(View.GONE);
                    findViewById(R.id.pause).setVisibility(View.VISIBLE);
                    song.start();
                }
                break;
            }
            case R.id.shuffle: {
                shuffle.setVisibility(View.GONE);
                shuffleActive.setVisibility(View.VISIBLE);
                repeat.setVisibility(View.VISIBLE);
                repeatOne.setVisibility(View.GONE);
                repeatActive.setVisibility(View.GONE);
                shuffleOrrepeat = 1;
                break;
            }
            case R.id.shuffleActive: {
                shuffle.setVisibility(View.VISIBLE);
                shuffleActive.setVisibility(View.GONE);
                repeat.setVisibility(View.VISIBLE);
                repeatOne.setVisibility(View.GONE);
                repeatActive.setVisibility(View.GONE);
                shuffleOrrepeat = 0;
                break;
            }
            case R.id.repeat: {
                shuffle.setVisibility(View.VISIBLE);
                shuffleActive.setVisibility(View.GONE);
                repeat.setVisibility(View.GONE);
                repeatOne.setVisibility(View.VISIBLE);
                repeatActive.setVisibility(View.GONE);
                shuffleOrrepeat = 2;
                break;
            }
            case R.id.repeatOne: {
                shuffle.setVisibility(View.VISIBLE);
                shuffleActive.setVisibility(View.GONE);
                repeat.setVisibility(View.GONE);
                repeatOne.setVisibility(View.GONE);
                repeatActive.setVisibility(View.VISIBLE);
                shuffleOrrepeat = 3;
                break;
            }
            case R.id.repeatActive: {
                shuffle.setVisibility(View.VISIBLE);
                shuffleActive.setVisibility(View.GONE);
                repeat.setVisibility(View.VISIBLE);
                repeatOne.setVisibility(View.GONE);
                repeatActive.setVisibility(View.GONE);
                shuffleOrrepeat = 0;
                break;
            }
            case R.id.previous:{
                previous.startAnimation(animationfornext);
                if(i!=0){
                    i--;
                }
                else {
                    i= (arrSong.size()-1);
                }
                findViewById(R.id.unpause).setVisibility(View.GONE);
                findViewById(R.id.pause).setVisibility(View.VISIBLE);
                playSong();
                break;
            }
            case R.id.next:{
                next.startAnimation(animationfornext);
                if(i== arrSong.size()-1){
                    i=0;
                }
                else {
                    i++;
                }
                findViewById(R.id.unpause).setVisibility(View.GONE);
                findViewById(R.id.pause).setVisibility(View.VISIBLE);
                playSong();
                break;
            }
        }
    }
    public void playSong(){
        if(song!= null){
            song.stop();
            song= MediaPlayer.create(getApplicationContext(),arrSong.get(i));
        }
        else {
            song= MediaPlayer.create(getApplicationContext(),arrSong.get(i));
        }
        tvNameOfSong.setText(arrNameSong.get(i));
        song.start();
        play=true;

        song.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer song) {
                //Toast.makeText(display.this, ""+sttShuffleOrRepeat + arrNameOfSong.get(i), Toast.LENGTH_LONG ).show();
                // phat binh thuong
                if(shuffleOrrepeat==0){
                    if(i<arrSong.size()-1){
                        i++;
                        playSong();
                    }
                    else {
                        findViewById(R.id.pause).setVisibility(View.GONE);
                        findViewById(R.id.unpause).setVisibility(View.VISIBLE);
                    }
                }
                //xao tron bai hat
                else if (shuffleOrrepeat==1){
                    // chon mot bai hat ngau nhien tu Min - Max)
                    //Random rd=new Random();
                    //x=rd.nextInt((Max-Min+1)+Min);
                    Random rd= new Random();
                    i= rd.nextInt(((arrSong.size()-1)-0+1)+0);
                    playSong();
                }
                // lap lai bai hat dang phat
                else if (shuffleOrrepeat==2){
                    playSong();
                }
                // lap lai danh sach bai hat dang phat
                else {
                    if (i==arrSong.size()-1){
                        i=0;
                        playSong();
                    }
                    else {
                        i++;
                        playSong();
                    }
                }
            }
        });
    }
}
