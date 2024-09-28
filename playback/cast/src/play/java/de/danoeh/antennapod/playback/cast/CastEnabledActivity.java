package de.danoeh.antennapod.playback.cast;

import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Activity that allows for showing the MediaRouter button whenever there's a cast device in the
 * network.
 */
public abstract class CastEnabledActivity extends AppCompatActivity {
    private boolean canCast = false;
    private boolean hasCastButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        canCast = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS;
        if (canCast) {
            try {
                CastContext.getSharedInstance(this);
            } catch (Exception e) {
                e.printStackTrace();
                canCast = false;
            }
        }
    }

    public void requestCastButton(Menu menu) {
        if (!canCast) {
            return;
        }

        if (!hasCastButton) {
            getMenuInflater().inflate(R.menu.cast_button, menu);
            CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.media_route_menu_item);
        }
        hasCastButton = true;
    }
}
