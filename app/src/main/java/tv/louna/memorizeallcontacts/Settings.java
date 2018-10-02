package tv.louna.memorizeallcontacts;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tv.louna.memorizeallcontacts.R;

public class Settings extends AppCompatActivity {
    Button save_btn;
    EditText time_limit, number_contacts;
    SharedPreferences settings;
    SharedPreferences.Editor sEditor;
    String time_limit_pref, number_contacts_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        save_btn = (Button) findViewById(R.id.save_btn);
        time_limit = (EditText) findViewById(R.id.time_limit_edittext);
        number_contacts = (EditText) findViewById(R.id.number_contacts_edittext);

        SharedPreferences settings_time_limit = this.getSharedPreferences("settings.time_limit", Context.MODE_PRIVATE);
        SharedPreferences settings_number_contacts = this.getSharedPreferences("settings.number_contacts", Context.MODE_PRIVATE);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        sEditor = settings.edit();

        time_limit_pref = settings.getString("time_limit","120");
        number_contacts_pref = settings.getString("number_contacts","5");

        time_limit.setText(time_limit_pref);
        number_contacts.setText(number_contacts_pref);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEditor.putString("time_limit", String.valueOf(time_limit.getText()));
                sEditor.putString("number_contacts", String.valueOf(number_contacts.getText()));
                sEditor.commit();
            }
        });
    }
}
