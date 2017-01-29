package kishore.mad.com.expenseapp;
/*
* InClass08
* ExpenseItemAdapter.java
* Nanda Kishore Kolluru
* */
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishorekolluru on 10/17/16.
 */

public class ExpenseItemAdapter extends ArrayAdapter<Expense> {
    List<Expense> expenses;
    public ExpenseItemAdapter(Context context, int resource, List<Expense> objects) {
        super(context, resource, objects);
        expenses = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item, parent, false);
        }
        TextView exp = (TextView) convertView.findViewById(R.id.itemName);
        TextView amt = (TextView) convertView.findViewById(R.id.itemAmt);
        exp.setText(expenses.get(position).getName());
        amt.setText("$"+String.valueOf(new DecimalFormat("#.#").format(expenses.get(position).getAmount())));
        return convertView;
    }
}
