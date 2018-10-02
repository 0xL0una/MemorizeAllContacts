package tv.louna.memorizeallcontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tv.louna.memorizeallcontacts.R;

public class Game extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
    }
    protected String check(){

        return "0100010101";
    }
}
