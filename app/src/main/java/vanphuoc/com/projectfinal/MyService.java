package vanphuoc.com.projectfinal;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ZICK on 5/9/2017.
 */

public class MyService extends Service {

    static int i;
    static boolean play;
    static int shuffleOrrepeat= 0;
    ArrayList<Integer> arrSong;

    MediaPlayer song;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // nhan ds bai hat
        arrSong= intent.getIntegerArrayListExtra("arrSong");
        stopSelf();
        // nhan bai hat duoc chon o listview gui qua
        i=intent.getIntExtra("i", 0);
        playSong();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void playSong(){

        Intent a= new Intent(MyService.this, display.class);
        startActivity(a);

        if(song!= null){
            song.stop();
            song= MediaPlayer.create(getApplicationContext(),arrSong.get(i));
        }
        else {
            song= MediaPlayer.create(getApplicationContext(),arrSong.get(i));
        }
        song.start();

        song.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer song) {
                Toast.makeText(MyService.this, "Service is started", Toast.LENGTH_LONG ).show();
            }
        });
    }
}
