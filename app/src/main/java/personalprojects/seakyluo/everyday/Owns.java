package personalprojects.seakyluo.everyday;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Owns {
    public static final String TABLE_NAME = "Owns", OWNER = "Owner", NAME = "Name", IMAGE = "Image";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + OWNER +" text, " +
                                                                                NAME + "text, " +
                                                                                IMAGE + "blob, " +
                                                                                "PRIMARY KEY(" + OWNER + ", " + NAME + "))";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
    private String owner;
    private String name; // Image Name
    private byte[] image;
    public Owns(String owner, String name, byte[] image){
        this.owner = owner;
        this.name = name;
        this.image = image;
    }
    public Owns(String owner, String name, Bitmap image){
        this.owner = owner;
        this.name = name;
        this.image = BitmapToBytes(image);
    }
    public void insert(){ Insert(owner, name, image); }
    public static void Insert(String owner, String name, byte[] image){
        ContentValues values = new ContentValues();
        values.put(OWNER, owner);
        values.put(NAME, name);
        values.put(IMAGE, image);
        DbHelper.db.insert(TABLE_NAME, null, values);
    }
    public static String getQuery() { return "SELECT * FROM " + TABLE_NAME; }
    public static Bitmap BytesToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static byte[] BitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream BAOStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, BAOStream);// (0-100)压缩文件
        return BAOStream.toByteArray();
    }
    public static Bitmap findImage(String owner, String name){
        ArrayList<Owns> result = DbHelper.get(TABLE_NAME, getQuery() + " WHERE " + OWNER + "=" + owner + " AND " + NAME + "=" + name);
        if (result.size() == 0) return null;
        return BytesToBitmap(result.get(0).image);
    }
}
