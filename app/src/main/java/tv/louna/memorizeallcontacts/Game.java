package tv.louna.memorizeallcontacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Game extends Activity {
    static String correctNumber_txt;
    static String name_txt;
    static String pic_txt;
    String guessedNumber;
    Button nums[];
    Button remove_btn, validate_btn;
    TextView cases[][];
    TextView inputs[];
    TextView time_remaining;
    int counter,currentLine,id;
    CountDownTimer cTimer;

    public int getTimeLimit(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        return Integer.parseInt(settings.getString("time_limit","120"));
    }

    public int time_to_seconds(String time){
        String[] tmp = time.split(":");
        return Integer.parseInt(tmp[0])*60 + Integer.parseInt(tmp[1]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        counter=0;
        currentLine=0;
        guessedNumber="";
        cTimer = null;
        initUI();

        startTimer(getTimeLimit());




        for (int i=0; i < 10; i++){
            nums[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (counter < 6) {
                        Button tmp = (Button) findViewById(v.getId());
                        inputs[counter].setText(tmp.getText());
                        guessedNumber+=tmp.getText();
                        System.out.println("Button "+v.getId()+" CLICKED ------ "+guessedNumber);
                        counter++;
                    }
                }
            });
        }




        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 0){
                    inputs[--counter].setText("");
                    guessedNumber = guessedNumber.substring(0,guessedNumber.length()-1);
                }

            }
        });

        validate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(this, "ENTER PRESSED", Toast.LENGTH_SHORT).show();
                System.out.println("ENTER PRESSED");
                inputs[0].setText("5");

                String check_number;
                if (counter==6 && guessedNumber.length() == 6) {

                    counter=0;

                    // Empty the inputs
                    for (int i = 0; i < 6; i++)
                        inputs[i].setText("");

                    // Write to the appropriate line
                    for (int i = 0; i < 6; i++) {

                        cases[currentLine][i].setText(""+guessedNumber.charAt(i));
                        doCheck();
                    }

                    // Colorize


                    currentLine++;

                    guessedNumber = "";
                }
            }
        });


    }

    @Override
    public void onBackPressed() {

    }

    public void initUI(){
        time_remaining = (TextView) findViewById(R.id.time_remaining);
        cases = new TextView[5][6];
        inputs = new TextView[6];
        nums = new Button[10];

        for (int i=0; i< 6; i++){
            id = getResources().getIdentifier("input"+(i+1), "id", getPackageName());
            inputs[i] = (TextView) findViewById(id);
        }

        for (int l=0; l < 5; l++)
            for (int c=0; c < 6; c++){
                id = getResources().getIdentifier("l"+(l+1)+"_c"+(c+1), "id", getPackageName());
                cases[l][c] = (TextView) findViewById(id);
            }

        for (int i=0; i < 10; i++) {
            id = getResources().getIdentifier("num" + i, "id", getPackageName());
            nums[i] = (Button) findViewById(id);
        }

        remove_btn = (Button) findViewById(R.id.remove_btn);
        validate_btn = (Button) findViewById(R.id.validate_btn);
    }

    void startTimer(int secs) {
        cTimer = new CountDownTimer(secs*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                time_remaining.setText(String.format("%02d", minutes)+":"+String.format("%02d", seconds));
            }
            public void onFinish() {
                Intent fp = new Intent(getApplicationContext(), YouLose.class);
                startActivity(fp);
            }
        };
        cTimer.start();
    }

    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

    protected void doCheck(){
        // get guessed number from textview ...
        String guessed = guessedNumber;
        String check = check(correctNumber_txt,guessed);
        //Toast.makeText(this, check, Toast.LENGTH_SHORT).show();
        for (int i=0;i<check.length();i++) {
            char c = check.charAt(i);
            switch (c){
                case '0':
                        //cases[currentLine][i].setBackgroundColor(Color.parseColor("#ff0000"));
                        cases[currentLine][i].setBackground(getDrawable(R.drawable.red_case));
                    break;
                case '1':
                        //cases[currentLine][i].setBackgroundColor(Color.parseColor("#0000ff"));
                        cases[currentLine][i].setBackground(getDrawable(R.drawable.yellow_case));
                    break;
                case '2':
                        //cases[currentLine][i].setBackgroundColor(Color.parseColor("#00ff00"));
                        cases[currentLine][i].setBackground(getDrawable(R.drawable.green_case));
                    break;
            }
        }
        if (currentLine==4 && !check.equals("222222")){
            for (int i=0; i < 10; i++)
              nums[i].setEnabled(false);
            Intent fp = new Intent(getApplicationContext(), YouLose.class);
            startActivity(fp);
        }
        else if (check.equals("222222")){
            Intent fp = new Intent(getApplicationContext(), YouWin.class);
            fp.putExtra("TIME_WIN", getTimeLimit()-time_to_seconds(String.valueOf(time_remaining.getText())));
            fp.putExtra("ATTEMPTS",currentLine+1);
            startActivity(fp);
        }
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
