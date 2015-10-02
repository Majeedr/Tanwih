package com.majeedr.tanwih;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @brief Quote manager : addition, check, remove, link, etc.
 * Show lists of quotes with it's brief information and has been designed mark.
 */
public class TanwihListActivity extends AppCompatActivity {
    public static String tanwihTitle = "tanwih-title";
    public static String tanwihContent = "tanwih-content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_tanwih_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tanwih_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_search) {
            // Show search bar
            // Focus on it
            return true;
        }

        if (id == R.id.action_add_item) {
            // Show add activity
            Intent add_item_new = new Intent(TanwihListActivity.this, TanwihTypeActivity.class);
            startActivityForResult(add_item_new, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            String title = data.getIntExtra(tanwihTitle);
            String content = data.getIntExtra(tanwihContent);

            // Add to database
        }
    }
}
