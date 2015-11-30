package com.majeedr.tanwih;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.majeedr.tanwih.activity.CollectionActivity;
import com.majeedr.tanwih.database.DatabaseHelper;
import com.majeedr.tanwih.database.contract.TanwihContract;

/**
 * Created by abdillah on 03/10/15.
 */
public class CollectionListAdapter extends BaseAdapter {

    Tanwih[] tanwihItems;
    Context context;
    View.OnClickListener listener;

    protected DatabaseHelper mDbHelper;
    protected SQLiteDatabase db;

    public CollectionListAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.tanwihItems = new Tanwih[0];
    }

    public CollectionListAdapter(Context context, Tanwih[] tanwihs) {
        this.context = context;
        this.tanwihItems = tanwihs;
        Log.i("tanwih.list.adapter", String.valueOf(tanwihs.length));
    }

    @Override
    public int getCount() {
        Log.i("tanwih.list.adapter", "Tanwih length is " + tanwihItems.length);
        return tanwihItems.length;
    }

    @Override
    public Object getItem(int i) {
        return tanwihItems[i];
    }

    @Override
    public long getItemId(int i) {
        return tanwihItems[i].id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.tanwih_listview_item, null);
        v.setTag(R.id.tag_tanwih_index, tanwihItems[i].id);
        v.setOnClickListener(listener);
        Log.d("item.id", "Assign an id to list item : " + tanwihItems[i].id);

        TextView title = (TextView) v.findViewById(R.id.tanwih_listview_item_title);
        TextView content = (TextView) v.findViewById(R.id.tanwih_listview_item_content);
        if (tanwihItems[i] != null) {
            title.setText(tanwihItems[i].title);
            content.setText(tanwihItems[i].content);
        }
        return v;
    }

    public void refreshData() {
        mDbHelper = new DatabaseHelper(this.context);
        String WHERE_NOT_DELETED_ENTRY = TanwihContract.TanwihEntry.COLUMN_NAME_OPERATION + " != "
                + /* DELETE */ "1";

        db = mDbHelper.getReadableDatabase();
        Cursor cur = db.query("tanwih",
                new String[]{
                        TanwihContract.TanwihEntry.COLUMN_NAME_ENTRY_ID,
                        TanwihContract.TanwihEntry.COLUMN_NAME_TITLE,
                        TanwihContract.TanwihEntry.COLUMN_NAME_CONTENT,
                        TanwihContract.TanwihEntry.COLUMN_NAME_AUTHOR
                }, WHERE_NOT_DELETED_ENTRY, null, null, null, null);

        this.tanwihItems = new Tanwih[cur.getCount()];

        Log.i("db.cursor", "Cursor count : " + cur.getCount());
        if (cur == null || cur.getCount() == 0) {
            notifyDataSetChanged();
            Log.i("db.cursor", "Empty cursor");
            return;
        }

        if (cur.moveToFirst()) {
            int i = 0;
            do {
                int id = cur.getInt(cur.getColumnIndex(TanwihContract.TanwihEntry.COLUMN_NAME_ENTRY_ID));
                String title = cur.getString(cur.getColumnIndex(TanwihContract.TanwihEntry.COLUMN_NAME_TITLE));
                String author = cur.getString(cur.getColumnIndex(TanwihContract.TanwihEntry.COLUMN_NAME_AUTHOR));
                String content = cur.getString(cur.getColumnIndex(TanwihContract.TanwihEntry.COLUMN_NAME_CONTENT));
                Log.i("db.value", String.valueOf(id));
                Tanwih tanw = new Tanwih(id, author, title, content);
                this.tanwihItems[i] = tanw;
                i++;
            } while (cur.moveToNext());
        }

        notifyDataSetChanged();

        db.close();
    }

    public long addData(ContentValues entry) {
        db = mDbHelper.getReadableDatabase();
        long rowId = db.insert(TanwihContract.TanwihEntry.TABLE_NAME, "null", entry);
        db.close();
        return rowId;
    }

    public void setTanwihItems(Tanwih[] items) {
        this.tanwihItems = items;
    }
}
