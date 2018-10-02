package tv.louna.memorizeallcontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import tv.louna.memorizeallcontacts.R;

public class MainActivity extends AppCompatActivity  {

    Button startGame, faq, settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        startGame = (Button) findViewById(R.id.start_game_btn);
        faq = (Button) findViewById(R.id.how_to_play_btn);
        settings = (Button) findViewById(R.id.settings_btn);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp=new Intent(getApplicationContext(),Game.class);
                startActivity(fp);
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp=new Intent(getApplicationContext(),Faq.class);
                startActivity(fp);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp=new Intent(getApplicationContext(),Settings.class);
                startActivity(fp);
            }
        });

    }
//    @Override
//    protected void onClick(View v){
//        Intent fp = null;
//        switch (v.getId()){
//            case R.id.start_game_btn:
//                fp=new Intent(getApplicationContext(),Game.class);
//                break;
//            case R.id.how_to_play_btn:
//                fp=new Intent(getApplicationContext(),Faq.class);
//                break;
//            case R.id.settings_btn:
//                fp=new Intent(getApplicationContext(),Settings.class);
//                break;
//        }
//        if (fp != null)
//            startActivity(fp);
//    }
}
