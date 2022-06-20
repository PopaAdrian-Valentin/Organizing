package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import DataBase.DataBaseHelper;
import com.example.organizing.R;

import Models.CategoryModel;
import Models.CurrencyModel;

public class CurrencyActivity extends AppCompatActivity {

    Button btn_addcurr;
    EditText name;
    ListView lv_currlist;

    ArrayAdapter currencyArratAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        btn_addcurr=findViewById(R.id.btn_addcurr);
        name=findViewById(R.id.namecurr);
        lv_currlist=findViewById(R.id.lv_currlist);

        dataBaseHelper=new DataBaseHelper(CurrencyActivity.this);

        ShowListView(dataBaseHelper);
        btn_addcurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrencyModel currencyModel;
                try{
                    currencyModel = new CurrencyModel(-1,name.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(CurrencyActivity.this, "Error creating category", Toast.LENGTH_SHORT).show();
                    currencyModel=new CurrencyModel(-1,"error");
                }

                DataBaseHelper dataBaseHelper =new DataBaseHelper(CurrencyActivity.this);
                boolean success=dataBaseHelper.addCurr(currencyModel);

                Toast.makeText(CurrencyActivity.this, "Result= " +success, Toast.LENGTH_SHORT).show();
                ShowListView(dataBaseHelper);
                name.setText("");
            }
        });

        lv_currlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryModel clickedCat= (CategoryModel) parent.getItemAtPosition(position);
                dataBaseHelper.DeleteCurr(clickedCat);
                ShowListView(dataBaseHelper);
                Toast.makeText(CurrencyActivity.this, "Deleted"+ clickedCat.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ShowListView(DataBaseHelper dataBaseHelper) {
        currencyArratAdapter=new ArrayAdapter<CurrencyModel>(CurrencyActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getCurrencies());
        lv_currlist.setAdapter(currencyArratAdapter);
    }
}