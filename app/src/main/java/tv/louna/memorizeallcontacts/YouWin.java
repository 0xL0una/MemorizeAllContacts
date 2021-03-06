package tv.louna.memorizeallcontacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class YouWin extends Activity {
    Button back,play_again;
    TextView time_win, attempts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.you_win);
        back = (Button) findViewById(R.id.back_btn);
        play_again = (Button) findViewById(R.id.play_again_btn);
        time_win = (TextView) findViewById(R.id.time_win_label);
        attempts = (TextView) findViewById(R.id.atempts_label);

        String time_win_to_show = String.valueOf(getIntent().getExtras().getInt("TIME_WIN"));
        String attempts_to_show = String.valueOf(getIntent().getExtras().getInt("ATTEMPTS"));

        time_win.setText(time_win_to_show+"s");
        attempts.setText(attempts_to_show+" tries");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(fp);
            }
        });

        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp = new Intent(getApplicationContext(),ChooseContacts.class);
                startActivity(fp);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }


}
