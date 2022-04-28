package com.jesusguzman.pokedexapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreenActivity extends AppCompatActivity {

    private GifImageView gifImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        gifImageView = (GifImageView) findViewById(R.id.gifSplashScreen);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar1);
        progressBar.setVisibility(View.VISIBLE);

        try {
            InputStream inputStream = getAssets().open("splashscreen.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException exception) {
            System.out.println(exception);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashScreenActivity.this.startActivity(
                        new Intent(
                                SplashScreenActivity.this, MainActivity.class));
                SplashScreenActivity.this.finish();
            }
        }, 5000);

    }
}