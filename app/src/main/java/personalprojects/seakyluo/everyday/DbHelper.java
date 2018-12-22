package personalprojects.seakyluo.everyday;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Everyday.db";

    public static DbHelper instance;
    public static SQLiteDatabase db;

    private static ArrayList<OnDatabaseChangeListener> listeners = new ArrayList<>();

    public static void init(Context context){
        instance = new DbHelper(context, DB_NAME, null, 1);
    }

    private DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = getWritableDatabase();
    }

    public static void clearDB(){
        db.execSQL(User.DROP_TABLE);
        db.execSQL(Owns.DROP_TABLE);
        createDB();
        notifyListeners();
    }
    public static void createDB(){
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Owns.CREATE_TABLE);
    }
    public static ArrayList get(String table_name, String query){
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Object> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            switch (table_name){
                case User.TABLE_NAME:
                    data.add(new User(cursor.getString(cursor.getColumnIndex(User.EMAIL)),
                                    cursor.getString(cursor.getColumnIndex(User.PASSWORD)),
                                    cursor.getString(cursor.getColumnIndex(User.NAME))));
                    break;
                case Owns.TABLE_NAME:
                    data.add(new Owns(cursor.getString(cursor.getColumnIndex(Owns.OWNER)),
                                    cursor.getString(cursor.getColumnIndex(Owns.OWNER)),
                                    cursor.getBlob(cursor.getColumnIndex(Owns.IMAGE))));
                    break;
                default:
                    break;
            }
        }
        cursor.close();
        return data;
    }

    private static void notifyListeners() {
        for (OnDatabaseChangeListener listener: listeners)
            listener.onDbChange();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create the SQLite table
        createDB();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        clearDB();
        onCreate(db);
    }

    public interface OnDatabaseChangeListener {
        void onDbChange();
    }
}
