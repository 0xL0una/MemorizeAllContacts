package tv.louna.memorizeallcontacts;


import android.content.Context;
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

public class Settings extends AppCompatActivity {
    Button save_btn;
    EditText time_limit;
    static SharedPreferences settings;
    SharedPreferences.Editor sEditor;
    String time_limit_pref;
    Switch enable_sound;
    boolean enable_sound_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        save_btn = (Button) findViewById(R.id.save_btn);
        time_limit = (EditText) findViewById(R.id.time_limit_edittext);
        enable_sound = (Switch) findViewById(R.id.enable_sound);

        SharedPreferences settings_time_limit = this.getSharedPreferences("settings.time_limit", Context.MODE_PRIVATE);
        SharedPreferences settings_number_contacts = this.getSharedPreferences("settings.number_contacts", Context.MODE_PRIVATE);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        sEditor = settings.edit();

        time_limit_pref = getTimeLimit();
        enable_sound_pref = getSoundState();

        time_limit.setText(time_limit_pref);
        enable_sound.setChecked(enable_sound_pref);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEditor.putString("time_limit", String.valueOf(time_limit.getText()));
                sEditor.commit();
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
    }

    public static String getTimeLimit(){
        return settings.getString("time_limit","120");
    }

    public static boolean getSoundState(){
        return settings.getBoolean("enable_sound",true);
    }
}
