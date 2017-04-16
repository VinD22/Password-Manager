package manager.password.app.v.passwordmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import io.realm.Realm;
import model.AccountDetails;

/**
 * See and Edit Account Details!
 */

public class EditAccountDetails extends AppCompatActivity {


    private Button mUpdateAccountDetails;
    private ImageButton mPasswordGenerator;
    private EditText mTitle, mUsername, mPassword, mAdditionalInfo;
    private Toolbar mToolbar;

    int accountId = 0;

    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_details);

        realm = Realm.getDefaultInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mUpdateAccountDetails = (Button) findViewById(R.id.update_account_details);
        mPasswordGenerator = (ImageButton) findViewById(R.id.password_generator);
        mTitle = (EditText) findViewById(R.id.title);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mAdditionalInfo = (EditText) findViewById(R.id.additional_info);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(R.string.update_account_details);
            getSupportActionBar().setTitle(R.string.update_account_details);
            // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            Toast.makeText(this, "Error in intents! (Contact Developer)", Toast.LENGTH_SHORT).show();
        } else {
            accountId = extras.getInt("id");

            AccountDetails tempAccountDetails = realm.where(AccountDetails.class).equalTo("id", accountId).findFirst();
            realm.beginTransaction();
            mTitle.setText(tempAccountDetails.getTitle());
            mUsername.setText(tempAccountDetails.getUsername() + "");
            mPassword.setText(tempAccountDetails.getPassword() + "");
            mAdditionalInfo.setText(tempAccountDetails.getAdditionalData());
            realm.commitTransaction();

        }

        mPasswordGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(EditAccountDetails.this, "Pending - Password Generator", Toast.LENGTH_SHORT).show();

            }
        });

        mUpdateAccountDetails.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(EditAccountDetails.this, R.string.empty_fields , Toast.LENGTH_SHORT).show();
                } else {
                    // Save Account Details into Realm!

                    realm.beginTransaction();

                    AccountDetails editAccountDetails = realm.where(AccountDetails.class).equalTo("id", accountId).findFirst();
                    editAccountDetails.setId(accountId);
                    editAccountDetails.setTitle(title);
                    editAccountDetails.setUsername(username);
                    editAccountDetails.setPassword(password);
                    editAccountDetails.setAdditionalData(additionalInfo + "");

                    realm.commitTransaction();

                    // Toast.makeText(EditAccountDetails.this, "Details Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditAccountDetails.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }



            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_product_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {

            case R.id.delete:

                // Show Alert Dialog to confirm to delete this product.
                // On clicking yes, delete the product, on Clicking no, hide the alert dialog!
//
//                AlertDialog.Builder builder =
//                        new AlertDialog.Builder(this, R.style.MyDialogTheme);

                new AlertDialog.Builder(EditAccountDetails.this, R.style.MyDialogTheme)
                        .setTitle(R.string.delete_account_details)
                        .setMessage(R.string.confirm_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                                // Realm realm = Realm.getInstance(EditAccountDetails.this);
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                AccountDetails editAccountDetails = realm.where(AccountDetails.class).equalTo("id", accountId).findFirst();
                                editAccountDetails.deleteFromRealm();
                                realm.commitTransaction();

                                finish();

                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
