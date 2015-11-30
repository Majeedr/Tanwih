package com.majeedr.tanwih.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.majeedr.tanwih.R;

/**
 * An activity manage addition and revision of tanwih.
 */
public class WriterActivity extends AppCompatActivity {

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
            // Get tanwih data from widget
            String title = ((EditText) findViewById(R.id.tanwih_title)).getText().toString();
            String content = ((EditText) findViewById(R.id.tanwih_content)).getText().toString();
            // TODO: Get owner username
            String author = "owner";

            // Triger saving
            onSaveTanwih(title, content, author);
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSaveTanwih(String title, String content, String author) {
        Intent tanwih = new Intent();
        tanwih.putExtra(CollectionActivity.tanwihOperation, "add");
        tanwih.putExtra(CollectionActivity.tanwihTitle, title);
        tanwih.putExtra(CollectionActivity.tanwihAuthor, author);
        tanwih.putExtra(CollectionActivity.tanwihContent, content);
        setResult(RESULT_OK, tanwih);
        finish();
    }
}
