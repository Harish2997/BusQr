package team.har.busqr;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import team.har.busqr.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(5*1000);

                    Intent i=new Intent(getBaseContext(),ScanActivity.class);
                    startActivity(i);

                    finish();

                } catch (Exception e) {

                }
            }
        };


        background.start();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
