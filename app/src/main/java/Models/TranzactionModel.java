package Models;

import android.app.Activity;
import android.content.Context;

import DataBase.DataBaseHelper;

public class TranzactionModel extends Activity {

    private int id;
    private float value;
    private String description;
    private String category;
    private String currency;
    private String type;
    private String date;
    private Context context;


    public TranzactionModel(int id, float value, String description, String category, String currency, String type,String date) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.category = category;
        this.currency = currency;
        this.type = type;
        this.date=String.valueOf(date);
    }
    public TranzactionModel(int id, float value, String description, String category, String currency, String type,String date,Context context) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.category = category;
        this.currency = currency;
        this.type = type;
        this.date=String.valueOf(date);
        this.context=context;
    }

    public TranzactionModel(int id,float value,String description){
        this.id=id;
        this.value=value;
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {

        DataBaseHelper dataBaseHelper=new DataBaseHelper(context);
        return "Value=" + value +'\n' +
                "Description='" + description + '\n' +
                "Category=" + category + '\n'+
                "Currencyid=" + currency + '\n' +
                "Type='" + type + '\n' +
                "Date=" + date + '\n';
    }

    public String Display(String category,String currency) {

        DataBaseHelper dataBaseHelper=new DataBaseHelper(context);
        return "Value=" + value +'\n' +
                "Description='" + description + '\n' +
                "Category=" + category + '\n'+
                "Currency=" + currency + '\n' +
                "Type='" + type + '\n' +
                "Date=" + date + '\n';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrencyid(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
