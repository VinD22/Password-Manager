package manager.password.app.v.passwordmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.AccountDetailsAdapter;
import io.realm.Realm;
import model.AccountDetails;

public class MainActivity extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "Preferences";
    private static final String HAS_WATCHED_TUTORIAL = "HasWatchedTutorial";

    Realm realm;

    ArrayList<AccountDetails> listOfAccountDetails = new ArrayList<>();

    private Toolbar mToolbar;
    FloatingActionButton mAddAccountDetail;

    private RecyclerView recList;
    private AccountDetailsAdapter mAdapter;
    private EditText mSearchET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        boolean hasWatchedTutorial = prefs.getBoolean(HAS_WATCHED_TUTORIAL, false);
        if (!hasWatchedTutorial) {
            goToOnboardingActivity();
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddAccountDetail = (FloatingActionButton) findViewById(R.id.fab_add_account_details);
        recList = (RecyclerView) findViewById(R.id.account_details_list_recycler_view);
        mSearchET = (EditText) findViewById(R.id.search_for_account);

        mSearchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mAdapter.filter(charSequence.toString() + "");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(R.string.app_name);
            mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mAddAccountDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAccountDetail.class);
                startActivity(intent);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(layoutManager);
        recList.setHasFixedSize(true);

        recList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    mAddAccountDetail.hide();
                else if (dy < 0)
                    mAddAccountDetail.show();
            }
        });


    }

    private void goToOnboardingActivity() {

        Intent intent = new Intent(MainActivity.this, OnBoardingActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {

            case R.id.settings:
                // Toast.makeText(this, "Show Settings!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.info:
                Toast.makeText(MainActivity.this, "TODO : Show Popup", Toast.LENGTH_LONG).show();

            default:
                return super.onOptionsItemSelected(item);
        }


    }


    private void getProductsList() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        getProductsList();
    }

}
