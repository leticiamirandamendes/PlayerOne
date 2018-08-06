package com.example.letic.playerone;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letic.playerone.data.TaskContract;
import com.example.letic.playerone.models.Song;

import java.util.Observable;
import java.util.Observer;

import de.hdodenhof.circleimageview.CircleImageView;


public class PlayerFragment extends Fragment {

    private boolean isBookmarked = false;
    private TextView name;
    private TextView artist;
    private CircleImageView albumCover;
    private Song mSong;

    private Observer change = new Observer(){
        @Override
        public void update(Observable observable, Object o) {
            String musicName = mSong.getTitle();
            name.setText(musicName);
            String musicArtist = mSong.getArtist();
            if(musicArtist.equals("<unknown>")){
                artist.setText(getString(R.string.unknown_artist));
            }else {
                artist.setText(musicArtist);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSong = MainActivity.getmSong();
        name = getActivity().findViewById(R.id.title);
        artist = getActivity().findViewById(R.id.artist);
        albumCover = getActivity().findViewById(R.id.album);
        if(mSong!=null) {
            mSong.addObserver(change);
            setData();
            checkFavs();
        }
        Bitmap bm=((BitmapDrawable)albumCover.getDrawable()).getBitmap();
        int color = getDominantColor(bm);
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFFF,color});
        gradient.setCornerRadius(15f);
        view.setBackground(gradient);
        FloatingActionButton fab = getActivity().findViewById(R.id.btn_fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFavs();
                if(!isBookmarked) {
                    if(mSong==null){
                        Toast.makeText(getContext(),getString(R.string.no_song_selected), Toast.LENGTH_LONG).show();
                    }else {
                        ContentValues contentValues = new ContentValues();
                        String id = String.valueOf(mSong.getId());
                        String name = mSong.getTitle();
                        String description = mSong.getArtist();
                        contentValues.put(TaskContract.TaskEntry.COLUMN_ID, id);
                        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME, name);
                        contentValues.put(TaskContract.TaskEntry.COLUMN_AUTHOR, description);
                        getActivity().getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);
                        Toast.makeText(getContext(), getString(R.string.music_added), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void checkFavs(){
        Cursor c = getActivity().getContentResolver().query(
                TaskContract.TaskEntry.CONTENT_URI,
                null,
                " id=" + String.valueOf(mSong.getId()),
                null,
                null);
        if (c.getCount() > 0) {
            isBookmarked = true;
        }
    }

    public void setData(){
        String musicName = mSong.getTitle();
        name.setText(musicName);
        String musicArtist = mSong.getArtist();
        if(musicArtist.equals("<unknown>")){
            artist.setText(getString(R.string.unknown_artist));
        }else {
            artist.setText(musicArtist);
        }
    }

    public static int getDominantColor(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }
}
