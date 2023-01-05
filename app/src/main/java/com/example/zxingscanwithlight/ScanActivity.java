package com.example.zxingscanwithlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScanActivity extends AppCompatActivity {
    private DecoratedBarcodeView dbvCustom;
    private ImageView switch_flashlight;
    private CaptureManager captureManager;
    private boolean tag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan);
        dbvCustom= findViewById(R.id.zxing_barcode_scanner);
        switch_flashlight = findViewById(R.id.switch_flashlight);
        switch_flashlight.setOnClickListener(lightOnClickListener);

        //若沒有閃光燈功能，則將按鈕隱藏
        if(!hasFlash()){
            switch_flashlight.setVisibility(View.INVISIBLE);
        }

        captureManager = new CaptureManager(this, dbvCustom);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return dbvCustom.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    //判斷是否有閃光燈功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private View.OnClickListener lightOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (tag == false){
                switch_flashlight.setImageResource(R.drawable.open);
                dbvCustom.setTorchOn();
                tag = true;
            }
            else if (tag == true){
                switch_flashlight.setImageResource(R.drawable.close);
                dbvCustom.setTorchOff();
                tag = false;
            }
        }
    };
}