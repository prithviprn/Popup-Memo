package pe.kmh.popupmemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PopupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context c, Intent i) {
        i = new Intent(c, PopupService.class);
        c.startService(i);
    }

}
