package vanphuoc.com.projectfinal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SongsAdapter adapter;
    ArrayList<Integer> arrSong;
    ArrayList<String> arrNameSong;
    ArrayList<Songs> arrListSongs;
    ListView lvSongs;
    static int i;
    static boolean play;
    static MediaPlayer song;
    int miliSecond=0;
    static int shuffleOrrepeat=0;

    ImageButton pause;
    ImageButton unpause;
    ImageButton previous;
    ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        initArr();
        adapter= new SongsAdapter(this,R.layout.item_song_listview,arrListSongs);
        lvSongs.setAdapter(adapter);

        // gui ds cac bai hat cho Service
        //Intent listSongs= new Intent(MainActivity.this, MyService.class);
        //listSongs.putIntegerArrayListExtra("arrSong",arrSong);
        //startService(listSongs);



        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, ""+ position, Toast.LENGTH_LONG).show();
                Intent myintent= new Intent(MainActivity.this, display.class);
                Bundle bundle= new Bundle();
                int i= position;
                bundle.putInt("i", i);
                bundle.putIntegerArrayList("arrSong", arrSong);
                bundle.putStringArrayList("arrNameSong", arrNameSong);
                myintent.putExtra("playSong",bundle);
                if (song!= null){
                    song.stop();
                }
                startActivityForResult(myintent, 123);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            // nhan du lieu gui ve tu display_activity
            miliSecond= data.getIntExtra("miliSecond", 0);
            i = data.getIntExtra("i", 0);
            play= data.getBooleanExtra("play", false);
            shuffleOrrepeat= data.getIntExtra("shuffleOrrepeat", 0);
            playSong();

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
        if (miliSecond!=0){
            song.seekTo(miliSecond);
        }
        song.start();
        if (!play){
            song.pause();
        }
        TextView nameOfSong= (TextView) findViewById(R.id.tvNameOfSong);
        nameOfSong.setText(arrNameSong.get(i));
        findViewById(R.id.lnShow).setVisibility(View.VISIBLE);
        if (play){
            findViewById(R.id.pause).setVisibility(View.VISIBLE);
        }
        else findViewById(R.id.unpause).setVisibility(View.VISIBLE);

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

    private void initWidget() {
        lvSongs= (ListView) findViewById(R.id.lvSongs);
        pause= (ImageButton) findViewById(R.id.pause);
        unpause= (ImageButton) findViewById(R.id.unpause);
        previous= (ImageButton) findViewById(R.id.previous);
        next= (ImageButton) findViewById(R.id.next);
    }
    public void onClick(View v) {
        Animation animationfornext= AnimationUtils.loadAnimation(this, R.anim.fadefornext);
        animationfornext.reset();
        switch (v.getId()){
            case R.id.previous:{
                previous.startAnimation(animationfornext);
                if(i!=0){
                    i-=1;
                }
                else {
                    i=arrSong.size()-1;
                }
                findViewById(R.id.unpause).setVisibility(View.GONE);
                findViewById(R.id.pause).setVisibility(View.VISIBLE);
                playSong();
                break;
            }
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
            case R.id.next: {
                next.startAnimation(animationfornext);
                if (i == arrSong.size() - 1) {
                    i = 0;
                } else {
                    i += 1;
                }
                findViewById(R.id.unpause).setVisibility(View.GONE);
                findViewById(R.id.pause).setVisibility(View.VISIBLE);
                playSong();
                break;
            }
        }
    }
    public void initArr(){
        arrSong= new ArrayList<Integer>();
        arrListSongs= new ArrayList<Songs>();
        arrNameSong= new ArrayList<String>();

        arrSong.add(R.raw.didetrove);
        arrListSongs.add( new Songs("Đi để trở về","Soobin Hoàng Sơn"));
        arrNameSong.add("Đi để trở về");

        arrSong.add(R.raw.gothong);
        arrListSongs.add( new Songs("Gót hồng","Lam Trường"));
        arrNameSong.add("Gót hồng");

        arrSong.add(R.raw.haydenvoiconnguoivietnam);
        arrListSongs.add( new Songs("Hãy đến với con người Việt Nam tôi","CLB Giai Điệu Xanh"));
        arrNameSong.add("Hãy đến với con người Việt Nam tôi");

        arrSong.add(R.raw.lamon);
        arrListSongs.add( new Songs("Làm ơn","Trần Trung Đức"));
        arrNameSong.add("Làm ơn");

        arrSong.add(R.raw.ngaymoinanglen);
        arrListSongs.add( new Songs("Ngày  mới nắng lên","V.Music"));
        arrNameSong.add("Ngày  mới nắng lên");

        arrSong.add(R.raw.phaidaucuoctinh);
        arrListSongs.add( new Songs("Phai dấu cuộc tình","Quang Vinh"));
        arrNameSong.add("Phai dấu cuộc tình");

        arrSong.add(R.raw.saigoncafesuada);
        arrListSongs.add( new Songs("Sài Gòn Cafe sữa đá","Hà Okio"));
        arrNameSong.add("Sài Gòn Cafe sữa đá");

        arrSong.add(R.raw.sautatca);
        arrListSongs.add( new Songs("Sau tất cả","ERIK"));
        arrNameSong.add("Sau tất cả");

        arrSong.add(R.raw.thattinh);
        arrListSongs.add( new Songs("Thất tình","Trịnh Đình Quang"));
        arrNameSong.add("Thất tình");

        arrSong.add(R.raw.toiyeuvietnam);
        arrListSongs.add( new Songs("Tôi yêu Việt Nam","V.Music"));
        arrNameSong.add("Tôi yêu Việt Nam");

        arrSong.add(R.raw.voivang);
        arrListSongs.add( new Songs("Vội vàng","Tạ Quang Thắng"));
        arrNameSong.add("Vội vàng");

        arrSong.add(R.raw.totest);
        arrListSongs.add( new Songs("Test","to test"));
        arrNameSong.add("Test");
    }
}
