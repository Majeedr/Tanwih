package com.majeedr.tanwih;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.majeedr.tanwih.database.TanwihDbHelper;
import com.majeedr.tanwih.database.contract.TanwihContract;

/**
 * @brief Quote manager : addition, check, remove, link, etc.
 * Show lists of quotes with it's brief information and has been designed mark.
 */
public class TanwihListActivity extends AppCompatActivity {
    public static String tanwihTitle = "tanwih-title";
    public static String tanwihAuthor = "tanwih-author";
    public static String tanwihContent = "tanwih-content";
    public static String tanwihOperation = "tanwih-operation";

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
            String title = data.getStringExtra(tanwihTitle);
            String content = data.getStringExtra(tanwihContent);
            TanwihContract.TanwihEntry.EntryOperation oper = TanwihContract.TanwihEntry.EntryOperation.values()[data.getIntExtra(tanwihOperation, 0)];

            if (content.isEmpty()) {
                Toast.makeText(getBaseContext(), "Item not saved", Toast.LENGTH_SHORT);
                return;
            }

            TanwihDbHelper mDbHelper = new TanwihDbHelper (getBaseContext());
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            // Add to database
            ContentValues entry = new ContentValues();
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_TITLE, title);
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_CONTENT, content);
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_INSYNC, 0);
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_OPERATION, TanwihContract.TanwihEntry.EntryOperation.ADD.getValue());

            long rowId;

            // Check operation
            switch (oper) {
                case ADD: {
                    rowId = db.insert(
                            TanwihContract.TanwihEntry.TABLE_NAME,
                            "null",
                            entry
                    );
                    break;
                }
                case DELETE: {
                    break;
                }
                case UPDATE: {
                    break;
                }
            }
        }
    }
}
