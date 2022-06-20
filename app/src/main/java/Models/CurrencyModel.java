package Models;

public class CurrencyModel {

    private int id;
    private String currencyname;
    private float value;

    public CurrencyModel(int id, String currencyname) {
        this.id = id;
        this.currencyname = currencyname;
        this.value = 0;
    }

    @Override
    public String toString() {
        return
                "CurrencyName=" + currencyname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setCurrencyname(String currencyname) {
        this.currencyname = currencyname;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
