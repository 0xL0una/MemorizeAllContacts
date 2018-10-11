package tv.louna.memorizeallcontacts;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import tv.louna.memorizeallcontacts.R;

public class Settings extends Activity {
    Button save_btn;
    EditText time_limit;
    static SharedPreferences settings;
    SharedPreferences.Editor sEditor;
    String time_limit_pref;
    Switch enable_sound, enable_music;
    boolean enable_sound_pref, enable_music_pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        save_btn = (Button) findViewById(R.id.save_btn);
        time_limit = (EditText) findViewById(R.id.time_limit_edittext);
        enable_sound = (Switch) findViewById(R.id.enable_sounds);
        enable_music = (Switch) findViewById(R.id.enable_music);

//        SharedPreferences settings_time_limit = this.getSharedPreferences("settings.time_limit", Context.MODE_PRIVATE);
//        SharedPreferences settings_number_contacts = this.getSharedPreferences("settings.number_contacts", Context.MODE_PRIVATE);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        sEditor = settings.edit();

        time_limit_pref = getTimeLimit();
        enable_sound_pref = getSoundState();
        enable_music_pref = getMusicState();


        time_limit.setText(time_limit_pref);
        enable_sound.setChecked(enable_sound_pref);
        enable_music.setChecked(enable_music_pref);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEditor.putString("time_limit", String.valueOf(time_limit.getText()));
                sEditor.commit();
                Intent fp = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(fp);
            }
        });

        enable_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sEditor.putBoolean("enable_sound",true);
                }
                else {
                    sEditor.putBoolean("enable_sound",false);
                }
                sEditor.commit();
            }
        });

        enable_music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sEditor.putBoolean("enable_music",true);
                }
                else {
                    sEditor.putBoolean("enable_music",false);
                }
                sEditor.commit();
            }
        });
    }

    public String getTimeLimit(){
        return settings.getString("time_limit","120");
    }

    public boolean getSoundState(){
        return settings.getBoolean("enable_sound",true);
    }

    public boolean getMusicState(){
        return settings.getBoolean("enable_music",true);
    }
}
