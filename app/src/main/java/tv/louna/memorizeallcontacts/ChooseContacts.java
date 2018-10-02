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
import android.widget.Toast;

public class ChooseContacts extends AppCompatActivity {
    Button chooseContact;
    private final int REQUEST_CODE=99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_contacts);
        chooseContact = (Button) findViewById(R.id.choose_btn);
        chooseContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check contact reads permission
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    protected boolean storeNum(String num){
        //TODO : store num in pref
        Game.correctNumber_txt=num;
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
                                //Toast.makeText(this, num, Toast.LENGTH_LONG).show();
                                if(storeNum(num)){
                                    Intent fp=new Intent(getApplicationContext(),Game.class);
                                    startActivity(fp);
                                }else{
                                    Toast.makeText(this, "Choose a number please !", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                    break;
                }
        }
    }

}
