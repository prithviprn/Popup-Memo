package pe.kmh.popupmemo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class PreferenceScreen extends PreferenceActivity {

    ListPreference list;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowCustomEnabled(true);
        addPreferencesFromResource(R.xml.preference);

        PackageInfo pi = null;
        try {
            pi = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e) { e.printStackTrace(); }

        String version = pi.versionName;
        int versionCode = pi.versionCode;
        Preference r = getPreferenceScreen().findPreference("Info");
        r.setSummary("Version " + version + " (Build " + versionCode + ")");
        list = (ListPreference) findPreference("color");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent i = new Intent(this, NoteMain.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
