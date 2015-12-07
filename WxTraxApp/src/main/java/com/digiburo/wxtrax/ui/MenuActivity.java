package com.digiburo.wxtrax.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.digiburo.wxtrax.R;
import com.digiburo.wxtrax.audio.AudioFacade;
import com.digiburo.wxtraxlib.utility.LegalOptionMenuType;

/**
 * support for menu selection
 */
public class MenuActivity extends Activity {

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case android.R.id.home:
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu);

    AudioFacade audioFacade = new AudioFacade();
    audioFacade.playTransitionCue(this);

    ActionBar actionBar = getActionBar();
    actionBar.setHomeButtonEnabled(true);

    Intent intent = getIntent();
    if (intent == null) {
      finish();
    } else {
      Fragment fragment = null;

      String action = intent.getAction();
      LegalOptionMenuType menuType = LegalOptionMenuType.discoverMatchingEnum(action);
      switch(menuType) {
        case ABOUT:
          fragment = new AboutFragment();
          break;
        case PREFERENCE:
          fragment = new SettingFragment();
          break;
        default:
          finish();
      }

      if (fragment != null) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutFragment, fragment);
        fragmentTransaction.commit();
      }
    }
  }
}