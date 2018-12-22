package personalprojects.seakyluo.everyday;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;

public class SignupActivity extends AppCompatActivity {

    private EditText nameText, emailText, passwordText, cpasswordText;
    private Button signupButton;
    private TextView loginLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SwipeBackHelper.onCreate(this);
        Intent intent = getIntent();

        nameText = findViewById(R.id.signup_name);
        emailText = findViewById(R.id.signup_email);
        passwordText = findViewById(R.id.signup_password);
        cpasswordText = findViewById(R.id.signup_confirmPassword);
        signupButton = findViewById(R.id.signup);
        loginLink = findViewById(R.id.link_login);

        emailText.setText(intent.getStringExtra(User.EMAIL));
        passwordText.setText(intent.getStringExtra(User.PASSWORD));

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    User.user = new User(emailText.getText().toString(), passwordText.getText().toString(), nameText.getText().toString());
                    User.user.insert();
                    setResult(RESULT_OK);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                }
            }
        });
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
            }
        });
    }

    public boolean validate(){
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String cpassword = cpasswordText.getText().toString();

        valid = checkName(nameText, name);
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Invalid Email!");
            valid = false;
        }else if (User.findEmail(email).size() == 1){
            emailText.setError("This email has been used");
            valid = false;
        }
        if (password.length() < 8) {
            passwordText.setError("You password should have at least 8 characters.");
            valid = false;
        }else if (password.length() > 30) {
            passwordText.setError("You password cannot have more than 30 characters.");
            valid = false;
        }
        if (!cpassword.equals(password)) {
            cpasswordText.setError("Passwords NOT match!");
            valid = false;
        }
        return valid;
    }

    public static boolean checkName(EditText editText, String name){
        boolean valid = true;
        if (name.trim().length() < 2) {
            editText.setError("Username should have at least 2 characters");
            valid = false;
        }else if (name.trim().length() > 40) {
            editText.setError("Username cannot have more than 40 characters");
            valid = false;
        }else if (name.contains(" ")){
            editText.setError("You cannot have space in your name.");
            valid = false;
        }
        return valid;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}
