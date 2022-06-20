package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import DataBase.DataBaseHelper;
import com.example.organizing.R;

import Models.CalendarModel;
import Models.CategoryModel;

public class CalendarActivity extends AppCompatActivity {

    private EditText txt_event;
    private EditText txt_description;
    private CalendarView calendarView;
    private String selectedDate=null;
    DataBaseHelper dataBaseHelper;
    ListView lv_events;
    ArrayAdapter calendarArrayAdapter;
    Button btn_save;
    Button btn_tranzactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dataBaseHelper=new DataBaseHelper(this);

        txt_event=findViewById(R.id.txt_event);
        txt_description=findViewById(R.id.txt_eventdescription);
        calendarView=findViewById(R.id.calendarView);

        lv_events=findViewById(R.id.lv_events);

        lv_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarModel clickedEvent= (CalendarModel) parent.getItemAtPosition(position);
                boolean succes=dataBaseHelper.DeleteEvent(clickedEvent.getEvent());
                if(!succes) {
                    ShowListView(dataBaseHelper, selectedDate);
                    Toast.makeText(CalendarActivity.this, "Deleted" + clickedEvent.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_tranzactions=findViewById(R.id.btn_bank);
        btn_tranzactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_save=findViewById(R.id.btn_save);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month++;
                if(month<10){
                    selectedDate=String.valueOf(year) +"-0"+ String.valueOf(month) +"-"+ String.valueOf(dayOfMonth);
                }
                if((dayOfMonth)<10){
                    selectedDate=String.valueOf(year) +"-"+ String.valueOf(month) +"-0"+ String.valueOf(dayOfMonth);
                }
                if(month<10 && (dayOfMonth)<10){

                    selectedDate=String.valueOf(year) +"-0"+ String.valueOf(month) +"-0"+ String.valueOf(dayOfMonth);
                }
                else {
                    if (selectedDate == null)
                        selectedDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(dayOfMonth);
                }
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CalendarModel calendarModel;

                        try{
                            String event=txt_event.getText().toString();
                            String eventDescription=txt_description.getText().toString();
                            calendarModel=new CalendarModel(selectedDate,event,eventDescription);
                            boolean success=dataBaseHelper.addEvent(calendarModel);
                        }
                        catch (Exception e){
                            Toast.makeText(CalendarActivity.this, "Error creating event", Toast.LENGTH_SHORT).show();
                        }
                        ShowListView(dataBaseHelper,selectedDate);
                        txt_description.setText("");
                        txt_event.setText("");
                    }
                });
                ShowListView(dataBaseHelper,selectedDate);
            }
        });

       // ShowListView(dataBaseHelper);
    }

    private void ShowListView(DataBaseHelper dataBaseHelper,String date) {

        try{
            calendarArrayAdapter=new ArrayAdapter<CalendarModel>(CalendarActivity.this, android.R.layout.simple_list_item_1,dataBaseHelper.getEvents(date));
            lv_events.setAdapter(calendarArrayAdapter);
        }
        catch (Exception e){
            Toast.makeText(CalendarActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}