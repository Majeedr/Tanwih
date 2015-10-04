package com.majeedr.tanwih;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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

    protected TanwihDbHelper mDbHelper;
    protected SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_tanwih_list);
        setupDatabase();
        setupList();
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
            String author = data.getStringExtra(tanwihAuthor);
            TanwihContract.TanwihEntry.EntryOperation oper = TanwihContract.TanwihEntry.EntryOperation.values()[data.getIntExtra(tanwihOperation, 0)];

            if (content.isEmpty()) {
                Toast.makeText(this, "Item not saved", Toast.LENGTH_SHORT);
                return;
            }

            db = mDbHelper.getWritableDatabase();
            // Add to database
            ContentValues entry = new ContentValues();
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_TITLE, title);
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_AUTHOR, author);
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_CONTENT, content);
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_INSYNC, 0);

            long rowId;

            // Check operation
            switch (oper) {
                case ADD: {
                    entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_OPERATION, TanwihContract.TanwihEntry.EntryOperation.ADD.getValue());
                    rowId = db.insert(
                            TanwihContract.TanwihEntry.TABLE_NAME,
                            "null",
                            entry
                    );
                    db.close();
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

    private void setupDatabase() {
        mDbHelper = new TanwihDbHelper (this);
    }

    private void setupList() {
        String WHERE_NOT_DELETED_ENTRY = TanwihContract.TanwihEntry.COLUMN_NAME_OPERATION + " != "
                + TanwihContract.TanwihEntry.EntryOperation.DELETE.getValue();

        ListView tanwihList = (ListView) findViewById(R.id.tanwih_listview);
        db = mDbHelper.getReadableDatabase();
        Cursor cur = db.query("tanwih",
                              new String[]{
                                      TanwihContract.TanwihEntry.COLUMN_NAME_ENTRY_ID,
                                      TanwihContract.TanwihEntry.COLUMN_NAME_TITLE,
                                      TanwihContract.TanwihEntry.COLUMN_NAME_CONTENT,
                                      TanwihContract.TanwihEntry.COLUMN_NAME_AUTHOR
                              }, WHERE_NOT_DELETED_ENTRY, null, null, null, null);

        Log.i("db.cursor", "Cursor count : " + cur.getCount());
        if (cur == null || cur.getCount() == 0) {
            Log.i("db.cursor", "Empty cursor");
            return;
        }

        Tanwih[] tanwihs = new Tanwih[cur.getCount()];

        if (cur.moveToFirst()) {
            int i = 0;
            do {
                int id = cur.getInt(cur.getColumnIndex(TanwihContract.TanwihEntry.COLUMN_NAME_ENTRY_ID));
                String title = cur.getString(cur.getColumnIndex(TanwihContract.TanwihEntry.COLUMN_NAME_TITLE));
                String author = cur.getString(cur.getColumnIndex(TanwihContract.TanwihEntry.COLUMN_NAME_AUTHOR));
                String content = cur.getString(cur.getColumnIndex(TanwihContract.TanwihEntry.COLUMN_NAME_CONTENT));
                Log.i("db.value", String.valueOf(id));
                Tanwih tanw = new Tanwih(id, author, title, content);
                tanwihs[i] = tanw;
                i++;
            } while (cur.moveToNext());
        }

        db.close();

        TanwihListAdapter adapter = new TanwihListAdapter(this, tanwihs);
        tanwihList.setAdapter(adapter);
    }
}
