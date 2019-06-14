package arpit.com.farmis;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Alert extends AppCompatActivity {

    private MediaPlayer player = new MediaPlayer();
    private Vibrator vibrator;
    Ringtone ringtone;
    FloatingActionButton fab;
    TextView textView;
    String trackerName, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setStatusBarTranslucent(false);

        fab = findViewById(R.id.navigation_image);
        final Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sh);
        fab.startAnimation(shake);

        textView = findViewById(R.id.danger_textView);


        textView.setText(getString(R.string.alert_text));

        startRinging();
    }

    private void startRinging() {
        //Playing ringtone sound
        /*Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(this, uri);
        ringtone.play();*/

        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
        player = MediaPlayer.create(this,
                Settings.System.DEFAULT_RINGTONE_URI);
        try {
            player.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        final long[] vibrationPattern = new long[]{0, 702, 1000};

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator != null) {
            vibrator.vibrate(vibrationPattern, 0);
        }

    }

    private void stopRinging(){
        player.stop();
        vibrator.cancel();
        finish();
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void navigate(View view) {
        stopRinging();

        /*Double myLatitude = 12.969709;
        Double myLongitude = 79.155681;*/

        ///String[] a = message.replace("Help! :","").split(",");

        String urlAddress = "http://maps.google.com/maps?q=12.970892,79.163873(SJT Tracker)&iwloc=A&hl=es";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
        startActivity(intent);

    }
}
