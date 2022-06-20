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

public class CategoryActivity extends AppCompatActivity {

    Button btn_addcat;
    EditText name;
    ListView lv_catlist;

    ArrayAdapter categoryArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btn_addcat=findViewById(R.id.btn_addcat);
        name=findViewById(R.id.namecat);
        lv_catlist=findViewById(R.id.lv_catlist);

        dataBaseHelper=new DataBaseHelper(CategoryActivity.this);

        ShowListView(dataBaseHelper);
        btn_addcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryModel categoryModel;
                try{
                   categoryModel = new CategoryModel(-1,name.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(CategoryActivity.this, "Error creating category", Toast.LENGTH_SHORT).show();
                    categoryModel=new CategoryModel(-1,"error");
                }

                DataBaseHelper dataBaseHelper =new DataBaseHelper(CategoryActivity.this);
                boolean success=dataBaseHelper.addCat(categoryModel);

                Toast.makeText(CategoryActivity.this, "Result= " +success, Toast.LENGTH_SHORT).show();
                ShowListView(dataBaseHelper);
                name.setText("");
            }
        });

        lv_catlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryModel clickedCat= (CategoryModel) parent.getItemAtPosition(position);
                dataBaseHelper.DeleteCat(clickedCat);
                ShowListView(dataBaseHelper);
                Toast.makeText(CategoryActivity.this, "Deleted"+ clickedCat.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ShowListView(DataBaseHelper dataBaseHelper) {
        categoryArrayAdapter=new ArrayAdapter<CategoryModel>
                (CategoryActivity.this, android.R.layout.simple_list_item_1,dataBaseHelper.getCategoriesFrom4());
        lv_catlist.setAdapter(categoryArrayAdapter);
    }
}