package tv.louna.memorizeallcontacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class Game extends AppCompatActivity {
    static String correctNumber_txt;
    TextView correctNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        correctNumber=(TextView)findViewById(R.id.number_txt);
        correctNumber.setText(correctNumber_txt);

    }
    protected String check(String correct, String guess){
        if(correct.length() != guess.length())
            return "ERROR !";
        char[] ret = new char[correct.length()];
        char[] correct_ = correct.toCharArray();
        char[] guess_ = guess.toCharArray();
        for(int i=0;i<correct.length();i++){
            if(correct_[i] == guess_[i] ){
                correct_[i]  = '#';
                ret[i] = '2';
            }else
                ret[i] = '0';
        }
        for(int i=0;i<correct.length();i++){
            if(ret[i] == '2')
                continue;
            int pos = String.valueOf(correct_).indexOf(guess_[i]);
            if(pos >= 0){
                ret[i] ='1';
                correct_[pos] ='#';
            }
        }
        return String.valueOf(ret);
    }

}
