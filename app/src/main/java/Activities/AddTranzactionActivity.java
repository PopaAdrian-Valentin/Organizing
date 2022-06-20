package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import DataBase.DataBaseHelper;
import com.example.organizing.R;

import java.util.ArrayList;
import java.util.List;

import Models.CategoryModel;
import Models.CurrencyModel;
import Models.TranzactionModel;

public class AddTranzactionActivity extends AppCompatActivity {

    private static final int AddCategory=0;
    private static final int CardtoCashCategory=1;
    private static final int CashtoCardCategory=2;
    DataBaseHelper dataBaseHelper=new DataBaseHelper(AddTranzactionActivity.this);
    List<CategoryModel> catlist=new ArrayList<>();
    List<CurrencyModel> currlist=new ArrayList<>();
    String[] types={"cash","card"};
    Spinner spinner_cat;
    Spinner spinner_curr;
    Spinner spinner_type;
    Button btn_addTra;
    TextView txt_value,txt_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tranzaction);
        DataBaseHelper dataBaseHelper =new DataBaseHelper(AddTranzactionActivity.this);
        txt_value=findViewById(R.id.txt_value);
        txt_description=findViewById(R.id.txt_description);

        spinner_cat=findViewById(R.id.spinner_cat);
        catlist=dataBaseHelper.getCategories();
        ArrayAdapter<CategoryModel> adaptercat=new ArrayAdapter<CategoryModel>(this, android.R.layout.simple_spinner_item,catlist);
        adaptercat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cat.setAdapter(adaptercat);

        spinner_curr=findViewById(R.id.spinner_curr);
        currlist= dataBaseHelper.getCurrencies();
        ArrayAdapter<CurrencyModel> adaptercurr=new ArrayAdapter<CurrencyModel>(this, android.R.layout.simple_spinner_item,currlist);
        adaptercurr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_curr.setAdapter(adaptercurr);

        spinner_type=findViewById(R.id.spinner_type);
        ArrayAdapter<String> adaptertype=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,types);
        adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adaptertype);

        btn_addTra=findViewById(R.id.btn_addTra);

        btn_addTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TranzactionModel tranzactionModel;
                try{
                    int categoryID;
                    categoryID=spinner_cat.getSelectedItemPosition();
                    String category=dataBaseHelper.GetCategoryName(String.valueOf(categoryID+1));
                    String currency=dataBaseHelper.GetCurrencyName(1);
                    String value=txt_value.getText().toString();
                    float valuefromstring=Float.parseFloat(value);
                    java.util.Date datenow = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(datenow.getTime());
                    float valuef=valuefromstring;
                    String add="Add";
                    boolean success;
                    if(categoryID==CardtoCashCategory){
                            TranzactionModel tranzactionModeladd;
                            tranzactionModeladd=new TranzactionModel(-1,valuef,txt_description.getText().toString(),add,currency,"cash",String.valueOf(sqlDate),AddTranzactionActivity.this);
                            tranzactionModel=new TranzactionModel(-1,-valuef,txt_description.getText().toString(),"FromCard",currency,"card",String.valueOf(sqlDate),AddTranzactionActivity.this);
                        success=dataBaseHelper.addTranzaction(tranzactionModeladd);
                        success=dataBaseHelper.addTranzaction(tranzactionModel);
                        }
                    else if(categoryID==CashtoCardCategory){
                        TranzactionModel tranzactionModeladd;
                        tranzactionModeladd=new TranzactionModel(-1,valuef,txt_description.getText().toString(),add,currency,"card",String.valueOf(sqlDate),AddTranzactionActivity.this);
                        tranzactionModel=new TranzactionModel(-1,-valuef,txt_description.getText().toString(),"FromCash",currency,"cash",String.valueOf(sqlDate),AddTranzactionActivity.this);
                        success=dataBaseHelper.addTranzaction(tranzactionModeladd);
                        success=dataBaseHelper.addTranzaction(tranzactionModel);
                    }else if(categoryID!=AddCategory){
                        valuef=-valuefromstring;
                        tranzactionModel=new TranzactionModel(-1,valuef,txt_description.getText().toString(),category,currency,types[spinner_type.getSelectedItemPosition()].toString(),String.valueOf(sqlDate),AddTranzactionActivity.this);
                        success=dataBaseHelper.addTranzaction(tranzactionModel);
                    }
                    else{
                        tranzactionModel=new TranzactionModel(-1,valuef,txt_description.getText().toString(),category,currency,types[spinner_type.getSelectedItemPosition()].toString(),String.valueOf(sqlDate),AddTranzactionActivity.this);
                        success=dataBaseHelper.addTranzaction(tranzactionModel);
                    }


                }catch (Exception e){
                    Toast.makeText(AddTranzactionActivity.this, "Error creating transaction", Toast.LENGTH_SHORT).show();
                    tranzactionModel=new TranzactionModel(-1,-1,"error");
                }
                //boolean success=dataBaseHelper.addTranzaction(tranzactionModel);

                Intent intent =new Intent(AddTranzactionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}