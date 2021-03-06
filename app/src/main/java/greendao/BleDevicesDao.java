package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mylowcarbon.app.model.BleDevices;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BLE_DEVICES".
*/
public class BleDevicesDao extends AbstractDao<BleDevices, String> {

    public static final String TABLENAME = "BLE_DEVICES";

    /**
     * Properties of entity BleDevices.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", false, "NAME");
        public final static Property Address = new Property(1, String.class, "address", true, "ADDRESS");
        public final static Property Rssi = new Property(2, int.class, "rssi", false, "RSSI");
    }


    public BleDevicesDao(DaoConfig config) {
        super(config);
    }
    
    public BleDevicesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BLE_DEVICES\" (" + //
                "\"NAME\" TEXT," + // 0: name
                "\"ADDRESS\" TEXT PRIMARY KEY NOT NULL ," + // 1: address
                "\"RSSI\" INTEGER NOT NULL );"); // 2: rssi
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BLE_DEVICES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BleDevices entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(2, address);
        }
        stmt.bindLong(3, entity.getRssi());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BleDevices entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(2, address);
        }
        stmt.bindLong(3, entity.getRssi());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
    }    

    @Override
    public BleDevices readEntity(Cursor cursor, int offset) {
        BleDevices entity = new BleDevices( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // address
            cursor.getInt(offset + 2) // rssi
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BleDevices entity, int offset) {
        entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAddress(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setRssi(cursor.getInt(offset + 2));
     }
    
    @Override
    protected final String updateKeyAfterInsert(BleDevices entity, long rowId) {
        return entity.getAddress();
    }
    
    @Override
    public String getKey(BleDevices entity) {
        if(entity != null) {
            return entity.getAddress();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BleDevices entity) {
        return entity.getAddress() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
