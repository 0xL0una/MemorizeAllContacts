package tv.louna.memorizeallcontacts;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;



public class MainActivity extends AppCompatActivity {

    Button startGame, faq, settings, help;

    private final static int REQUEST_CODE_ASK_PERMISSION = 1;
    private static final String REQUIRED_SDK_PERMISSION =  Manifest.permission.READ_CONTACTS;

    protected void checkPermissions() {
        boolean missed = false;
        final int result = ContextCompat.checkSelfPermission(this, REQUIRED_SDK_PERMISSION);
        if (result != PackageManager.PERMISSION_GRANTED) {
            missed=true;
        }

        if (missed) {
            // request all missing permissions
            ActivityCompat.requestPermissions(this, new String[]{REQUIRED_SDK_PERMISSION}, REQUEST_CODE_ASK_PERMISSION);
        } else {
            final int[] grantResults = new int[1];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSION, new String[]{REQUIRED_SDK_PERMISSION}, grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSION:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Required permission to read contacts not granted,, exiting ...", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        startGame = (Button) findViewById(R.id.start_game_btn);
        faq = (Button) findViewById(R.id.how_to_play_btn);
        settings = (Button) findViewById(R.id.settings_btn);
        help = (Button)findViewById(R.id.help_btn);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
                Intent fp = new Intent(getApplicationContext(), ChooseContacts.class);
                startActivity(fp);
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp = new Intent(getApplicationContext(), Faq.class);
                startActivity(fp);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp = new Intent(getApplicationContext(), Settings.class);
                startActivity(fp);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp = new Intent(getApplicationContext(), Faq.class);
                startActivity(fp);
            }
        });

    }

}
