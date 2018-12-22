package personalprojects.seakyluo.everyday;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView dNavigationView;
    private TextView title;
    private ImageButton menu, search;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_nav);
        drawer = findViewById(R.id.drawer_layout);
        dNavigationView = findViewById(R.id.drawer_navigation);
        title = findViewById(R.id.main_title);
        menu = findViewById(R.id.main_menu);
        search = findViewById(R.id.main_search);
        recyclerView = findViewById(R.id.main_content);

        dNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(false);
                item.setCheckable(false);
                drawer.closeDrawer(Gravity.START);
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_user_profile:
                        intent = new Intent(MainActivity.this, UserProfileActivity.class);
//                        intent.putExtra(UserProfileActivity.USERID, User.user.getId());
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                        return true;
                    case R.id.nav_settings:
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                        return true;
                }
                return false;
            }
        });
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH), day = calendar.get(Calendar.DAY_OF_MONTH);
        title.setText("Today");
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        title.setText();
                    }
                }, year, month, day);
                dialog.show();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.START);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });
        setRecyclerView(recyclerView);
    }

    private void moveTo(int position){
        recyclerView.smoothScrollToPosition(position);
    }

    private void setRecyclerView(RecyclerView recyclerView){
        DiaryAdapter adapter = new DiaryAdapter();
//        adapter.addElements(User.user.getDiaries());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        layoutParams.setMargins(0, 4, 0, 4);
        recyclerView.setLayoutParams(layoutParams);
    }
}
