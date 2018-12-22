package personalprojects.seakyluo.everyday;

import android.content.ContentValues;

import java.util.ArrayList;

public class User {
    public static User user;
    public static final String TABLE_NAME = "User", EMAIL = "Email", PASSWORD = "Password", NAME = "Name";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + EMAIL + " text, " +
                                                                                        PASSWORD + " text, " +
                                                                                        NAME + " text)";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
    private String email, password, name;
    private ArrayList<Diary> diaries = new ArrayList<>();

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User verify(String email, String password) throws Exception {
        if (findEmail(email).size() == 0)
            throw new Exception("Wrong Email!");
        ArrayList<User> users = DbHelper.get(TABLE_NAME, getQuery() + " WHERE " + EMAIL + "=" + email + " AND " + PASSWORD + "=" + password);
        if (users.size() == 0)
            throw new Exception("Wrong Password!");
        return users.get(0);
    }
    public static ArrayList<User> findEmail(String email){
        return DbHelper.get(TABLE_NAME, getQuery() + " WHERE " + EMAIL + "=" + email);
    }

    public String getName() { return name; }
    public ArrayList<Diary> getDiaries() { return diaries; }
    public void addDiary(Diary diary){ diaries.add(0, diary); }
    public void insert(){ Insert(email, password, name); }
    public static void Insert(String email, String password, String name){
        ContentValues values = new ContentValues();
        values.put(EMAIL, email);
        values.put(PASSWORD, password);
        values.put(NAME, name);
        DbHelper.db.insert(TABLE_NAME, null, values);
    }
    public static String getQuery() { return "SELECT * FROM " + TABLE_NAME; }
}
