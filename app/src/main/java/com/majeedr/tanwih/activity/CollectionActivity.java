package com.majeedr.tanwih.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.majeedr.tanwih.CollectionListAdapter;
import com.majeedr.tanwih.R;
import com.majeedr.tanwih.database.DatabaseHelper;
import com.majeedr.tanwih.database.contract.TanwihContract;

/**
 * @brief Quote manager : addition, check, remove, link, etc.
 * Show lists of quotes with it's brief information and has been designed mark.
 */
public class CollectionActivity extends AppCompatActivity implements View.OnClickListener {
    public static String tanwihTitle = "tanwih-title";
    public static String tanwihAuthor = "tanwih-author";
    public static String tanwihContent = "tanwih-content";
    public static String tanwihOperation = "tanwih-operation";

    protected DatabaseHelper mDbHelper;
    protected SQLiteDatabase db;

    ListView tanwihCollection;

    TextView title_tv;
    TextView content_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_tanwih_list);
        tanwihCollection = (ListView) findViewById(R.id.tanwih_listview);

        setupWidget();
        setupDatabase();
        setupList();
    }

    private void setupWidget() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.list_item: {
                // Showing tanwih activity
                Intent edit_item = new Intent(CollectionActivity.this, ShowActivity.class);
                // TODO: Make constant in enum
                // Get id attached to view as tag
                Log.d("item.id", "ID Tanwih: " + v.getTag(R.id.tag_tanwih_index));
                edit_item.putExtra("item.id", (int) v.getTag(R.id.tag_tanwih_index));
                startActivity(edit_item);
                break;
            }
        }
        return;
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
            Intent add_item_new = new Intent(CollectionActivity.this, WriterActivity.class);
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

            // Add to database
            ContentValues entry = new ContentValues();
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_TITLE, title);
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_AUTHOR, author);
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_CONTENT, content);
            entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_INSYNC, 0);

            db = mDbHelper.getWritableDatabase();
            CollectionListAdapter adapter = ((CollectionListAdapter) tanwihCollection.getAdapter());
            // Check operation
            switch (oper) {
                case ADD: {
                    entry.put(TanwihContract.TanwihEntry.COLUMN_NAME_OPERATION, TanwihContract.TanwihEntry.EntryOperation.ADD.getValue());
                    adapter.addData(entry);
                    break;
                }
                case DELETE: {
                    break;
                }
                case UPDATE: {
                    break;
                }
            }

            Log.e("Tanwih List", "ID " + tanwihCollection.getId());
            adapter.refreshData();
        }
    }

    private void setupDatabase() {
        mDbHelper = new DatabaseHelper(this);
    }

    private void setupList() {
        CollectionListAdapter adapter = new CollectionListAdapter(this, this);
        adapter.refreshData();
        tanwihCollection.setAdapter(adapter);
        Log.i("Tanwih List", "Adapter has been set!");
        Log.i("Tanwih List", "Adapter :" + tanwihCollection.getAdapter().toString());
    }
}
