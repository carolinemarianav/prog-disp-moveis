package com.example.previsaodotempo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.previsaodotempo.R;
import com.google.zxing.integration.android.IntentIntegrator;

public class CityScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_scan);

        var scanner = new IntentIntegrator(this);
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        scanner.setBeepEnabled(false);
        scanner.initiateScan();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            var result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            if (result != null && result.getContents() != null) {

                String woeid = result.getContents();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("WOEID", woeid);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        }
    }

}

