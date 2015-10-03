package com.majeedr.tanwih.database.contract;

import android.provider.BaseColumns;

/**
 * Created by abdillah on 03/10/15.
 */
public final class TanwihContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TanwihContract() {}

    /* Inner class that defines the table contents */
    public static abstract class TanwihEntry implements BaseColumns {
        public static final String TABLE_NAME = "tanwih";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_LINKS = "links";

        public static final String COLUMN_NAME_INSYNC = "in_sync";
        public static final String COLUMN_NAME_OPERATION = "operation";

        public enum EntryOperation {
            ADD(0),
            DELETE(1),
            UPDATE(2);

            private final int value;
            private EntryOperation(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }
        };
    }
}
