package com.example.letic.playerone.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.letic.playerone.MainActivity;
import com.example.letic.playerone.R;

import java.util.ArrayList;

import static com.example.letic.playerone.widget.UpdateSongsService.FROM_ACTIVITY_SONG_LIST;

/**
 * Created by RPDesign on 22/05/2018.
 */

public class SongsWidgetProvider extends AppWidgetProvider{

    public static String REMOTEVIEW_SONG_LIST="REMOTEVIEW_SONG_LIST";
    public static String REMOTEVIEW_BUNDLE="REMOTEVIEW_BUNDLE";
    public static String APP_WIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE2";
    static ArrayList<String> songsList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.music_widget);

        Intent appIntent = new Intent(context, MainActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    }

    public static void updateMusicWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, SongsWidgetProvider.class));

        final String action = intent.getAction();

        assert action != null;
        if (action.equals(APP_WIDGET_UPDATE)) {
            songsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_SONG_LIST);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
            SongsWidgetProvider.updateMusicWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }
    }

}
