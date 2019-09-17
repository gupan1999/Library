package com.example.version1.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LENT_INFORMATION".
*/
public class LentInformationDao extends AbstractDao<LentInformation, Long> {

    public static final String TABLENAME = "LENT_INFORMATION";

    /**
     * Properties of entity LentInformation.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BookName = new Property(0, String.class, "bookName", false, "BOOK_NAME");
        public final static Property LentTime = new Property(1, String.class, "lentTime", false, "LENT_TIME");
        public final static Property Id = new Property(2, Long.class, "id", true, "_id");
    }


    public LentInformationDao(DaoConfig config) {
        super(config);
    }
    
    public LentInformationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LENT_INFORMATION\" (" + //
                "\"BOOK_NAME\" TEXT," + // 0: bookName
                "\"LENT_TIME\" TEXT," + // 1: lentTime
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT );"); // 2: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LENT_INFORMATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LentInformation entity) {
        stmt.clearBindings();
 
        String bookName = entity.getBookName();
        if (bookName != null) {
            stmt.bindString(1, bookName);
        }
 
        String lentTime = entity.getLentTime();
        if (lentTime != null) {
            stmt.bindString(2, lentTime);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(3, id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LentInformation entity) {
        stmt.clearBindings();
 
        String bookName = entity.getBookName();
        if (bookName != null) {
            stmt.bindString(1, bookName);
        }
 
        String lentTime = entity.getLentTime();
        if (lentTime != null) {
            stmt.bindString(2, lentTime);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(3, id);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2);
    }    

    @Override
    public LentInformation readEntity(Cursor cursor, int offset) {
        LentInformation entity = new LentInformation( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // bookName
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // lentTime
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LentInformation entity, int offset) {
        entity.setBookName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setLentTime(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LentInformation entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LentInformation entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LentInformation entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
