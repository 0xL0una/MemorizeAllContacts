package tv.louna.memorizeallcontacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class YouLose extends Activity {
    Button back,retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.you_lose);
        back = (Button) findViewById(R.id.back_btn);
        retry = (Button) findViewById(R.id.retry_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(fp);
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp = new Intent(getApplicationContext(),Game.class);
                startActivity(fp);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
