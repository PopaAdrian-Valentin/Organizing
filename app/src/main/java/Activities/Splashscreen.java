package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import DataBase.DataBaseHelper;
import com.example.organizing.R;

import java.util.ArrayList;
import java.util.List;

public class Splashscreen extends AppCompatActivity {
    private static int Splash_Time_out=4000;
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("My notification","My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        java.util.Date datenow = new java.util.Date();
        java.sql.Date tomorrow = new java.sql.Date(datenow.getTime()+MILLIS_IN_A_DAY);

        dataBaseHelper=new DataBaseHelper(this);

        List<String> list = new ArrayList<String>();
        list=dataBaseHelper.getEventsName(String.valueOf(tomorrow));
        String events;
        if(list.isEmpty()){
            events="No events tomorrow";
        }else {
            events = TextUtils.join(", ", list);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(Splashscreen.this,"My notification").
                setSmallIcon(R.drawable.colors)
                .setContentTitle("Tomorrow's events")
                .setContentText(events)
                .setAutoCancel(true);
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(Splashscreen.this);
        managerCompat.notify(1,builder.build());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent= new Intent(Splashscreen.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },Splash_Time_out);
    }
}