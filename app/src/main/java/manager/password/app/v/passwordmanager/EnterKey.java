package manager.password.app.v.passwordmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterKey extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "Preferences";
    private static final String HAS_ENTERED_KEY = "HasEnteredKey";
    private static final String KEY = "Key";

    private EditText mKey;
    private Button mEnterKey;

    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_key);

        mKey = (EditText) findViewById(R.id.enter_key);
        mEnterKey = (Button) findViewById(R.id.enter);

        // Fetch saved key from Shared Preferences!

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        boolean hasEnteredKey = prefs.getBoolean(HAS_ENTERED_KEY, false);
        if (!hasEnteredKey) {
            Intent intent = new Intent(EnterKey.this, SaveKey.class);
            startActivity(intent);
            finish();
        } else {
            key = prefs.getString(KEY, "");
        }

        mEnterKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredKey = mKey.getText().toString();
                if(enteredKey.isEmpty()) {
                    Toast.makeText(EnterKey.this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
                } else if(!enteredKey.equals(key)) { // Compare Key with saved key!
                    Toast.makeText(EnterKey.this, R.string.incorrect_key_entered, Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(EnterKey.this, "Great, Correct Key Entered!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EnterKey.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }



            }
        });


    }
}
