package manager.password.app.v.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Random;

import io.realm.Realm;
import model.AccountDetails;

/**
 * Activity to add new account details
 */

public class AddAccountDetail extends AppCompatActivity {

    private Button mAddAccountDetails;
    private ImageButton mPasswordGenerator;
    private EditText mTitle, mUsername, mPassword, mAdditionalInfo;
    private Toolbar mToolbar;

    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account_details);

        realm = Realm.getDefaultInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddAccountDetails = (Button) findViewById(R.id.add_account_details);
        mPasswordGenerator = (ImageButton) findViewById(R.id.password_generator);
        mTitle = (EditText) findViewById(R.id.title);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mAdditionalInfo = (EditText) findViewById(R.id.additional_info);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(R.string.add_account_details);
            getSupportActionBar().setTitle(R.string.add_account_details);
            // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPasswordGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String randomString = getRandomString();
                mPassword.setText(randomString);

            }
        });

        mAddAccountDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = mTitle.getText().toString();
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String additionalInfo = mAdditionalInfo.getText().toString();

                if(additionalInfo.isEmpty()) {
                    additionalInfo = "";
                }

                if(title.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AddAccountDetail.this, R.string.empty_fields , Toast.LENGTH_SHORT).show();
                } else {
                    // Save Account Details into Realm!

                    realm.beginTransaction();

                    AccountDetails newAccountDetails = realm.createObject(AccountDetails.class);
                    int nextKey = getNextKey();
                    newAccountDetails.setId(nextKey);
                    newAccountDetails.setTitle(capitalizeFirstLetter(title));
                    newAccountDetails.setUsername(username);
                    newAccountDetails.setPassword(password);
                    newAccountDetails.setAdditionalData(additionalInfo + "");

                    realm.commitTransaction();

                    Toast.makeText(AddAccountDetail.this, "Details Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAccountDetail.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }



            }
        });




    }

    private String getRandomString() {

        String randomChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()";
        StringBuilder randomString = new StringBuilder();
        Random rnd = new Random();
        while (randomString.length() < 11) { // length of the random string.
            int index = (int) (rnd.nextFloat() * randomChars.length());
            randomString.append(randomChars.charAt(index));
        }
        String saltStr = randomString.toString();
        return saltStr;

    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public int getNextKey()
    {
        try {
            return realm.where(AccountDetails.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e)
        { return 0; }
    }

}
