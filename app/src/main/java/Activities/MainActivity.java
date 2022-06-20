package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import DataBase.DataBaseHelper;
import com.example.organizing.R;

import Models.TranzactionModel;

public class MainActivity extends AppCompatActivity {

    public float sold=0,soldCard=0,soldCash=0;
    public TextView Sold;
    public TextView SoldCash;
    public TextView SoldCard;
    Button category;
    Button currency;
    Button tranzaction;
    Button date1;
    Button date2;
    Button btn_calendar;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    ListView lv_tra;
    ArrayAdapter tranzactionArrayAdapter;
    DataBaseHelper dataBaseHelper;
    Button generatePDFbtn;

    int pageHeight = 1120;
    int pagewidth = 792;

    Bitmap bmp, scaledbmp;
    Bitmap bmp2, scaledbmp2;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper=new DataBaseHelper(MainActivity.this);
        sold= dataBaseHelper.getValue();
        Sold=(TextView)findViewById(R.id.textViewSold);
        Sold.setText(String.valueOf(sold));
        soldCard= dataBaseHelper.getValueCard();
        SoldCard=(TextView)findViewById(R.id.textViewCard);
        SoldCard.setText(String.valueOf(soldCard));
        soldCash= dataBaseHelper.getValueCash();
        SoldCash=(TextView)findViewById(R.id.textViewCash);
        SoldCash.setText(String.valueOf(soldCash));


        btn_calendar=findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
        lv_tra=findViewById(R.id.lv_tra);

