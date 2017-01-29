package kishore.mad.com.expenseapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by kishorekolluru on 9/9/16.
 */
public class Expense implements Parcelable {
    private String _key;
    private String name;
    private String category;
    private double amount;
    private Date dateMade;


    public Expense(String name, double amount, Date date, String category) {
        this.name = name;
        this.amount = amount;
        this.dateMade = date;
        this.category = category;

    }

    public String get_key() {
        return _key;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDateMade() {
        return dateMade;
    }

    public void setDateMade(Date dateMade) {
        this.dateMade = dateMade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeDouble(amount);
        dest.writeSerializable(dateMade);
    }


    protected Expense(Parcel in) {
        name = in.readString();
        category = in.readString();
        amount = in.readDouble();
        dateMade = (Date) in.readSerializable();
    }


    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                ", category='" + category + '\'' +
                ", date='" + dateMade.toString() + '\'' +

                '}';
    }
}
