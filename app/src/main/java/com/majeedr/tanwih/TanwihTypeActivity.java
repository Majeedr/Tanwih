package com.majeedr.tanwih;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class TanwihTypeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanwih_type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tanwih_type, menu);
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

        if (id == R.id.action_save) {
            // Fetch tanwih
            String title = ((EditText) findViewById(R.id.tanwih_title)).getText().toString();
            String content = ((EditText) findViewById(R.id.tanwih_content)).getText().toString();

            // Call event
            onTanwihSaved(title, content);
        }

        return super.onOptionsItemSelected(item);
    }

    private void onTanwihSaved(String title, String content) {
        Intent tanwih = new Intent();
        tanwih.putExtra(TanwihListActivity.tanwihTitle, title);
        tanwih.putExtra(TanwihListActivity.tanwihContent, content);
        setResult(RESULT_OK, tanwih);
        finish();
    }
}
