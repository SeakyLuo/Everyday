package personalprojects.seakyluo.everyday;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String CREDENTIALS = "Credentials";
    private static final int SIGN_UP = 0;
    private Button loginButton;
    private TextView signup, forgot;
    private EditText emailText, passwordText;

    @Override
    protected void onStart() {
        super.onStart();
//        DbHelper.init(this);
        SharedPreferences sharedPreferences =  getSharedPreferences(CREDENTIALS, MODE_PRIVATE);
        String email = sharedPreferences.getString(User.EMAIL, ""), password =  sharedPreferences.getString(User.PASSWORD, "");
        if (verify(email, password)){
            onLoginSuccess();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login);
        emailText = findViewById(R.id.login_email);
        passwordText = findViewById(R.id.login_password);
        signup = findViewById(R.id.link_signup);
        forgot = findViewById(R.id.link_forgot_password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verify(emailText.getText().toString(), passwordText.getText().toString())){
                    onLoginSuccess();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                intent.putExtra(User.EMAIL, emailText.getText().toString());
                intent.putExtra(User.PASSWORD, passwordText.getText().toString());
                startActivityForResult(intent, SIGN_UP);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });
    }

    private boolean verify(String email, String password){
        if (email.isEmpty() || password.isEmpty()) return false;
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();
        dialog.setMessage("Verifying...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        loginButton.setEnabled(false);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loginButton.setEnabled(true);
            }
        });
        try{
            User.user = User.verify(email, password);
            dialog.dismiss();
            return User.user != null;
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return false;
        }
    }

    private void onLoginSuccess(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        // Save Credentials
        SharedPreferences userInfo = getSharedPreferences(CREDENTIALS, MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putString(User.EMAIL, emailText.getText().toString());
        editor.putString(User.PASSWORD, passwordText.getText().toString());
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        onLoginSuccess();
    }
}
