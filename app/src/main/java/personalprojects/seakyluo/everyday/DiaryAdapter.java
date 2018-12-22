package personalprojects.seakyluo.everyday;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryAdapter extends CustomAdapter {

    public class ViewHolder extends CustomAdapter.CustomViewHolder{
        private TextView date, weather;
        private ContentView content;
        private Diary data;

        public ViewHolder(@NonNull View view) {
            super(view);
            date = view.findViewById(R.id.diary_date);
            weather = view.findViewById(R.id.diary_weather);
            content = view.findViewById(R.id.diary_content);
        }

        @Override
        public void setData(Object object) {
            data = (Diary) object;
            date.setText(new SimpleDateFormat("yyyy-MM-dd E", Locale.CHINA).format(new Date(data.getDate())));
            weather.setText(data.getWeather());
            content.setText(data.getContent());
        }
    }

}
