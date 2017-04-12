package manager.password.app.v.passwordmanager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;



/*
 * 1. Can see details about the app
 * 2. Can go to On-Boarding Activity
 * 3. Can Update Balance
 * 4. Can share, rate or provice feedback of the app
 */


public class SettingsActivity extends AppCompatActivity {

    private Button mSeeAppOnBoarding, mUpdateBalance, mRateApp, mShareApp, mCheckMyOtherApps;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSeeAppOnBoarding = (Button) findViewById(R.id.see_app_onboarding);
        mUpdateBalance = (Button) findViewById(R.id.update_balance);
        mRateApp = (Button) findViewById(R.id.rate_app);
        mShareApp = (Button) findViewById(R.id.share_app);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCheckMyOtherApps = (Button) findViewById(R.id.other_apps_by_me);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(R.string.settings);
            getSupportActionBar().setTitle(R.string.settings);
            // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mSeeAppOnBoarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, OnBoardingActivity.class);
                startActivity(intent);
            }
        });

        mUpdateBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, UpdateBalance.class);
                startActivity(intent);
            }
        });

        mShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                    String sAux = getString(R.string.share_text);
                    sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName();
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, getString(R.string.share)));
                } catch (Exception e) {
                    e.toString();
                }

            }
        });

        mRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            }
        });

        mCheckMyOtherApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=V.Apps")));

            }
        });

    }
}
