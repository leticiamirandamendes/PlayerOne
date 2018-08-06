package com.example.letic.playerone;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.letic.playerone.analytics.PlayerApplication;
import com.example.letic.playerone.models.Song;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends AppCompatActivity {

    TextView toolbarTitle;
    TextView btnPlaylist;
    TextView btnMusic;
    TextView btnFavorites;

    static final String STATE_SCORE = "playerScore";

    public static Song mSong;
    public boolean permission;
    private Tracker mTracker;

    public static void setmSong(Song song) {
        mSong = song;
    }

    public static Song getmSong() {
        return mSong;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Permission checked!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    public void setup() {
        toolbarTitle = findViewById(R.id.toolbar_title);
        btnPlaylist = findViewById(R.id.btn_playlist);
        btnMusic = findViewById(R.id.btn_music);
        btnFavorites = findViewById(R.id.btn_favorite);
        PlayerApplication application = (PlayerApplication) getApplication();
        mTracker = application.getDefaultTracker();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new PlaylistFragment()).commit();
        setupMenu();
        clearMenuItem();
        toolbarTitle.setText(getString(R.string.playlist));
        btnPlaylist.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_playlist_selected, 0, 0);
        btnPlaylist.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void clearMenuItem() {
        btnMusic.setTextColor(getResources().getColor(R.color.white));
        btnPlaylist.setTextColor(getResources().getColor(R.color.white));
        btnFavorites.setTextColor(getResources().getColor(R.color.white));
        btnMusic.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
                .getDrawable(R.drawable.ic_play), null, null);
        btnPlaylist.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
                .getDrawable(R.drawable.ic_playlist), null, null);
        btnFavorites.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
                .getDrawable(R.drawable.ic_favorite), null, null);

    }

    public void setupMenu() {
        btnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new PlaylistFragment()).commit();
                clearMenuItem();
                toolbarTitle.setText(getString(R.string.playlist));
                btnPlaylist.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_playlist_selected, 0, 0);
                btnPlaylist.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new PlayerFragment()).commit();
                clearMenuItem();
                toolbarTitle.setText(getString(R.string.music));
                btnMusic.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_play_selected, 0, 0);
                btnMusic.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new FavoriteFragment()).commit();
                clearMenuItem();
                toolbarTitle.setText(getString(R.string.favs));
                btnFavorites.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_selected, 0, 0);
                btnFavorites.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
