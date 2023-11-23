package com.interstitial.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// Импортируйте необходимые классы:
import androidx.annotation.NonNull;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.LoadAdError;

public class Main extends Activity {

    Button button01;
    // Добавьте переменную для интерстициальной рекламы:
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        // Инициализация рекламы
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        Toast.makeText(Main.this, "Реклама загружена, можно показать", Toast.LENGTH_SHORT).show();
                        // Реклама загружена, можно показать
                        mInterstitialAd = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Toast.makeText(Main.this, "Обработка ошибки загрузки", Toast.LENGTH_SHORT).show();
                        // Обработка ошибки загрузки
                        mInterstitialAd = null;
                    }
                });

        // Настройка кнопки
        button01 = findViewById(R.id.button_01);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main.this, "showInterstitial", Toast.LENGTH_SHORT).show();
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Main.this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                @Override
                public void onAdDismissedFullScreenContent() {
                    Toast.makeText(Main.this, "Переход на другое активити после закрытия рекламы", Toast.LENGTH_SHORT).show();
                    // Переход на другое активити после закрытия рекламы
                    Intent intent = new Intent(Main.this, OneClass.class);
                    startActivity(intent);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Toast.makeText(Main.this, "Обработка ошибки показа", Toast.LENGTH_SHORT).show();
                    // Обработка ошибки показа
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Toast.makeText(Main.this, "Реклама показывается на полном экране", Toast.LENGTH_SHORT).show();
                    // Реклама показывается на полном экране
                    mInterstitialAd = null;
                }
            });
        } else {
            Toast.makeText(Main.this, "Реклама не загружена", Toast.LENGTH_SHORT).show();
            // Реклама не загружена, переход на другое активити
            Intent intent = new Intent(Main.this, OneClass.class);
            startActivity(intent);
        }
    }
}
