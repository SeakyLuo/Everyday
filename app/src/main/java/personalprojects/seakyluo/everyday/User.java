package personalprojects.seakyluo.everyday;

import java.util.ArrayList;

public class User {
    public static User user;
    public static final String CREATE_TABLE = "CREATE TABLE User(email text, password text, name text)";
    private String email, password, name;
    private ArrayList<Diary> diaries = new ArrayList<>();

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static boolean verify(String email, String password) throws Exception{

        return true;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Diary> getDiaries() {
        return diaries;
    }
    public void addDiary(Diary diary){
        diaries.add(0, diary);
    }
}
