package com.digiburo.wxtraxlib.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.text.format.Time;
import android.util.Log;

import com.digiburo.wxtraxlib.Constant;
import com.digiburo.wxtraxlib.db.DataBaseFacade;
import com.digiburo.wxtraxlib.db.ObservationModel;
import com.digiburo.wxtraxlib.db.StationModel;
import com.digiburo.wxtraxlib.utility.UserPreferenceHelper;
import com.digiburo.wxtraxlib.utility.Utility;
import com.digiburo.wxtraxlib.utility.WxXmlCollection;

import java.util.ArrayList;

public class CollectionService extends IntentService {
    public final String LOG_TAG = getClass().getName();

    private static final String ACTION_ALL = "com.digiburo.wxtraxlib.service.action.all";
    private static final String ACTION_SINGLE = "com.digiburo.wxtraxlib.service.action.single";

    private static final String EXTRA_STATION = "com.digiburo.wxtraxlib.service.extra.station";

    public CollectionService() {
        super("CollectionService");
    }

    public static void startActionAll(Context context) {
        Intent intent = new Intent(context, CollectionService.class);
        intent.setAction(ACTION_ALL);
        context.startService(intent);
    }

    public static void startActionSingle(Context context, String target) {
        Intent intent = new Intent(context, CollectionService.class);
        intent.setAction(ACTION_SINGLE);
        intent.putExtra(EXTRA_STATION, target);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            Log.d(LOG_TAG, "skipping null intent");
            return;
        }

        final String action = intent.getAction();
        if (ACTION_ALL.equals(action)) {
            collectAllStations();
        } else if (ACTION_SINGLE.equals(action)) {
            final String target = intent.getStringExtra(EXTRA_STATION);
            collectSingleStation(target);
        }
    }

    private void collectAllStations() {
        DataBaseFacade dbf = new DataBaseFacade(getBaseContext());

        ArrayList<String> stations = dbf.getActiveStations();
        StationModel favoriteStation = dbf.getFavoriteStation();

        collection(stations, favoriteStation, dbf);
    }

    private void collectSingleStation(String target) {
        DataBaseFacade dbf = new DataBaseFacade(getBaseContext());

        ArrayList<String> stations = new ArrayList<String>();
        stations.add(target);

        collection(stations, null, dbf);
    }

    /**
     * collect each station and save results
     * if favorite station, generate broadcast intent
     * @param stations
     * @param favoriteStation
     * @param dbf
     */
    private void collection(ArrayList<String> stations, StationModel favoriteStation, DataBaseFacade dbf) {
        try {
            WxXmlCollection wxc = new WxXmlCollection();

            for (String station:stations) {
                ObservationModel model = wxc.performCollection(getBaseContext(), station);
                if (model != null) {
                    dbf.newStation(model);
                    dbf.newObservation(model);

                    if ((favoriteStation != null) && (favoriteStation.getIdentifier().equals(model.getIdentifier()))) {
                        Intent observationIntent = new Intent(Constant.FRESH_OBSERVATION);
                        sendBroadcast(observationIntent);
                    }
                }
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setNextAlarm() {
        UserPreferenceHelper uph = new UserPreferenceHelper();
        int delay = uph.getPollInterval(this);
        Log.d(LOG_TAG, "current delay:" + delay);

        Time timeNow = Utility.timeNow();
        Time timeAlarm = new Time();
        timeAlarm.set(timeNow.toMillis(Constant.IGNORE_DST) + (delay * 60 * 1000L));
        Log.d(LOG_TAG, "next alarm:" + timeAlarm);

        Intent ii = new Intent(this, CollectionService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, ii, 0);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC, timeAlarm.toMillis(Constant.IGNORE_DST), pi);
    }
}
