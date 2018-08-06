package com.example.letic.playerone.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by RPDesign on 22/05/2018.
 */

public class UpdateSongsService extends IntentService{

    public static String FROM_ACTIVITY_SONG_LIST ="FROM_ACTIVITY_INGREDIENTS_LIST";

    public UpdateSongsService() {
        super("UpdateRecipesService");
    }

    public static void startAddingService(Context context, ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, UpdateSongsService.class);
        intent.putExtra(FROM_ACTIVITY_SONG_LIST,fromActivityIngredientsList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_SONG_LIST);
            handleActionUpdateBakingWidgets(fromActivityIngredientsList);

        }
    }

    private void handleActionUpdateBakingWidgets(ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(this, SongsWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(FROM_ACTIVITY_SONG_LIST,fromActivityIngredientsList);
        sendBroadcast(intent);
    }

}
