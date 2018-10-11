package tv.louna.memorizeallcontacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class ChooseContacts extends Activity {
    Button letsplay;
    TextView username,choose_contact_title;
    ImageView userPic;
    private final int REQUEST_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_contacts);
        //choose_contact_title = (TextView) findViewById(R.id.choose_contact_title);
        username = (TextView)findViewById(R.id.username_txt);
        userPic = (ImageView)findViewById(R.id.user_pic);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        letsplay = (Button) findViewById(R.id.lets_play_btn);
        letsplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(username.getText()) != ""){
                    Intent fp = new Intent(getApplicationContext(), Game.class);
                    startActivity(fp);
                }

            }
        });
    }

    protected void putInformation(String name, String pic){
        username.setText(name);
        if(pic != null)
            userPic.setImageURI(Uri.parse(pic));
        //else
        //  set default image ...
    }

    protected boolean storeNum(String num, String name, String pic) {
        //TODO : store num in pref
        Game.correctNumber_txt = num.replace("(","").replace(")","").replace(" ","").replace("-","");
        Game.correctNumber_txt = Game.correctNumber_txt.substring(Game.correctNumber_txt.length()-6);
        Game.name_txt = name;
        Game.pic_txt = pic;
        return true;
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                                String num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                String name = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String pic =  numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));

                                //Toast.makeText(this, num + "\n" + name + "\n" + pic + "\n", Toast.LENGTH_LONG).show();
                                if (storeNum(num, name, pic)) {
                                    putInformation(name, pic);
                                } else {
                                    Toast.makeText(this, "Choose a number please !", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                    break;
                }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent fp = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(fp);
    }

}
