package personalprojects.seakyluo.everyday;

import android.content.ContentValues;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.security.acl.Owner;

public class Owns {
    public static String TABLE_NAME = "Owns", OWNER = "Owner", IMAGE = "Image";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + OWNER +" text, " +
                                                                                IMAGE + "blob, " +
                                                                                "PRIMARY KEY(" + OWNER + ", " + IMAGE + "))";
    public static String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
    private String owner;
    private Bitmap image;
    public Owns(String owner, Bitmap image){
        this.owner = owner;
        this.image = image;
    }
    public void insert(){
        InsertQuery(owner, image);
    }
    public static void InsertQuery(String owner, Bitmap image){
        ByteArrayOutputStream BAOStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, BAOStream);// (0-100)压缩文件
        byte[] bytes = BAOStream.toByteArray();
        ContentValues values = new ContentValues();
        values.put(OWNER, owner);
        values.put(IMAGE, bytes);
        DbHelper.getDb().insert(TABLE_NAME, null, values);
    }
}
