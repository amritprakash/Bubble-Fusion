package co.amrit.bubbler;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static co.amrit.bubbler.BubbleUtil.getRandomColor;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(SplashScreen.this, BubbleActivity.class);
        startActivity(i);
        finish();
    }
}
