package com.example.zxingscanwithlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private ImageView imgScan;
    private TextView scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgScan = findViewById(R.id.imgScan);
        scanResult = findViewById(R.id.txtResult);
        imgScan.setOnClickListener(imgScanOnClickListener);
    }
    private View.OnClickListener imgScanOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                    .setPrompt("請將條碼放置框內，即可自動掃描")
                    .setOrientationLocked(false)
                    .setCaptureActivity(ScanActivity.class)
                    .initiateScan();
        }
    };

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestcode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "取消掃描",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(),
                        Toast.LENGTH_LONG).show();
                scanResult.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestcode, resultCode, data);
        }
    }
}