        category=findViewById(R.id.btn_cat);
        category.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
        });


        tranzaction=findViewById(R.id.btn_tra);
        tranzaction.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, AddTranzactionActivity.class);
            startActivity(intent);
        });
        ShowListView(dataBaseHelper);
        // initializing our variables.
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date1=findViewById(R.id.button_date1);
        date1.setText("Date1");
        date2=findViewById(R.id.button_date2);
        date2.setText("Date2");
        generatePDFbtn = findViewById(R.id.buttonPDF);
        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to
                // generate our PDF file.
                generatePDF(dataBaseHelper);
                date1.setText("Date1");
                date2.setText("Date2");
            }
        });
    }

    public void setDate1(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "SetDate1",
                Toast.LENGTH_SHORT)
                .show();
    }

    public void setDate2(View view) {
        showDialog(998);
        Toast.makeText(getApplicationContext(), "SetDate2",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener1, year, month, day);
        }
        else{
            if(id==998){
                return new DatePickerDialog(this,
                        myDateListener2, year, month, day);
            }
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener1 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate1(arg1,arg2+1,arg3);
                }
            };
    private DatePickerDialog.OnDateSetListener myDateListener2 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate2(arg1,arg2+1,arg3);
                }
            };
    private void showDate1(int year, int month, int day) {
        StringBuilder databuilder=null;
        if(month<10){
            databuilder=new StringBuilder().append(year).append("-0")
                    .append(month).append("-").append(day-1);
        }
        if((day-1)<10){
            databuilder=new StringBuilder().append(year).append("-")
                    .append(month).append("-0").append(day-1);
        }
        if(month<10 && (day-1)<10){

                databuilder = new StringBuilder().append(year).append("-0")
                        .append(month).append("-0").append(day-1);

        }
        else {
            if (databuilder == null) {
                databuilder = new StringBuilder().append(year).append("-")
                        .append(month).append("-").append(day-1);
            }
        }
        date1=findViewById(R.id.button_date1);
        date1.setText(String.valueOf(databuilder));
    }
    private void showDate2(int year, int month, int day) {
        StringBuilder databuilder = null;
        if(month<10){
            databuilder=new StringBuilder().append(year).append("-0")
                    .append(month).append("-").append(day+1);
        }
        if((day+1)<10){
            databuilder=new StringBuilder().append(year).append("-")
                    .append(month).append("-0").append(day+1);
        }
        if(month<10 && (day+1)<10){
            databuilder=new StringBuilder().append(year).append("-0")
                    .append(month).append("-0").append(day+1);
        }
        else{
            if(databuilder==null)
            databuilder=new StringBuilder().append(year).append("-")
                    .append(month).append("-").append(day+1);}
        date2=findViewById(R.id.button_date2);
        date2.setText(String.valueOf(databuilder));
    }

    public void Category(View view)
    {
        Intent intent =new Intent(MainActivity.this, CategoryActivity.class);
        startActivity(intent);
    }

    public void Currency(View view)
    {
        Intent intent =new Intent(MainActivity.this, CurrencyActivity.class);
        startActivity(intent);
    }

    private void ShowListView(DataBaseHelper dataBaseHelper) {
        tranzactionArrayAdapter=new ArrayAdapter<TranzactionModel>(MainActivity.this, android.R.layout.simple_list_item_1,dataBaseHelper.getTranzactions());
        lv_tra.setAdapter(tranzactionArrayAdapter);
    }

    private void generatePDF(DataBaseHelper dataBaseHelper) {
        List<TranzactionModel> list;
        if(date1.getText().toString()=="Date1"|| date2.getText().toString()=="Date2"){
            Toast.makeText(this, "Error generateing pdf please select dates", Toast.LENGTH_SHORT).show();
        }else{
            list=dataBaseHelper.getTranzactionsBetweenDates((String) date1.getText(),(String) date2.getText());
            tranzactionArrayAdapter=new ArrayAdapter<TranzactionModel>(MainActivity.this, android.R.layout.simple_list_item_1,dataBaseHelper.getTranzactions());

            PdfDocument pdfDocument = new PdfDocument();

            Paint paint = new Paint();
            Paint title = new Paint();


            int pageNumber=1;
            PdfDocument.PageInfo mypageInfo1 = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
            PdfDocument.PageInfo mypageInfo2 = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 2).create();

            PdfDocument.Page myPage1 = pdfDocument.startPage(mypageInfo1);

            Canvas canvas1 = myPage1.getCanvas();

            canvas1.drawBitmap(scaledbmp, 56, 40, paint);

            title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            title.setTextSize(15);

            title.setColor(ContextCompat.getColor(this, R.color.black));

            canvas1.drawText("AccountStatement "+(String) date1.getText()+"-"+(String) date2.getText(), 209, 80, title);

            title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            title.setColor(ContextCompat.getColor(this, R.color.black));
            title.setTextSize(15);

            title.setTextAlign(Paint.Align.LEFT);
            int y=210;
            int i=0;
            try {


                do {
                    canvas1.drawText(list.get(i).toString(), 10, y, title);
                    i++;
                    y = y + 30;
                } while (list.size() != i || i == 27);
            }catch (Exception e){
                canvas1.drawText("No tranzactions between " +(String)date1.getText()+"-"+(String)date2.getText(), 10, y, title);
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }

            pdfDocument.finishPage(myPage1);

            PdfDocument.Page myPage2 = pdfDocument.startPage(mypageInfo2);

            Canvas canvas2 = myPage2.getCanvas();
            canvas2.drawBitmap(scaledbmp, 56, 40, paint);

            title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

            title.setTextSize(15);

            title.setColor(ContextCompat.getColor(this, R.color.black));

            canvas2.drawText("AccountStatement "+(String) date1.getText()+"-"+(String) date2.getText(), 209, 80, title);


            title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            title.setColor(ContextCompat.getColor(this, R.color.black));
            title.setTextSize(15);

            title.setTextAlign(Paint.Align.LEFT);
            y=210;
            if(i!=list.size()){
            i=31;
            do{
                canvas2.drawText(list.get(i).toString(), 10, y, title);
                i++;
                y=y+30;
            }while(list.size()!=i);}

            pdfDocument.finishPage(myPage2);

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "AccountStatement "+(String) date1.getText()+"-"+(String) date2.getText()+".pdf");
            try {

                pdfDocument.writeTo(new FileOutputStream(file));


                Toast.makeText(MainActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {

                e.printStackTrace();
            }

            pdfDocument.close();
        }
    }

    private boolean checkPermission() {

        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

}