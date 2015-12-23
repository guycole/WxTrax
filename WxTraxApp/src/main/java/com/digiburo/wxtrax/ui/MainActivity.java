package com.digiburo.wxtrax.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.digiburo.wxtrax.R;
import com.digiburo.wxtraxlib.Constant;
import com.digiburo.wxtraxlib.db.DataBaseFacade;
import com.digiburo.wxtraxlib.utility.LegalOptionMenuType;

public class MainActivity extends AppCompatActivity implements StationListener {
    private ViewPager _viewPager;
    private WxPagerAdapter _adapter;

    /**
     * Display observation list fragment
     * @param rowId station rowId
     * @param tabTag parent tab
     */
    public void stationSelect(Long rowId, String tabTag) {
//        selectedStationId = rowId;
//        tabHelper.onStationSelect(rowId, tabTag);
    }

    /**
     * display delete dialog
     * @param rowId within stationTable
     */
    public void displayStationDeleteDialog(long rowId) {
//        deleteStationId = rowId;
//    DeleteDialogFragment ddf = DeleteDialogFragment.newInstance(R.string.dialog_delete_station_title, R.string.dialog_delete_station_message);
//    ddf.show(getFragmentManager(), "deleteDialog");
    }

    public void stationDeleteYes() {
        DataBaseFacade dbf = new DataBaseFacade(this);
  //      dbf.deleteStationById(deleteStationId);
 //       deleteStationId = -1L;
    }

    /**
     * cancel delete
     */
    public void stationDeleteNo() {
//        deleteStationId = -1L;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _adapter = new WxPagerAdapter();
        _viewPager = (ViewPager) findViewById(R.id.viewpager);
        _viewPager.setAdapter(_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
