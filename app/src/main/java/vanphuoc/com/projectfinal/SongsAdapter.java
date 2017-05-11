package vanphuoc.com.projectfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZICK on 5/9/2017.
 */

public class SongsAdapter extends ArrayAdapter<Songs> {

    public SongsAdapter(Context context, int resource, List<Songs> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.item_song_listview, null);
        }
        Songs song = getItem(position);
        if (song != null) {
            TextView namesong = (TextView) view.findViewById(R.id.tv_nameSong);
            namesong.setText(song.tvNameSong);

            TextView namesinger = (TextView) view.findViewById(R.id.tv_nameSinger);
            namesinger.setText(song.tvNameSinger);

            ImageView imgicon= (ImageView) view.findViewById(R.id.img_icon);
            imgicon.setBackgroundResource(R.drawable.imgicon);
        }
        return view;
    }

}
