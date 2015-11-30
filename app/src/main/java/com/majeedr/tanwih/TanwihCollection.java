package com.majeedr.tanwih;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.majeedr.tanwih.database.DatabaseHelper;
import com.majeedr.tanwih.database.contract.TanwihContract;

import java.util.Collection;
import java.util.Iterator;

/**
 * Tanwih collection database synchronizer
 */
public class TanwihCollection implements Collection {
    Tanwih[] tanwihCollection;
    protected DatabaseHelper mDbHelper;
    protected SQLiteDatabase db;

    public TanwihCollection(Tanwih[] tanwihCollection) {
        this.tanwihCollection = tanwihCollection;
    }

    private Tanwih[] getCollection() {
        return tanwihCollection;
    }

    @Override
    public boolean add(Object i) {
        ContentValues entry = (ContentValues) i;
        db = mDbHelper.getReadableDatabase();
        long rowId = db.insert(TanwihContract.TanwihEntry.TABLE_NAME, "null", entry);
        db.close();
        return rowId != -1;
    }

    @Override
    public boolean addAll(Collection collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public Tanwih[] toArray(Tanwih[] objects) {
        mDbHelper = new DatabaseHelper(this.context);
        String WHERE_NOT_DELETED_ENTRY = TanwihContract.TanwihEntry.COLUMN_NAME_OPERATION + " != "
                + /* DELETE */ "1";

        db = mDbHelper.getReadableDatabase();
        Cursor cur = db.query(
                "tanwih",
                new String[]{
                        TanwihContract.TanwihEntry.COLUMN_NAME_ENTRY_ID,
                        TanwihContract.TanwihEntry.COLUMN_NAME_TITLE,
                        TanwihContract.TanwihEntry.COLUMN_NAME_CONTENT,
                        TanwihContract.TanwihEntry.COLUMN_NAME_AUTHOR
                }, WHERE_NOT_DELETED_ENTRY, null, null, null, null
        );

        this.tanwihCollection = new Tanwih[cur.getCount()];

        Log.i("db.cursor", "Cursor count : " + cur.getCount());
        if (cur == null || cur.getCount() == 0) {
            Log.i("db.cursor", "Empty cursor");
            return this.tanwihCollection;
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
                this.tanwihCollection[i] = tanw;
                i++;
            } while (cur.moveToNext());
        }

        db.close();

        return this.tanwihCollection;
    }

    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }

    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }
}
