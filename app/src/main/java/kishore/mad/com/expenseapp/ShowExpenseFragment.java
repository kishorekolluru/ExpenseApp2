package kishore.mad.com.expenseapp;

import android.widget.TextView;
/*
* InClass08
* ShowExpenseFragment.java
* Nanda Kishore Kolluru
* */
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ShowExpenseFragment extends Fragment {

    Expense expense;
    private OnFragmentInteractionListener mListener;

    public ShowExpenseFragment(){

    }
TextView name,categ, date , amount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_expense, container, false);

        return v;
    }
    private void setFields(){
        name.setText(expense.getName());
        amount.setText("$"+String.valueOf(new DecimalFormat("#.#").format(expense.getAmount())));
        categ.setText(expense.getCategory());
        date.setText(new SimpleDateFormat("MM/dd/yyyy").format(expense.getDateMade()));
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = (TextView) getView().findViewById(R.id.name_val);
        categ = (TextView) getView().findViewById(R.id.category_val);
        date = (TextView) getView().findViewById(R.id.date_val);
        amount = (TextView) getView().findViewById(R.id.amount_val);
        Button button = (Button) getView().findViewById(R.id.closeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction();
            }
        });
        setFields();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnExpenseFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void addExpense(Expense expenses) {
        expense = expenses;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
